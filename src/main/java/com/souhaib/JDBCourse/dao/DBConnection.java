package com.souhaib.JDBCourse.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306;
    private static final String DB_NAME = "JavaTest";
    private static final String USERNAME = "souhaib";
    private static final String PASSWORD = "souhaib2003";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
        } catch(SQLException se) {
            se.printStackTrace();
        }
        return connection;
    }
}
