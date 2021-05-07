package com.tridiots.cms.utils.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// TODO add a path to store exceptions

// TODO add comments


public class ConnectionUtils {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/cms";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static void registerDriver() {
        try {
            Class.forName(DRIVER_NAME);
            System.out.println("Driver Registered");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
    
    public static Connection openConnection() {
        registerDriver();
        try {
            System.out.println("Connection opened.");
            return DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);
        } catch(SQLException exception) {
            System.out.println("The connection was not successful");
        } return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
