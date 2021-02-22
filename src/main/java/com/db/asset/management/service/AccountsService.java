package com.db.asset.management.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AccountUpdate;
import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.NotEnoughFundsException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;
import com.db.asset.management.repository.AccountRepository;

@Service
public class AccountsService {

	@Autowired
	private AccountRepository accountRepo;
	
	public void createAccount(Account account) {
		 accountRepo.createAccount(account);
	}

	public Account getAccount(String accountId) {	
		Account account = accountRepo.getAccount(accountId);
		if(null == account) {
			throw new AccountNotFoundException("Account " + accountId + " does not exist.");
		}
		 return account;
	}

	/**
	 * Makes a transfer between two accounts for the balance specified by the
	 * {@link Transfer} object
	 * 
	 * @param transfer
	 * @throws AccountNotFoundException            When an account does not exist
	 * @throws NotEnoughFundsException             When there are not enough funds
	 *                                             to complete the transfer
	 * @throws TransferBetweenSameAccountException Transfer to self account is not
	 *                                             permitted
	 */
	public String transferAmount(AmountTransfer transfer)
			throws AccountNotFoundException, NotEnoughFundsException, TransferBetweenSameAccountException {

		final Account from = getAccount(transfer.getAccountFromId());
		final Account to = getAccount(transfer.getAccountToId());
		final BigDecimal amount = transfer.getAmount();

		validate(from, to);
		boolean successful = false;
		synchronized (from.getAccountId()) {
			synchronized (to.getAccountId()) {
				if (!enoughFunds(from, transfer.getAmount())){
		            throw new NotEnoughFundsException("Not enough funds in account " + from.getAccountId() + ". Please check your balance.");
		        }
				successful = accountRepo.updateAccounts(
						Arrays.asList(new AccountUpdate(from.getAccountId(), amount.negate()),
								new AccountUpdate(to.getAccountId(), amount)));				
			}
		}
		if (successful) {
			//TODO Notification Service provided by other 
			 //notificationService.notifyAboutTransfer(accountFrom, "The transfer to the account with ID " + accountTo.getAccountId() + " is now complete for the amount of " + transfer.getAmount() + ".");
			 //notificationService.notifyAboutTransfer(accountTo, "The account with ID + " + accountFrom.getAccountId() + " has transferred " + transfer.getAmount() + " into your account.");
			return "Successfully transferred";
			
		}
		return "Some Error occured while transferring amount. Please try again.";
		
	}
	
    private void validate(final Account accountFrom, final Account accountTo)
            throws AccountNotFoundException, TransferBetweenSameAccountException{

        if (null == accountFrom || null == accountTo){
            throw new AccountNotFoundException("Please check account details.");
        }

        if (accountFrom.getAccountId().equals(accountTo.getAccountId())){
            throw new TransferBetweenSameAccountException("Self Transfer not permitted.");
        }
    }

	 private boolean enoughFunds(final Account account, final BigDecimal amount) {
	        return account.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
	    }

}
