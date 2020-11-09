package com.revature;

import java.util.List;
import com.revature.model.Account;
import com.revature.model.User;

public class Printer {
	
	//helper class to print input in a formatted manner.
	
	public Printer() {}
	
	public boolean printUserList(List<User> userList, int key) {
		
		System.out.println("+----+---------------+---------------+---------------+----+");
		
		//no need to make a function for each one, just change the title.
		if(key == 1) {
			System.out.println("|                         Customers                       |");
		}else if(key == 2) {
			System.out.println("|                         Employees                       |");
		}else if(key == 3) {
			System.out.println("|                       Administrators                    |");
		}
		
		System.out.println("+----+---------------+---------------+---------------+----+");
		System.out.println("| ID |   Username    |   First Name  |   Last Name   |Role|");
		System.out.println("+----+---------------+---------------+---------------+----+");
		
		for(User u : userList) {
			System.out.printf("|%-4s|%-15s|%-15s|%-15s|%-4s|%n",u.getUserId(),u.getUsername(),u.getFirst_name(),u.getLast_name(),u.getRoleId());
			System.out.println("+----+---------------+---------------+---------------+----+");
		}
		
		return true;
	}
	
	public boolean printAccountList(List<Account> accountList) {
		
		System.out.println("+------------------------------------+");
		
		System.out.println("|               Accounts             |");
		
		System.out.println("+----+--------+----------+-----------+");
		System.out.println("| ID | Status |   Type   |  Balance  |");
		System.out.println("+----+--------+----------+-----------+");
		
		for(Account a : accountList) {
			System.out.printf("|%-4s|%-8s|%-10s|%-11s|%n",a.getAccountId(),a.getAccountStatus().getStatus(),a.getAccountType().getAccountType(),a.getBalance());
			System.out.println("+----+--------+----------+-----------+");
		}
		return true;
	}

}
