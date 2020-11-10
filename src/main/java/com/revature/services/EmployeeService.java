package com.revature.services;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.repositories.EmployeeDAO;
import com.revature.repositories.impl.EmployeeDAOImpl;

public class EmployeeService {
	
	EmployeeDAO repository = null;
	
	private static final Logger log = Logger.getLogger(EmployeeService.class);
	
	public EmployeeService() {
		repository = new EmployeeDAOImpl();
	}
	
	public boolean EmployeeLogin(User employee){
		
		System.out.println("Welcome "+employee.getFirst_name()+"!");
		
		while(true) {
			System.out.println("\nWhat would you like to do?");
		
			System.out.println("1 - View all customers");
			System.out.println("2 - View all accounts");
			System.out.println("3 - Act on pending accounts");	//approve or deny pending accounts.
			System.out.println("4 - Logout");
			
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
					log.info("Customers requested by "+employee.getUsername());
					repository.findCustomers();
					break;
				case 2:
					log.info("Accounts requested by "+employee.getUsername());
					repository.findAccounts();
					break;
				case 3:
					repository.pendingChoice();
					break;
				case 4:
					employee = null;	//remove the user before logging out.
					break;
			}
			if(key == 4) {	
				return false;					
			}
		}
}

}
