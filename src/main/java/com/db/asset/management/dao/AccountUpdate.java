package com.db.asset.management.dao;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountUpdate {
	
	private final String accountId;
    private final BigDecimal amount;
    
	public AccountUpdate(String accountId, BigDecimal amount) {		
		this.accountId = accountId;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}    
	
	
	
}
