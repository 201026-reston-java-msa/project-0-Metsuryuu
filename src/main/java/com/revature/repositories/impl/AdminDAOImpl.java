package com.revature.repositories.impl;

import java.util.List;

import com.revature.model.User;
import com.revature.repositories.AdminDAO;

public class AdminDAOImpl implements AdminDAO{

	//TODO might want to pass the userId instead of the user for Admin. For User services, it should be fine.
	
	@Override
	public List<User> findAll() {
		// TODO return all users.
		return null;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(User u) {
		// TODO insert the user passed.
		return false;
	}

	@Override
	public boolean update(User u) {
		// TODO update the relative fields based on id.
		return false;
	}

	@Override
	public boolean withdraw(double amount, User u) {
		/* 
		 * TODO withdraw from the user's account the amount.
		 * Make sure to check for overdrawing and negative numbers.
		 */
		
		return false;
	}

	@Override
	public boolean deposit(double amount, User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancel(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transfer(double amount, User u, User other) {
		// TODO Auto-generated method stub
		return false;
	}

}
