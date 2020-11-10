package com.revature.repositories.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.revature.Printer;
import com.revature.connection.ConnectionUtil;
import com.revature.model.Account;
import com.revature.model.AccountType;
import com.revature.model.Status;
import com.revature.model.User;
import com.revature.repositories.AdminDAO;



public class AdminDAOImpl implements AdminDAO{

	private static final Logger log = Logger.getLogger(AdminDAOImpl.class);
	
	@Override
	public boolean findAll(int key) {
		
		List<User> userList = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = null;
			
			if(key == 1) {
				//indicates search for customers.
				sql = "Select * from project0.get_all_customers()";
			}else if(key == 2) {
				//search for employees.
				sql = "Select * from project0.get_all_employees()";
			}else if(key == 3) {
				//search for admins.
				sql = "Select * from project0.get_all_admins()";
			}
			
			CallableStatement statement = conn.prepareCall(sql);
			
			ResultSet result = statement.executeQuery();	//As this is a select statement, executeQuery() works.
			
			while(result.next()) {
				
				User u = new User();
				
				u.setUserId(result.getInt("userid"));
				u.setUsername(result.getString("username"));
				u.setPassword(result.getString("pass"));
				u.setFirst_name(result.getString("first_name"));
				u.setLast_name(result.getString("last_name"));
				u.setRoleId(result.getInt("roleid"));
				
				userList.add(u);
				
			}
			
			Printer print = new Printer();	//use the helper class for formatted printing functions.
			if(print.printUserList(userList, key)) {
				return true;
			}
			
		}catch(SQLException e) {
			log.warn("Failed to find users, please wait and try again.",e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean findAllAccounts() {
		
		List<Account> accountList = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = null;
			
			sql = "Select * from project0.get_all_accounts()";
						
			CallableStatement statement = conn.prepareCall(sql);
			
			ResultSet result = statement.executeQuery();	
			
			while(result.next()) {
				
				Account a = new Account();
				
				a.setAccountId(result.getInt("accountid"));
				
				Status s = new Status(result.getInt("status"));
				a.setAccountStatus(s);
				
				AccountType at = new AccountType(result.getInt("typeid"));
				a.setAccountType(at);
				
				a.setBalance(result.getBigDecimal("balance").doubleValue());
				
				accountList.add(a);
				
			}
			
			Printer print = new Printer();
			if(print.printAccountList(accountList)) {
				return true;
			}
			
		}catch(SQLException e) {
			log.warn("Failed to find accounts, please wait and try again.",e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean withdraw(int id) {

		try(Connection conn = ConnectionUtil.getConnection()){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("How much would you like to withdraw?");
			
			if(!sc.hasNextDouble()) {
				throw new NumberFormatException();
			}
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {	//check for negative numbers.
				
				String sql = "CALL project0.withdraw(?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setBigDecimal(2, BigDecimal.valueOf(amount));	
				
				statement.execute();
				log.info(amount+" has been withdrawn from account "+id+".");
				
				//Check to see that the account has been updated.
				
				System.out.println("The updated balance will now be displayed...\n");
				
				sql = "Select status, typeid, balance From project0.accounts Where accountid = ?";
				
				statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				
				ResultSet result = statement.executeQuery();	
				
				List<Account> accountList = new ArrayList<>();
				
				while(result.next()) {
					
					Account a = new Account();
					a.setAccountId(id);
					
					Status s = new Status(result.getInt("status"));
					a.setAccountStatus(s);
					
					AccountType at = new AccountType(result.getInt("typeid"));
					a.setAccountType(at);
					
					a.setBalance(result.getBigDecimal("balance").doubleValue());
					
					accountList.add(a);
					
				}
				
				Printer print = new Printer();
				if(print.printAccountList(accountList)) {
					return true;
				}
				
			}else {
				//if the inputed amount happens to be negative, throw an exception.
				throw new NumberFormatException();
			}			
			
		}catch(SQLException e) {
			log.warn("Withdraw failed, please try again.", e);
		}catch(NumberFormatException e) {
			log.warn("Only positive numerics are accepted, please try again.");
		}
		
		return false;
	}

	@Override
	public boolean deposit(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("How much would you like to deposit?");
			
			if(!sc.hasNextDouble()) {
				throw new NumberFormatException();
			}
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {
				
				String sql = "CALL project0.deposit(?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setBigDecimal(2, BigDecimal.valueOf(amount));	
				
				statement.execute();
				log.info(amount+" has been deposited into account "+id+".");
				
				//Check to see that the account has been updated.
				
				System.out.println("The updated balance will now be displayed...\n");
				
				sql = "Select status, typeid, balance From project0.accounts Where accountid = ?";
				
				statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				
				ResultSet result = statement.executeQuery();	
				
				List<Account> accountList = new ArrayList<>();
				
				while(result.next()) {
					
					Account a = new Account();
					a.setAccountId(id);
					
					Status s = new Status(result.getInt("status"));
					a.setAccountStatus(s);
					
					AccountType at = new AccountType(result.getInt("typeid"));
					a.setAccountType(at);
					
					a.setBalance(result.getBigDecimal("balance").doubleValue());
					
					accountList.add(a);
					
				}
				
				Printer print = new Printer();
				if(print.printAccountList(accountList)) {
					return true;
				}
				
			}else {
				throw new NumberFormatException();
			}			
			
		}catch(SQLException e) {
			log.warn("Deposit failed, please try again.", e);
		}catch(NumberFormatException e) {
			log.warn("Only positive numerics are accepted, please try again.");
			/*
			 * Calling the function again here would truly allow the user to try again. 
			 * For the sake of this program, I chose to make an improper input return to 
			 * the choice selection in case the user did not want to deposit. The same is
			 * true for the withdraw function above and all other exceptions.
			 */
		}
		
		return false;
	}
	
	@Override
	public boolean transfer(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Provide the ID of the recipient account...");
			
			if(!sc.hasNextInt()) {
				throw new NumberFormatException();
			}
			
			int id2 = sc.nextInt();
			
			System.out.println("How much would you like to transfer?");
			
			if(!sc.hasNextDouble()) {
				throw new NumberFormatException();
			}
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {	//check for negative numbers.
				
				String sql = "CALL project0.transfer(?,?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setInt(2, id2);
				statement.setBigDecimal(3, BigDecimal.valueOf(amount));	
				
				statement.execute();
				log.info(amount+" has been transferred from account "+id+" to account "+id2+".");
				
				//Check to see that the account has been updated.
				
				System.out.println("The updated balance will now be displayed...\n");
				
				sql = "Select status, typeid, balance From project0.accounts Where accountid = ? OR accountid = ?";
				
				statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setInt(2, id2);
				
				ResultSet result = statement.executeQuery();	
				
				List<Account> accountList = new ArrayList<>();
				
				while(result.next()) {
					
					Account a = new Account();
					a.setAccountId(id);
					
					Status s = new Status(result.getInt("status"));
					a.setAccountStatus(s);
					
					AccountType at = new AccountType(result.getInt("typeid"));
					a.setAccountType(at);
					
					a.setBalance(result.getBigDecimal("balance").doubleValue());
					
					accountList.add(a);
					
				}
				
				Printer print = new Printer();
				if(print.printAccountList(accountList)) {
					return true;
				}
				
			}else {
				throw new NumberFormatException();
			}			
			
		}catch(SQLException e) {
			log.warn("Transfer failed, please try again.", e);
		}catch(NumberFormatException e) {
			log.warn("Only positive numerics are accepted, please try again.");
		}
		
		return false;
	}
	
	@Override
	public boolean cancel() {
		
		/*
		 * I took this to mean deleting an account, though it could easily be set to "Closed" as well.
		 * In that case, the status would be changed to 3, which corresponds with "Closed" 
		 */
		
		findAllAccounts();	//print out all the accounts for easier viewing.
		
		System.out.println("Enter the ID of the account that you would like to cancel...");
		Scanner sc = new Scanner(System.in);
		if(!sc.hasNextInt()) {
			throw new NumberFormatException();
		}
		int id = sc.nextInt();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "Call project0.deny_account(?)";	//this procedure removes the passed account.
			
			CallableStatement statement = conn.prepareCall(sql);
			
			statement.setInt(1, id);
			statement.execute();
			
			log.info("Account "+id+" has been terminated.");
			System.out.println("The account with the ID "+id+" has been terminated.");
			
		} catch (SQLException e) {
			log.warn("Cancellation failed. Please try again.");
		}catch(NumberFormatException e) {
			log.warn("ID misinput: Only positive numerics are accepted.");
		}
		
		return true;
	}

	@Override
	public boolean pendingChoice() {	//TODO make sure to test this after testing the register function.
		
		Scanner sc = new Scanner(System.in);
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			System.out.println("The following accounts are pending:");
			
			String sql = "Select accountid, typeid, balance from project0.accounts a, "
					+"project0.account_status a2 Where a.status = a2.statusid AND a.status = 1";
			
			CallableStatement statement = conn.prepareCall(sql);
			
			ResultSet result = statement.executeQuery();	
			
			List<Account> accountList = new ArrayList<>();
			
			while(result.next()) {
				
				Account a = new Account();
				a.setAccountId(result.getInt("accountid"));
				
				Status s = new Status(1);
				a.setAccountStatus(s);
				
				AccountType at = new AccountType(result.getInt("typeid"));
				a.setAccountType(at);
				
				a.setBalance(result.getBigDecimal("balance").doubleValue());
				
				accountList.add(a);
				
			}
			
			Printer print = new Printer();
			print.printAccountList(accountList);
			
			//Check to see if any accounts would like to be approved or denied.
			System.out.println("Would you like to act on any of these accounts? Y/n");
			String choice = sc.nextLine();
			
			if(choice.equalsIgnoreCase("y")) {
				
				System.out.println("Enter the ID you would like to act on.");
				int id = sc.nextInt();
				System.out.println("Would you like to approve (1) or deny (0) this account?");
				int apden = sc.nextInt();
				
				if(apden == 1) {
					sql = "Call project0.approve_account(?)";
				}else if(apden == 0) {
					sql = "Call project0.deny_account(?)";
				}else {
					throw new NumberFormatException();
				}
				
				statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				
				statement.execute();
				
				log.info("Account status of account "+id+" has been updated.");
				System.out.println("The account status has been updated.\nWould you like to act on another account? Y/n");
				choice = sc.next();
				
				if(choice.equalsIgnoreCase("y")) {
					//call the function again and start from the top.
					pendingChoice();
				}else {
					//return to the panel.
					return true;
				}
				
			}else {
				//return to the AdminService panel.
				return true;
			}
			
		} catch (SQLException e) {
			log.warn("Update failed, please try again later.");
		}catch(NumberFormatException e) {
			log.warn("Choice not valid, please try again.");
		}
		
		
		return false;
	}

}
