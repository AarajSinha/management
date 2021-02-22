package com.db.asset.management.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AmountTransfer implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3166682299738190218L;

	@NotNull
    @NotEmpty
    private String accountFromId;

    @NotNull
    @NotEmpty
    private String accountToId;

    @NotNull
    @Min(value = 1, message = "Transfer amount must be positive.")
    private BigDecimal amount;

    
    public String getAccountFromId() {
		return accountFromId;
	}


	public void setAccountFromId(String accountFromId) {
		this.accountFromId = accountFromId;
	}


	public String getAccountToId() {
		return accountToId;
	}


	public void setAccountToId(String accountToId) {
		this.accountToId = accountToId;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	@JsonCreator
    public AmountTransfer(@JsonProperty("accountFromId") String accountFromId,
                    @JsonProperty("accountToId") String accountToId,
                    @JsonProperty("amount") BigDecimal amount){
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }
}
