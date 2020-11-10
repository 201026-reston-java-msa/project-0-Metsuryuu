package com.revature;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.revature.repositories.impl.LoginDAOImpl;
import com.revature.services.LoginService;

public class Driver {
	
	private static Logger log = Logger.getLogger(LoginDAOImpl.class);
	
	public static void main(String[] args) {
		
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
