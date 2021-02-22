package com.db.asset.management.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.exception.AccountNotFoundException;
import com.db.asset.management.exception.NotEnoughFundsException;
import com.db.asset.management.exception.TransferBetweenSameAccountException;
import com.db.asset.management.service.AccountsService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private AccountsService accountsService;
    
    @PutMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> transfer(@RequestBody @Valid AmountTransfer transfer) {
    	String response;
        try {
            response = accountsService.transferAmount(transfer);
        } catch (AccountNotFoundException ane) {
            return new ResponseEntity<>(ane.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NotEnoughFundsException nbe) {
            return new ResponseEntity<>(nbe.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TransferBetweenSameAccountException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
}
