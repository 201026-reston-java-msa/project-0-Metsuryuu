package com.revature.repositories;

import java.util.List;

import com.revature.model.User;

public interface AdminDAO {
	
	public List<User> findAll();
	public User findById(int id);
	public boolean insert(User u);
	public boolean update(User u);
	public boolean withdraw(double amount, User u);
	public boolean deposit(double amount, User u);
	public boolean transfer(double amount, User u, User other);
	public boolean cancel(User u);	//remove the user account from db.

}
