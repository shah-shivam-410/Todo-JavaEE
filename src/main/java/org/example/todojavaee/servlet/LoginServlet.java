package org.example.todojavaee.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.todojavaee.utils.KeycloakConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Inside LoginServlet - doGet");
        String state = UUID.randomUUID().toString();
        log.info("State: {}", state);
        HttpSession session = req.getSession();
        session.setAttribute("oauth_state", state);

        String authUrl = KeycloakConfig.AUTH_ENDPOINT +
                "?client_id=" + URLEncoder.encode(KeycloakConfig.CLIENT_ID, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(KeycloakConfig.REDIRECT_URI, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&state=" + state;

        resp.sendRedirect(authUrl);

    }
}
