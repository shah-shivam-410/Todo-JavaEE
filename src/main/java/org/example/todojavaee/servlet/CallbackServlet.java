package org.example.todojavaee.servlet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.todojavaee.dao.UserDao;
import org.example.todojavaee.model.User;
import org.example.todojavaee.utils.KeycloakConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CallbackServlet.class);

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Inside CallbackServlet - doGet");
        String code = req.getParameter("code");
        log.info("code: {}", code);
        String state = req.getParameter("state");
        log.info("state: {}", state);
        String error = req.getParameter("error");
        log.info("error: {}", code);

        HttpSession session = req.getSession();
        String savedState = (String) session.getAttribute("oauth_state");
        log.info("savedState from session: {}", savedState);

        // Verify state (CSRF protection)
        if (state == null || !state.equals(savedState)) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=invalid_state");
            return;
        }
        if (error != null) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=" + error);
            return;
        }
        if (code == null) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=no_code");
            return;
        }

        try {
            JsonObject tokens = exchangeCodeForToken(code);
            String accessToken = tokens.getString("access_token");
            log.info("accessToken: {}", accessToken);
            JsonObject userInfo = getUserInfo(accessToken);
            log.info("userInfo: {}", userInfo);

            String keyCloakId = userInfo.getString("sub");
            User user = userDao.findByKeycloakId(keyCloakId);
            if(user == null) {
                user = new User(keyCloakId, userInfo.getString("email"), userInfo.getString("name", userInfo.getString("email")));
                userDao.createUser(user);
            }

            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("accessToken", accessToken);

            resp.sendRedirect(req.getContextPath() + "/tasks");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/error.jsp?msg=auth_failed");
        }

    }

    private JsonObject exchangeCodeForToken(String code) throws IOException {
        URL url = new URL(KeycloakConfig.TOKEN_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "grant_type=authorization_code" +
                "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(KeycloakConfig.REDIRECT_URI, StandardCharsets.UTF_8) +
                "&client_id=" + URLEncoder.encode(KeycloakConfig.CLIENT_ID, StandardCharsets.UTF_8) +
                "&client_secret=" + URLEncoder.encode(KeycloakConfig.CLIENT_SECRET, StandardCharsets.UTF_8);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }

        try (InputStream is = conn.getInputStream();
             JsonReader reader = Json.createReader(is)
        ) {
            return reader.readObject();
        }
    }

    private JsonObject getUserInfo(String accessToken) throws IOException {
        URL url = new URL(KeycloakConfig.USERINFO_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        try (InputStream is = conn.getInputStream();
             JsonReader reader = Json.createReader(is)) {
            return reader.readObject();
        }
    }


}
