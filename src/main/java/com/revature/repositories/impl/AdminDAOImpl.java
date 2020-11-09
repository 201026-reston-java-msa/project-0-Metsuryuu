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
	public boolean withdraw(int id) {	//TODO Add the logger here, not sure how to at the moment.

		try(Connection conn = ConnectionUtil.getConnection()){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("How much would you like to withdraw?");
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {	//check for negative numbers.
				
				String sql = "CALL project0.withdraw(?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setBigDecimal(2, BigDecimal.valueOf(amount));	
				
				statement.execute();
				
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
			log.warn("Negative numerics are not accepted, please try again.");
		}
		
		return false;
	}

	@Override
	public boolean deposit(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			Scanner sc = new Scanner(System.in);
			System.out.println("How much would you like to deposit?");
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {
				
				String sql = "CALL project0.deposit(?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setBigDecimal(2, BigDecimal.valueOf(amount));	
				
				statement.execute();
				
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
			log.warn("Negative numerics are not accepted, please try again.");
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
			int id2 = sc.nextInt();
			
			System.out.println("How much would you like to transfer?");
			double amount = sc.nextDouble();
			
			if(amount >= 0) {	//check for negative numbers.
				
				String sql = "CALL project0.transfer(?,?,?)";
			
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, id);
				statement.setInt(2, id2);
				statement.setBigDecimal(3, BigDecimal.valueOf(amount));	
				
				statement.execute();
				
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
			log.warn("Negative numerics are not accepted, please try again.");
		}
		
		return false;
	}
	
	@Override
	public boolean cancel(int id) {
		
		/*
		 * I took this to mean deleting an account, though it could easily be set to "Closed" as well.
		 * In that case, the status would be changed to 3, which corresponds with "Closed"
		 */
		return false;
	}

}
