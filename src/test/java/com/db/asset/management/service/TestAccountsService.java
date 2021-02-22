package com.db.asset.management.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.DuplicateAccountIdException;
import com.db.asset.management.exception.NotEnoughFundsException;
import com.db.asset.management.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class TestAccountsService {

	@InjectMocks
	private AccountsService accountService;
	
	@Mock
	private AccountRepository accountRepo;
	
	@Test
	public void testValidCreateAccount() {
		Mockito.doNothing().when(accountRepo).createAccount(new Account("1"));
		 accountService.createAccount(new Account("1"));
	}
	
	@Test
	public void testExistingCreateAccount() {
		Mockito.doThrow(new DuplicateAccountIdException("Account already exists.")).when(accountRepo).createAccount(new Account("1"));		 
		try {	 
		accountService.createAccount(new Account("1"));
		   fail("Should have failed when adding duplicate account");
        } catch (DuplicateAccountIdException ex) {
            assertThat(ex.getMessage()).isEqualTo("Account already exists.");
        }
	}
	
	@Test
    public void transfer_should_fail_when_accountDoesNotExist() {
        final String from = UUID.randomUUID().toString();
        final String to = UUID.randomUUID().toString();
        Mockito.when(accountRepo.getAccount(from)).thenReturn(null);
        AmountTransfer transfer = new AmountTransfer(from, to, new BigDecimal(100));
        try {
            accountService.transferAmount(transfer);
            fail("Should have failed because account does not exist");
        } catch (AccountNotFoundException anfe) {
            assertThat(anfe.getMessage()).isEqualTo("Account " + from + " does not exist.");
        }        
    }

    @Test
    public void transfer_should_fail_when_accountNotEnoughFunds() {
        final String from = UUID.randomUUID().toString();
        final String to = UUID.randomUUID().toString();
        Mockito.when(accountRepo.getAccount(from)).thenReturn(new Account(from));
        Mockito.when(accountRepo.getAccount(to)).thenReturn(new Account(to));
        AmountTransfer transfer = new AmountTransfer(from, to, new BigDecimal(100));
        try {
            accountService.transferAmount(transfer);
            fail("Should have failed because account does not have enough funds for the transfer");
        } catch (NotEnoughFundsException nbe) {
            assertThat(nbe.getMessage()).isEqualTo("Not enough funds on account " + from + ". Please check your balance.");
        }        
    }

    @Test
    public void should_transferFunds() {
        final String from = UUID.randomUUID().toString();
        final String to = UUID.randomUUID().toString();
        
        Mockito.when(accountRepo.getAccount(from)).thenReturn(new Account(from,new BigDecimal("201.99")));
        Mockito.when(accountRepo.getAccount(to)).thenReturn(new Account(to));
        Mockito.when(accountRepo.updateAccounts(Mockito.any())).thenReturn(true);
        AmountTransfer transfer = new AmountTransfer(from, to, new BigDecimal("200.99"));

        assertThat(accountService.transferAmount(transfer)).isEqualTo("Transferred Successfully");
    }   

	
}
