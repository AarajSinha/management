package com.db.asset.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.db.asset.management.dao.AmountTransfer;
import com.db.asset.management.service.AccountsService;
import com.fasterxml.jackson.core.JsonProcessingException;


@ExtendWith(SpringExtension.class)
public class TestAccountController {	
	
	@Autowired
	private MockMvc mockMvc;
	
	
	
	public void makeTransfer() throws JsonProcessingException, Exception {
		AmountTransfer transfer = new AmountTransfer("1", "2", BigDecimal.TEN);		
		
		mockMvc.perform(put("/transfer").contentType(MediaType.APPLICATION_JSON)
				.content("{\"accountFromId\":\"Id-1\",\"accountToId\":\"Id-2\",\"amount\":1000}")).andExpect(status().isOk());

	}

}
