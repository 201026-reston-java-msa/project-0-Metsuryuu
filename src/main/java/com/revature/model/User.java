package com.revature.model;

public class User {
	
	private int userId;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private Account account;
	
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


	public Account getAccount() {
		/*
		 * TODO check to make sure this is their account.
		 * This will probably be done in a DAO tho.
		 */
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}	
	

}
