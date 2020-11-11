package com.revature.repositories.impl;

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
import com.revature.repositories.EmployeeDAO;

public class EmployeeDAOImpl implements EmployeeDAO{
	
	private static final Logger log = Logger.getLogger(EmployeeDAOImpl.class);

	@Override
	public boolean findCustomers() {

		List<User> userList = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "Select * from project0.get_all_customers()";

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
			if(print.printUserList(userList, 1)) {
				return true;
			}
			
		}catch(SQLException e) {
			log.warn("Failed to find users, please wait and try again.",e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean findAccounts() {

		List<Account> accountList = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "Select * from project0.get_all_accounts()";
						
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
	public boolean pendingChoice() {

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
			return false;
		}catch(NumberFormatException e) {
			log.warn("Choice not valid, please try again.");
			return false;
		}
		
		return false;
	}

}
