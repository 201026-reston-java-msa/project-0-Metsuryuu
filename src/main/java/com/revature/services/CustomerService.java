package com.revature.services;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.Printer;
import com.revature.model.User;
import com.revature.repositories.CustomerDAO;
import com.revature.repositories.impl.CustomerDAOImpl;

import jdk.internal.org.jline.utils.Log;

public class CustomerService {

	CustomerDAO repository = null;
	
	private static final Logger log = Logger.getLogger(CustomerService.class);
	
	public CustomerService() {
		repository = new CustomerDAOImpl();
	}
	
	public boolean CustomerLogin(User customer){
		
		System.out.println("Welcome "+customer.getFirst_name()+"!");
		
		System.out.println("Your accounts are as follows:");
		Printer print = new Printer();
		print.printAccountList(repository.getOwnedAccounts(customer.getUserId()));
		
		while(true) {
						
			System.out.println("\nWhat would you like to do?");
		
			System.out.println("1 - Withdraw from account");
			System.out.println("2 - Deposit to account");
			System.out.println("3 - Transfer between accounts");
			System.out.println("4 - Apply for a new account");
			System.out.println("5 - Logout");
			
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
				case 2:
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
				case 3:
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
				case 4:
					System.out.println("Are you looking to apply for a standard(1) or joint(2) account?");
					try {
						if(!sc.hasNextInt()) {
							throw new NumberFormatException();
						}
						int account = sc.nextInt();
						
						System.out.println("Would you like to make a checking (1) or savings (2) account?");
						
						if(!sc.hasNextInt()) {
							throw new NumberFormatException();
						}
						int type = sc.nextInt();
						
						if(account == 1) {
							repository.apply(account, type, customer.getUserId());
						}else if(account == 2) {
							repository.apply(account, type, customer.getUserId());
						}else {
							throw new NumberFormatException();
						}
						
					}catch(NumberFormatException e) {
							log.warn("Improper choice selection, please only use 1 or 2.");
					}
					
					break;
				case 5:
					customer = null;	//remove the user before logging out.
					break;
			}
			if(key == 5) {	
				return false;					
			}
		}
}

}

