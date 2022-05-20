package com.currency.exchange.service;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CurrencyExchangeService {
	
	public String exchange(int debitAccId,int creditAccId, BigDecimal amount) throws JsonProcessingException;

}
