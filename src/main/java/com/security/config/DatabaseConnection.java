package com.security.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/security_bd", "postgres", "Kimnamjoon");
        } catch (ClassNotFoundException e) { throw new SQLException(e); }
    }
}