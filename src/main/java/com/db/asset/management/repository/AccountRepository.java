package com.db.asset.management.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.db.asset.management.dao.Account;
import com.db.asset.management.dao.AccountUpdate;
import com.db.asset.management.exception.DuplicateAccountIdException;

@Repository
public class AccountRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();	

	public void createAccount(Account account) throws DuplicateAccountIdException {
        Account existingAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (existingAccount != null) {
            throw new DuplicateAccountIdException(
                    "Account id " + account.getAccountId() + " already exists!");
        }
    }

	public Account getAccount(String accountId) {
		return accounts.get(accountId);		
    }

	
	public boolean updateAccounts(List<AccountUpdate> accountUpdates) {
		accountUpdates.stream().forEach(this::updateAccount);
		return true;
	}

	private void updateAccount(final AccountUpdate accountUpdate) {
		final String accountId = accountUpdate.getAccountId();
		accounts.computeIfPresent(accountId, (key, account) -> {
			account.setBalance(account.getBalance().add(accountUpdate.getAmount()));
			return account;
		});
	}
}
