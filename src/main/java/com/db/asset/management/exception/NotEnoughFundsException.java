package com.db.asset.management.exception;

@SuppressWarnings("serial")
public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException(String message){
        super(message);
    }
}
