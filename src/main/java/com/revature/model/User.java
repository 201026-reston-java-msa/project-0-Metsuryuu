package com.revature.model;

public class User {
	
	private int userId;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private int roleId;
	
	public User() {}

	public User(int userId, String first_name, String last_name, String username, String password) {
		super();
		this.userId = userId;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", first_name=" + first_name + ", last_name=" + last_name + ", username="
				+ username + ", roleId=" + roleId + "]";
	}	
	

}
