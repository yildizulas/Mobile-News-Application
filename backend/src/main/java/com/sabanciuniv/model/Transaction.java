package com.sabanciuniv.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {

	
	@Id private String fromId;
	private String toId;
	
	private double amount;
	private LocalDateTime createDate = LocalDateTime.now();

	private Account accountFrom;
	private Account accountTo;
	
	public Transaction() {

	}
	
	public Transaction(String fromId, String toId, double amount, LocalDateTime createDate) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.amount = amount;
		this.createDate = createDate;
	}
	

	public Transaction(String fromId, String toId, double amount, Account accountFrom, Account accountTo) {
		super();
		this.fromId = fromId;
		this.toId = toId;
		this.amount = amount;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
	}
	
	public Account getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Account accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Account getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Account accountTo) {
		this.accountTo = accountTo;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
}
