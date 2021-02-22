package com.db.asset.management.exception;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}
