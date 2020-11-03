package com.revature.services;

import java.util.Scanner;

import com.revature.repositories.LoginDAO;
import com.revature.repositories.impl.LoginDAOImpl;

public class LoginService {
	
	LoginDAO login = null;
	
	public LoginService() {
		login = new LoginDAOImpl();
	}
	
	public boolean login() {
		System.out.println("Welcome to RomanEmp MIM Banking.\n\n");
		System.out.println("1 - Register\n2 - User Login\n3 - Employee Login\n4 - Admin Login");
		
		Scanner sc = new Scanner(System.in);
		
		int login_key = sc.nextInt();
		
		switch(login_key) {
			case 1:
				newAccount();	//TODO might want to pass the created user up.
				break;
			case 2:
				loginUser();
				break;
			case 3:
				loginEmployee();
				break;
			case 4:
				loginAdmin();
				break;
		}
		
		return false;
	}
	
	public boolean newAccount() {
		return false;
	}
	
	public boolean loginUser() {
		return false;
	}
	
	public boolean loginEmployee() {
		return false;
	}
	
	public boolean loginAdmin() {
		return false;
	}

}
