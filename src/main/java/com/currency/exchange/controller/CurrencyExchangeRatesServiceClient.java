package com.currency.exchange.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CurrencyExchangeRatesServiceClient {
	
	private static final String EXCHANGE_RATES_URL = "https://api.currencyfreaks.com/latest?apikey=4cedff46aa1641adaef9c321bcc52c1f";
	
	private RestTemplate  restTemplate;
	
	CurrencyExchangeRatesServiceClient(RestTemplate  restTemplate){
		this.restTemplate = restTemplate;
	}


	public String getExchangeRates() throws JsonProcessingException {
		
		String exchangeRatesResponse = restTemplate.getForEntity(EXCHANGE_RATES_URL, String.class).getBody();
		
        return exchangeRatesResponse;

	}
	

}
