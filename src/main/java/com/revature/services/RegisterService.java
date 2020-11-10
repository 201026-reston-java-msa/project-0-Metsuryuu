package com.revature.services;

import java.util.Scanner;

import com.revature.model.User;
import com.revature.repositories.RegisterDAO;
import com.revature.repositories.impl.RegisterDAOImpl;

public class RegisterService {
	
	RegisterDAO repository = null;
	
	public RegisterService() {
		repository = new RegisterDAOImpl();
	}
	
	public boolean register() {
		
		System.out.println("Welcome!\n\nTo register, please enter the following:\n");
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			User newUser = new User();
			
			System.out.print("Username: ");
			newUser.setUsername(sc.nextLine());
			
			System.out.print("Password: ");
			newUser.setPassword(sc.nextLine());
			
			System.out.print("First Name: ");
			newUser.setFirst_name(sc.nextLine());
			
			System.out.print("Last Name: ");
			newUser.setLast_name(sc.nextLine());
			
			System.out.println("Is the following information correct? Y/n");
			
			System.out.println("Username: "+newUser.getUsername()+"\nPassword: "+newUser.getPassword()
			+"\nFirst Name: "+newUser.getFirst_name()+"\nLast Name: "+newUser.getLast_name());
			
			String key = sc.nextLine(); 
			
			if(key.equalsIgnoreCase("y")) {
				repository.register(newUser);
				return true;
			}else {
				System.out.println("Would you like to try again? Y/n");
				key = sc.nextLine();
				
				if(key.equalsIgnoreCase("y")) {
					System.out.println("Please enter the following:\n");
					continue;
				}else {
					return false;
				}
			}
		}
	}

}
