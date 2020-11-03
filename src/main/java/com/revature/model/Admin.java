package com.revature.model;

public class Admin {
	
	private int adminId;
	private String first_name;
	private String last_name;
	private String email;
	private double salary;
	
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public Admin(int adminId, String first_name, String last_name, String email, double salary) {
		super();
		this.adminId = adminId;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.salary = salary;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	

}
