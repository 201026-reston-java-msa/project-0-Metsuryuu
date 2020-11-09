package com.revature.repositories;

import java.util.List;

import com.revature.model.Account;

public interface CustomerDAO {

	public void getAccounts(int userid);
	public List<Account> getOwnedAccounts(int userid);
	public boolean withdraw(int id);
	public boolean deposit(int id);
	public boolean transfer(int id);
	
}
