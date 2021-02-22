package com.db.asset.management.exception;

@SuppressWarnings("serial")
public class DuplicateAccountIdException extends RuntimeException {

  public DuplicateAccountIdException(String message) {
    super(message);
  }
}
