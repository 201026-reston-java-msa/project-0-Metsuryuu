package com.revature.model;

public class AccountType {
	
	private int accountTypeId;
	private String accountType;
	
	public AccountType() {}

	public AccountType(int accountTypeId) {
		super();
		String accountType = "";
		
		this.accountTypeId = accountTypeId;
		
		if(accountTypeId == 1) {
			accountType = "Checking";
		}else if(accountTypeId == 2) {
			accountType = "Savings";
		}
		
		this.accountType = accountType;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
		
		String accountType = "";	//similar to Status, no need for the other setter.
		
		if(accountTypeId == 1) {
			accountType = "Checking";
		}else if(accountTypeId == 2) {
			accountType = "Savings";
		}
		
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	
	

}
