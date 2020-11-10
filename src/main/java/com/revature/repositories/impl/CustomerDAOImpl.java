package com.revature.repositories.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.security.auth.login.AccountException;

import org.apache.log4j.Logger;

import com.revature.Printer;
import com.revature.connection.ConnectionUtil;
import com.revature.model.Account;
import com.revature.model.AccountType;
import com.revature.model.Status;
import com.revature.repositories.CustomerDAO;

public class CustomerDAOImpl implements CustomerDAO{
	
	private static final Logger log = Logger.getLogger(CustomerDAOImpl.class);
	
	private List<Account> ownedAccounts;
	private int userid;
	
	public List<Account> getOwnedAccounts(int userid) {	
		
		//If the list is empty, set the accounts.
		if(this.ownedAccounts == null) {
			getAccounts(userid);
			this.userid = userid;
		}
		
		return this.ownedAccounts;
	}

	public void getAccounts(int userid){
		
		this.ownedAccounts = new ArrayList<Account>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM project0.get_owned_accounts(?)";
			
			CallableStatement statement = conn.prepareCall(sql);
			
			statement.setInt(1, userid);
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				
				//set each account to the ownedAccounts list.
				Account a = new Account();
				a.setAccountId(result.getInt("accountid"));
				
				Status s = new Status(result.getInt("status"));
				a.setAccountStatus(s);
				
				AccountType at = new AccountType(result.getInt("typeid"));
				a.setAccountType(at);
				
				a.setBalance(result.getBigDecimal("balance").doubleValue());
				
				this.ownedAccounts.add(a);
			}
			
		} catch (SQLException e) {
			log.warn("Failed to find accounts. Please contact system admin.");
		}
		
	}
	
	public boolean isOwned(int accountid) {
		
		boolean callPolice = true;	//this will make more sense later.
		
		for(Account a : ownedAccounts) {
			if(a.getAccountId() == accountid) {
				callPolice = false;
			}
		}
		
		//returns true if the account is not owned by that individual.
		return callPolice;
			
	}
	
	@Override
	public boolean withdraw(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			if(isOwned(id)) {
				throw new AccountException();
			}
			
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
				
				getAccounts(this.userid);
				
			}else {
				//if the inputed amount happens to be negative, throw an exception.
				throw new NumberFormatException();
			}			
			
		}catch(SQLException e) {
			log.warn("Withdraw failed, please try again.", e);
		}catch(NumberFormatException e) {
			log.warn("Only positive numerics are accepted, please try again.");
		}catch (AccountException e) {
			log.warn("You do not own this account. You may only withdraw from your accounts.");
		}
		
		return false;
	}

	@Override
	public boolean deposit(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			if(isOwned(id)) {
				throw new AccountException();
			}
			
			Scanner sc = new Scanner(System.in);
			System.out.println("How much would you like to deposit?");
			
			if(!sc.hasNextDouble()) {
				throw new NumberFormatException();
			}
			
			double amount = sc.nextDouble();
			
			if(amount >= 0) {	//check for negative numbers.
				
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
				//if the inputed amount happens to be negative, throw an exception.
				throw new NumberFormatException();
			}			
			
		}catch(SQLException e) {
			log.warn("Withdraw failed, please try again.", e);
		}catch(NumberFormatException e) {
			log.warn("Only positive numerics are accepted, please try again.");
		}catch (AccountException e) {
			log.warn("You do not own this account. You may only deposit to your accounts.");
		}
		
		return false;
	}

	@Override
	public boolean transfer(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			if(isOwned(id)) {
				throw new AccountException();
			}
			
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
		} catch (AccountException e) {
			log.warn("You may only transfer funds from one of your accounts.");
		}
		
		return false;
	}

	@Override
	public boolean apply(int key, int type, int userid) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "";
			
			if(key == 1) {
				
				sql = "CALL project0.insert_account(?,?,?)";
				
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, userid);
				statement.setInt(2, type);
				statement.setBigDecimal(3, BigDecimal.ZERO);	//set balance to 0.
				
				statement.execute();
				
			}else if(key == 2) {
				
				System.out.println("Please enter the User ID of the other account holder.");
				Scanner sc = new Scanner(System.in);
				if(!sc.hasNextInt()) {
					throw new NumberFormatException();
				}
				int user2 = sc.nextInt();
				
				sql = "CALL project0.insert_joint_account(?,?,?,?)";
				
				CallableStatement statement = conn.prepareCall(sql);
				
				statement.setInt(1, userid);
				statement.setInt(2, user2);
				statement.setInt(3, type);
				statement.setBigDecimal(4, BigDecimal.ZERO);
				
				statement.execute();
			}
			
			log.info("Account created: Status PENDING");
			System.out.println("Your account has been made and is currently pending. Please wait for it to be approved.");
			
		} catch (SQLException e) {
			log.warn("Application failed, please try again.");
		}catch(NumberFormatException e) {
			log.warn("Input incorrect: Only numerics accepted.");
		}
		
		return false;
	}

}
