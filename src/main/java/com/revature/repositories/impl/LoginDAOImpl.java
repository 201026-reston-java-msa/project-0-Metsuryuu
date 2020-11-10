package com.revature.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.connection.ConnectionUtil;
import com.revature.model.User;
import com.revature.repositories.LoginDAO;

import jdk.internal.org.jline.utils.Log;

public class LoginDAOImpl implements LoginDAO{
	
	private static Logger log = Logger.getLogger(LoginDAOImpl.class);

	@Override
	public boolean loginUser(User u) {	//user validation.
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "Select userid, first_name, last_name, roleid FROM project0.users "+
						 "Where username = ? AND pass = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1,u.getUsername());
			statement.setString(2, u.getPassword());
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				
				u.setUserId(result.getInt("userid"));
				u.setFirst_name(result.getString("first_name"));
				u.setLast_name(result.getString("last_name"));
				u.setRoleId(result.getInt("roleid"));
				
			}
			
			result.close();
			
			if(u.getFirst_name()==null) {	//implied that the SELECT didn't return an existing user.
				throw new SQLException();
			}
			
		} catch (SQLException e) {
			log.warn("Login failed. Please try again.");
			return false;
		}
		return true;
	}


}
