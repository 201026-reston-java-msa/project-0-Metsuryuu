package com.revature;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import com.revature.repositories.impl.LoginDAOImpl;
import com.revature.services.LoginService;

public class Driver {
	
	private static Logger log = Logger.getLogger(LoginDAOImpl.class);
	
	public static void main(String[] args) {
		
		//basically just call run.
		try {
			while(true) {
				run();
			}
		}catch(NoSuchElementException e) {
			log.info("Terminating... Have a nice day.");
			System.exit(0);
		}
		
		
		
	}
	
	static void run() {

		LoginService login = new LoginService();
		boolean session = login.login();
		if(session==false) {
			System.out.println("Session completed. We look forward to seeing you again.");
		}
	}
	
	/*
	 * ---------------------------------TODO-------------------------------------------------
	 * For the Logout, just delete the User with all their data and return to the run method. 
	 * 
	 * When registering a user, make sure they can only view a message that says their account 
	 * is pending and then send them back to the login. If they try to log in with the account 
	 * that was just made, make sure to check the account status before displaying any opportunities.
	 * Maybe involve a constructor?
	 * 
	 * The joint account should be simple, just have two people share an accountId in the database.
	 * Have a condition within newAccount() that checks for joint account and send it to a different
	 * function that accepts two usernames and two passwords.
	 * 
	 * Permissions will be implemented as each user type will have a different Service class. Have the
	 * proper switch statements inside those.
	 * 
	 * When finished in a service, bump back up to the run method in this class. Maybe put it in a while(true)
	 * in a try block with a Ctrl^Z exit exception like on your Linux machine.
	 */	

}
