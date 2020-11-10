package com.revature;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.revature.repositories.impl.LoginDAOImpl;
import com.revature.services.LoginService;

public class Driver {
	
	private static Logger log = Logger.getLogger(LoginDAOImpl.class);
	
	public static void main(String[] args) {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(
									"C:\\Users\\qroma\\Documents\\Revature\\Projects\\project-0-Metsuryuu\\src\\main\\resources\\banner.txt")
									);
			String line = in.readLine();
			
			while(line != null) {
				System.out.println(line);
				line = in.readLine();
			}
			in.close();
			
			//a thank you to those at ascii-art-generator.org for the banner.
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(true) {
				run();
			}
		}catch(NoSuchElementException e) {
			//This catches any Ctrl+ANY inputs which can be used for an immediate exit.
			log.info("Terminating... Have a nice day.");
			System.exit(0);
		}
		
	}
	
	static void run() {

		LoginService login = new LoginService();
		boolean session = login.login();
		if(session==false) {
			System.out.println("Session completed. We look forward to seeing you again.\n\n");
		}
	}

}
