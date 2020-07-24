package com.wellsfargo.payment.app.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Account")
public class Account {
	@Id
	private Long accountId;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private int accountNumber;
	private int userId;
	private BigDecimal accountBalance;

	public Account() {
		super();
	}

	public Account(Long accountId, int accountNumber, int userId, BigDecimal accountBalance) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.accountBalance = accountBalance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountNumber=" + accountNumber + ", userId=" + userId
				+ ", accountBalance=" + accountBalance + "]";
	}

}
