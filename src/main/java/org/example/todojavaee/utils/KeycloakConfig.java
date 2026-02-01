package org.example.todojavaee.utils;

public class KeycloakConfig {

    public static final String ROOT_APPLICATION_URL = "http://localhost:8080/todo-app";

    public static final String REALM = "todo-realm";
    public static final String AUTH_SERVER_URL = "http://localhost:8180";
    public static final String CLIENT_ID = "todo-app";
    public static final String CLIENT_SECRET = "rSIfAXMype2ZFUGMNWRUFZIxKmKcDNPy"; // Replace with your actual secret

    public static final String TOKEN_ENDPOINT = AUTH_SERVER_URL + "/realms/" + REALM + "/protocol/openid-connect/token";
    public static final String AUTH_ENDPOINT = AUTH_SERVER_URL + "/realms/" + REALM + "/protocol/openid-connect/auth";
    public static final String LOGOUT_ENDPOINT = AUTH_SERVER_URL + "/realms/" + REALM + "/protocol/openid-connect/logout";
    public static final String USERINFO_ENDPOINT = AUTH_SERVER_URL + "/realms/" + REALM + "/protocol/openid-connect/userinfo";

    public static final String REDIRECT_URI = ROOT_APPLICATION_URL + "/callback";



}
