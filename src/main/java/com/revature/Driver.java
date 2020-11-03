package com.revature;

import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
		
		//basically just call run.
		
	}
	
	static void run() {
		Scanner in = new Scanner(System.in);
		String userInput;
		
		System.out.println("Welcome to your online banking application!\n"+
		"\nDo you have an exisiting account? Y/n...");
		
		userInput = in.nextLine();
		
		if(userInput.equalsIgnoreCase("y")) {
			//login
		}else {
			//create new user
		}
	}

}
