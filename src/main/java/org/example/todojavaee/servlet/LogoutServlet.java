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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Inside LogoutServlet - doGet");
        HttpSession session = req.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        String logoutUrl = KeycloakConfig.LOGOUT_ENDPOINT +
                "?client_id=" + URLEncoder.encode(KeycloakConfig.CLIENT_ID, StandardCharsets.UTF_8) +
                "&post_logout_redirect_uri=" +
                URLEncoder.encode(KeycloakConfig.ROOT_APPLICATION_URL, StandardCharsets.UTF_8);
        resp.sendRedirect(logoutUrl);
    }
}
