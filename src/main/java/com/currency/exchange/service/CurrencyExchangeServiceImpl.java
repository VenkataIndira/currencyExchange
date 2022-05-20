package com.currency.exchange.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.currency.exchange.controller.CurrencyExchangeRatesServiceClient;
import com.currency.exchange.dao.AccountRepository;
import com.currency.exchange.entity.Account;
import com.currency.exchange.exception.CurrencyExchangeProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService{
	
	public AccountRepository currencyExchangeDAO;
	
	public CurrencyExchangeRatesServiceClient currencyExRatesServiceClient ;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String BASE_CURRENCY = "USD";
	
	@Autowired
	public CurrencyExchangeServiceImpl(AccountRepository currencyExchangeDAO, CurrencyExchangeRatesServiceClient currencyExRatesServiceClient) {
		this.currencyExchangeDAO = currencyExchangeDAO;
		this.currencyExRatesServiceClient = currencyExRatesServiceClient;
	}
	
	@Override
	public String exchange(int debitAccId,int creditAccId, BigDecimal amount) throws JsonProcessingException {
		
		Optional<Account> debitAccountOptional = currencyExchangeDAO.findById(debitAccId);
		Optional<Account> creditAccountOptional= currencyExchangeDAO.findById(creditAccId);
		
		
		if(!(debitAccountOptional.isPresent() && creditAccountOptional.isPresent())) {
			throw new CurrencyExchangeProcessingException("Either debit or credit account doesn't exist");
		}
		
		Account debitAccount = debitAccountOptional.get();
		Account creditAccount = creditAccountOptional.get();
		
		String debitAccCurrency = debitAccount.getCurrency();
		String creditAccCurrency = 	creditAccount.getCurrency();
		
		if(debitAccCurrency!=null && creditAccCurrency!=null ) {
			
			if(!debitAccCurrency.equals(BASE_CURRENCY)) {
				throw new CurrencyExchangeProcessingException("The debit account currency should be USD, as exchange "
						+ "rates api->api.currencyfreaks.com uses USD as the base currency ");
			}
			Map<String, String> exchangeRatesMap  = getExchangeRatesMap();
			
			if(!exchangeRatesMap.isEmpty() && exchangeRatesMap.get(creditAccCurrency)== null){
				throw new CurrencyExchangeProcessingException("Exchange rate cannot be retrieved for the currency " + creditAccount.getCurrency());
			}
			BigDecimal amountToBeTransferred = amount.multiply(new BigDecimal(exchangeRatesMap.get(creditAccCurrency)));
			
			BigDecimal debitAccountBal = debitAccount.getBalance();
			BigDecimal creditAccountBal = creditAccount.getBalance();
			
			if(debitAccountBal.compareTo(amount) < 0){
				
				throw new CurrencyExchangeProcessingException("There are no sufficient funds in the debit account");
			}
			
			debitAccount.setBalance(debitAccountBal.subtract(amount));
			creditAccount.setBalance(amountToBeTransferred.add(creditAccountBal));
			
			List<Account> accList = new ArrayList<>();
			
			accList.add(debitAccount);
			
			accList.add(creditAccount);
			try {
				currencyExchangeDAO.saveAll(accList);
			}
			catch(Exception ex)
			{
				throw new CurrencyExchangeProcessingException("Exception occurred while saving the accounts to the database");
			}
  		}
		return "currency exchanged successfully";
	}
	
	
	private Map<String, String> getExchangeRatesMap() throws JsonProcessingException{
		
		String exchangeRatesResponse = currencyExRatesServiceClient.getExchangeRates();
		
		HashMap<String,Object> exchangeRatesResponseMap = objectMapper.readValue(exchangeRatesResponse, new TypeReference<HashMap<String,Object>>() {});
		
		Map<String, String> exchangeRatesMap = (Map<String, String>) exchangeRatesResponseMap.get("rates");
		
		return exchangeRatesMap;
	}
}
