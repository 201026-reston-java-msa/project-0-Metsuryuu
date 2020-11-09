package com.revature.services;

import java.util.List;
import java.util.Scanner;

import com.revature.model.User;
import com.revature.repositories.AdminDAO;
import com.revature.repositories.impl.AdminDAOImpl;

public class AdminService {
	
	AdminDAO repository = null;
	
	public AdminService() {
		repository = new AdminDAOImpl();
	}
	
	public boolean AdminLogin(User admin){
		
		System.out.println("Welcome "+admin.getFirst_name()+"!");
		
		while(true) {
			System.out.println("\nWhat would you like to do?");
		
			System.out.println("1 - View all customers");
			System.out.println("2 - View all employees");
			System.out.println("3 - View all admins");
			System.out.println("4 - View all accounts");
			System.out.println("5 - Withdraw from account");
			System.out.println("6 - Deposit to account");
			System.out.println("7 - Transfer between accounts");
			System.out.println("8 - Act on pending accounts");	//approve or deny pending accounts.
			System.out.println("9 - Cancel accounts");
			System.out.println("10 - Logout");
			
			Scanner sc = new Scanner(System.in);
			int key = sc.nextInt();
		
		
			switch(key) {
				case 1:
					int findKey = 1;	//indicates find all customers.
					repository.findAll(findKey);
					break;
				case 2:
					findKey = 2;
					repository.findAll(findKey);
					break;
				case 3:
					findKey = 3;
					repository.findAll(findKey);
					break;
				case 4:
					repository.findAllAccounts();
					break;
				case 5:
					System.out.println("Enter the id of the account you wish to withdraw from...");
					int accountid = sc.nextInt();
					repository.withdraw(accountid);
					break;
				case 6:
					System.out.println("Enter the id of the account you wish to deposit into...");
					accountid = sc.nextInt();
					repository.deposit(accountid);
					break;
				case 7:
					System.out.println("Enter the id of the account to transfer funds from...");
					accountid = sc.nextInt();
					repository.transfer(accountid);
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					admin = null;	//remove the user before logging out.
					break;
			}
			if(key == 10) {	
				sc.close();
				return false;					
			}
		}
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
