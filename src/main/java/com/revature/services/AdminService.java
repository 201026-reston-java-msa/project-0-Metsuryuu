package com.revature.services;

import java.util.List;

import com.revature.model.User;
import com.revature.repositories.AdminDAO;
import com.revature.repositories.impl.AdminDAOImpl;

public class AdminService {
	
	AdminDAO repository = null;
	
	public AdminService() {
		repository = new AdminDAOImpl();
	}
	
	public List<User> findAll(){	//TODO might need to switch to Account.
		//TODO return all users.
		return null;
	}
	
	public User findById(int id) {
		//TODO find users by id number.
		return null;
	}
	
	public boolean insert(User u) {
		//insert the user.
		return false;
	}
	
	public boolean update(User u) {
		//update the fields in user based on id.
		return false;
	}
	
	public boolean withdraw(double amount, User u) {
		return false;
	}
	
	public boolean deposit(double amount, User u) {
		return false;
	}
	
	public boolean cancel(User u) {
		return false;
	}
	
	public boolean transfer(double amount, User u, User other) {
		return false;
	}

}
