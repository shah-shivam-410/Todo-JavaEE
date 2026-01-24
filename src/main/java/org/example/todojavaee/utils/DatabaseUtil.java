package org.example.todojavaee.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        }
    }

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/tododb";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "postgres";

    private static Connection dbConn = null;

    private static final Object lock = new Object();

    public static Connection getConnection() throws SQLException {
        if(dbConn == null || dbConn.isClosed()) {
            synchronized (lock) {
                dbConn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            }
        }
        return dbConn;
    }


}
