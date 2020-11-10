package com.revature.connection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection() {
		
		Connection conn = null;
		Properties prop = new Properties();
		
		try {
			prop.load(new FileReader("C:\\Users\\qroma\\Documents\\Revature\\Projects\\project-0-Metsuryuu\\src\\main\\resources\\application.properties"));		
			conn = DriverManager.getConnection(
					prop.getProperty("url"),
					prop.getProperty("username"),
					prop.getProperty("password")
					);
		} catch (SQLException e) {
			//Give a warning message with the stack trace.
			log.warn("Unable to obtain connection to database",e);
		}catch(FileNotFoundException e) {
			log.warn("Cannot locate property files.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}

}
