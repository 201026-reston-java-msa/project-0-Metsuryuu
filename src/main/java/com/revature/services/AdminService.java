package com.revature.services;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.repositories.AdminDAO;
import com.revature.repositories.impl.AdminDAOImpl;

public class AdminService {
	
	AdminDAO repository = null;
	
	private static final Logger log = Logger.getLogger(AdminService.class);
	
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
			try {
				if(!sc.hasNextInt()) {
					throw new NumberFormatException();
				}
				}catch(NumberFormatException e) {
					log.warn("Only positive numerics are allowed.");
				}
			int key = sc.nextInt();
		
		
			switch(key) {
				case 1:
					int findKey = 1;	//indicates find all customers.
					log.info("Customers requested by "+admin.getUsername());
					repository.findAll(findKey);
					break;
				case 2:
					findKey = 2;
					log.info("Employees requested by "+admin.getUsername());
					repository.findAll(findKey);
					break;
				case 3:
					findKey = 3;
					log.info("Admins requested by "+admin.getUsername());
					repository.findAll(findKey);
					break;
				case 4:
					log.info("Accounts requested by "+admin.getUsername());
					repository.findAllAccounts();
					break;
				case 5:
					System.out.println("Enter the id of the account you wish to withdraw from...");
					try {
						if(!sc.hasNextInt()) {
							throw new NumberFormatException();
						}
						}catch(NumberFormatException e) {
							log.warn("Only positive numerics are allowed.");
						}
					int accountid = sc.nextInt();
					repository.withdraw(accountid);
					break;
				case 6:
					System.out.println("Enter the id of the account you wish to deposit into...");
					try {
						if(!sc.hasNextInt()) {
							throw new NumberFormatException();
						}
						}catch(NumberFormatException e) {
							log.warn("Only positive numerics are allowed.");
						}
					accountid = sc.nextInt();
					repository.deposit(accountid);
					break;
				case 7:
					System.out.println("Enter the id of the account to transfer funds from...");
					try {
						if(!sc.hasNextInt()) {
							throw new NumberFormatException();
						}
						}catch(NumberFormatException e) {
							log.warn("Only positive numerics are allowed.");
						}
					accountid = sc.nextInt();
					repository.transfer(accountid);
					break;
				case 8:
					repository.pendingChoice();
					break;
				case 9:
					repository.cancel();
					break;
				case 10:
					admin = null;	//remove the user before logging out.
					break;
			}
			if(key == 10) {	
				return false;					
			}
		}
}

}
