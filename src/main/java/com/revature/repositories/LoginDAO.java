package com.revature.repositories;

import com.revature.model.User;

public interface LoginDAO {
	
	public boolean newAccount();
	public boolean loginUser(User u);

}
