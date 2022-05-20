package com.currency.exchange.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.exchange.service.CurrencyExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {
	
	@Autowired
	public CurrencyExchangeService currencyExchangeSvc;
	
	@GetMapping("/from/{fromAccount}/to/{toAccount}/{amount}")
	public String  currencyExchange(@PathVariable("fromAccount") int debitAccount, @PathVariable("toAccount") int creditAccount,
			@PathVariable("amount") BigDecimal amount) throws JsonProcessingException {
		
		return currencyExchangeSvc.exchange(debitAccount,creditAccount, amount);
		
	}
	
}
