package com.tridiots.cms.utils.dbutils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinWorkerThread;

import com.tridiots.cms.kanye.IO;

// TODO add a path to store exceptions

// TODO add comments


public class ConnectionUtils {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/cms";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static Driver driver;
    

    private static void registerDriver() {
        try {
            Class.forName(DRIVER_NAME);
            System.out.println("Driver Registered");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void unregisterDriver() {
    	try {
    		@SuppressWarnings("rawtypes")
			Enum drivers = (Enum) DriverManager.getDrivers();
    		while(drivers != null) {
    			DriverManager.deregisterDriver((Driver) drivers);
    		}
			
			IO.println("Driver unregistered");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static Connection openConnection() {
        registerDriver();
        try {
            // TODO add success log
            return DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);
        } catch(SQLException exception) {
            System.out.println("The connection was not successful");
        } return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            if(connection != null) {
                connection.close();
                // TODO add success log message
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } //unregisterDriver();
    }
}
