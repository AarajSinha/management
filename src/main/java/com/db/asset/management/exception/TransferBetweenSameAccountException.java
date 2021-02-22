package com.db.asset.management.exception;

@SuppressWarnings("serial")
public class TransferBetweenSameAccountException extends RuntimeException {

    public TransferBetweenSameAccountException(String message){
        super(message);
    }

}
