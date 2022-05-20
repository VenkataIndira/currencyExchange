package com.currency.exchange.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Account {
	
	@Id
	@Column(name="OwnerID")
	private int accountID;
	
	@Column(name="Currency")
	private String currency;
	
	@Column(name="Balance")
	private BigDecimal balance;
	
	public Account(int accountID, String currency, BigDecimal balance) {
		this.accountID = accountID;
		this.currency = currency;
		this.balance = balance;
	}
	
	Account(){
		
	}
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "Account [accountID=" + accountID + ", currency=" + currency + ", balance=" + balance + "]";
	}
	
}
