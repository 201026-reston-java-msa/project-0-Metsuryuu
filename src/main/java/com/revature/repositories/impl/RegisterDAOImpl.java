package com.revature.repositories.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.connection.ConnectionUtil;
import com.revature.model.User;
import com.revature.repositories.RegisterDAO;
import com.revature.services.EmployeeService;

public class RegisterDAOImpl implements RegisterDAO{
	
	private static final Logger log = Logger.getLogger(EmployeeService.class);

	@Override
	public boolean register(User u) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "CALL project0.insert_user(?,?,?,?,3)";
			
			CallableStatement statement = conn.prepareCall(sql);
			
			statement.setString(1, u.getUsername());
			statement.setString(2, u.getPassword());
			statement.setString(3, u.getFirst_name());
			statement.setString(4, u.getLast_name());
			
			statement.execute();
			
			log.info("Successfully added the new user "+u.getUsername()+"!");
			System.out.println("Please login with the given credentials to access your accounts.");
			
		} catch (SQLException e) {
			log.warn("Register failed, please try again.");
		}
		
		return false;
	}

}
