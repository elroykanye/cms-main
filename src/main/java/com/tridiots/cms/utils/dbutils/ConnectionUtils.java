package com.tridiots.cms.utils.dbutils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import com.tridiots.cms.kanye.IO;

// TODO add a path to store exceptions

// TODO add comments


public class ConnectionUtils {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST = "jdbc:mysql://localhost:3306/cms";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static Driver driver;

    @SuppressWarnings("restriction")
	private static void deregisterDriver() {
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while(drivers.hasMoreElements()) {
    		Driver driver = drivers.nextElement();
    		try {
    			DriverManager.deregisterDriver(driver);
    			IO.println("Driver deregistered");
    		} catch(SQLException exception) {
    			exception.printStackTrace();
    		}
    	}
    }
    
    private static void registerDriver() {
        try {
            Class.forName(DRIVER_NAME);
            System.out.println("Driver Registered");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public static Connection openConnection() {
        //registerDriver();
        try {
            // TODO add success log
        	registerDriver();
            return DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);
        } catch(SQLException exception) {
            System.out.println("The connection was not successful");
        } return null;
    }

    public static void closeConnection(Connection connection) {
    	try {
			if(connection != null && !(connection.isClosed())) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
