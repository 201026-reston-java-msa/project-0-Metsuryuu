package com.revature.model;

public class Account {
	
	private int accountId;
	private Status accountStatus;
	private AccountType accountType;
	private double balance;
	
	public Account() {}

	public Account(int accountId, Status accountStatus, AccountType accountType, double balance) {
		super();
		this.accountId = accountId;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Status getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Status accountStatus) {
		this.accountStatus = accountStatus;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	

}
