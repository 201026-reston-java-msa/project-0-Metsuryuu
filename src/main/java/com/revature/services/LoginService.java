package com.revature.services;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.model.User;
import com.revature.repositories.LoginDAO;
import com.revature.repositories.impl.LoginDAOImpl;


public class LoginService {
	
	public LoginDAO login = null;
	
	private static final Logger log = Logger.getLogger(LoginService.class);
	
	public LoginService() {
		login = new LoginDAOImpl();
	}
	
	public boolean login() {
		System.out.println("Welcome to RomanEmp MIM Banking.\n\n");	//TODO make this pretty.
		System.out.println("1 - Register\n2 - Login");
		
		Scanner sc = new Scanner(System.in);
		
		int login_key = sc.nextInt();
		
		switch(login_key) {
			case 1:
				newAccount();	//TODO might want to pass the created user up.
				break;
			case 2:
				loginUser();
				break;
		}
		
		return false;
	}
	
	public boolean newAccount() {
		return false;
	}
	
	public boolean loginUser() {
		
		System.out.println("Welcome!\nPlease enter your username and password.");
		
		Scanner sc = new Scanner(System.in);
		User user = new User();
		
		System.out.print("Username: ");
		user.setUsername(sc.nextLine());
		System.out.print("Password: ");
		user.setPassword(sc.nextLine());
		
		if(login.loginUser(user)) {
			System.out.println();	//just to make some space between this and the last.
			log.info(user.getUsername()+" successfully logged in!\n");
		}else {
			
			//give the chance to try again.
			System.out.println("Would you like to try again? Y/n");
			String tryAgain = sc.nextLine();
			
			if(tryAgain.equalsIgnoreCase("y")) {
				loginUser();
			}else {
				//if denied, abort the program.
				return false;
			}
		}
		
		if(user.getRoleId()==1) {
			AdminService adminLog = new AdminService();
			adminLog.AdminLogin(user);
		}
		
		return true;
	}

}
