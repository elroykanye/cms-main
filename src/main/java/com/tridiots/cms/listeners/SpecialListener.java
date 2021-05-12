package com.tridiots.cms.listeners;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import com.tridiots.cms.kanye.IO;

/**
 * Application Lifecycle Listener implementation class SpecialListener
 *
 */
@WebListener
public class SpecialListener implements ServletContextListener  {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	/**
     * Default constructor. 
     */
    public SpecialListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	 // On Application Shutdown, please…

        // 1. Go fetch that DataSource
        /*
        Context envContext = null;
        DataSource datasource = null;
		try {
			InitialContext initContext = new InitialContext();
			datasource = (DataSource)envContext.lookup("jdbc/database");

			envContext = (Context)initContext.lookup("java:/comp/env");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        *//*
        // 2. Deregister Driver
        try {
        	
            java.sql.Driver mySqlDriver = DriverManager.getDriver("jdbc:mysql://localhost:3306/");
            DriverManager.deregisterDriver(mySqlDriver);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } */
    	deregisterDriver();

        // 3. For added safety, remove the reference to dataSource for GC to enjoy.
        // datasource = null;
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	registerDriver();
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
    
    @SuppressWarnings("restriction")
	private static void deregisterDriver() {
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while(drivers.hasMoreElements()) {
    		Driver driver = drivers.nextElement();
    		IO.println(driver.toString());
    		try {
    			DriverManager.deregisterDriver(driver);
    			IO.println("Driver deregistered");
    		} catch(SQLException exception) {
    			exception.printStackTrace();
    		}
    	}
    }
	
}
