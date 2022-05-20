package com.currency.exchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.currency.exchange.controller.CurrencyExchangeRatesServiceClient;
import com.currency.exchange.dao.AccountRepository;
import com.currency.exchange.entity.Account;
import com.currency.exchange.exception.CurrencyExchangeProcessingException;
import com.currency.exchange.service.CurrencyExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangeServiceTest {
	
	
	@MockBean
	private CurrencyExchangeRatesServiceClient exchangeRatesclient;
	
	
	@MockBean
	public AccountRepository currencyExchangeDAO;
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@BeforeEach
	void init() throws IOException {
			
		Account account1 = new Account(1001,"USD",new BigDecimal(2000.00));
		Account account2 = new Account(1002,"INR",new BigDecimal(20.00));
		
		when(currencyExchangeDAO.findById(1001)).thenReturn(Optional.of(account1));
		when(currencyExchangeDAO.findById(1002)).thenReturn(Optional.of(account2));
		
		Account account3 = new Account(1001,"USD",new BigDecimal(100.00));
		Account account4 = new Account(1002,"INR",new BigDecimal(10.00));
		
		when(currencyExchangeDAO.findById(1003)).thenReturn(Optional.of(account3));
		when(currencyExchangeDAO.findById(1004)).thenReturn(Optional.of(account4));
		
		Account account5 = new Account(1001,"USD",new BigDecimal(1000.00));
		Account account6 = new Account(1002,"INR",new BigDecimal(50.00));
		
		when(currencyExchangeDAO.findById(1005)).thenReturn(Optional.of(account5));
		when(currencyExchangeDAO.findById(1006)).thenReturn(Optional.of(account6));
		
		Path p = Paths.get("src\\test\\resources\\sampleRates.txt");
		String strRates = Files.readString(p.toAbsolutePath());
		
		when(exchangeRatesclient.getExchangeRates()).thenReturn(strRates);
		
		List<Account> accountList = new ArrayList();
		accountList.add(account1);
		accountList.add(account2);
		
		//when(currencyExchangeDAO.saveAll(accountList)).thenReturn(accountList);
	}

	@Test
	void currencyExchangeRatesControllerTest() throws JsonProcessingException {
		
		String s = currencyExchangeService.exchange(1001, 1002, new BigDecimal(300));
		
		assertEquals("currency exchanged successfully", s);
	}
	
	@Test
	void currencyExchangeRatesControllerExceptionTest_01() throws JsonProcessingException,CurrencyExchangeProcessingException {
		
		CurrencyExchangeProcessingException exception = assertThrows(CurrencyExchangeProcessingException.class, () -> {
			currencyExchangeService.exchange(1003, 1004, new BigDecimal(200));
	  });

	  assertEquals("There are no sufficient funds in the debit account", exception.getMessage());
	}
	
	@Test
	void currencyExchangeRatesControllerExceptionTest_02() throws JsonProcessingException,CurrencyExchangeProcessingException {
		
		CurrencyExchangeProcessingException exception = assertThrows(CurrencyExchangeProcessingException.class, () -> {
			currencyExchangeService.exchange(100, 1006, new BigDecimal(200));
	  });

	  assertEquals("Either debit or credit account doesn't exist", exception.getMessage());
	}
			
}

