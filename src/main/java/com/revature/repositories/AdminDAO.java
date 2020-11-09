package com.revature.repositories;

public interface AdminDAO {
	
	public boolean findAll(int key);
	public boolean findAllAccounts();
	public boolean withdraw(int accountid);
	public boolean deposit(int accountid);
	public boolean transfer(int accountid);
	public boolean cancel(int userid);	//remove the user account from db.

}
