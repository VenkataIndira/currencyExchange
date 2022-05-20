package com.currency.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CurrencyExchangeExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<CurrencyExchangeErrorResponse> handleException(CurrencyExchangeProcessingException ex){
		
		CurrencyExchangeErrorResponse error = new CurrencyExchangeErrorResponse();
		
		error.setErrorCode(HttpStatus.NOT_FOUND.toString());
		error.setErrorMsg(ex.getMessage());
		error.setTimeStamp(Long.valueOf((System.currentTimeMillis())).toString());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
		
	}
	
	
	@ExceptionHandler
	public ResponseEntity<CurrencyExchangeErrorResponse> handleException(Exception ex){
		
		CurrencyExchangeErrorResponse error = new CurrencyExchangeErrorResponse();
		
		error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
		error.setErrorMsg("Exception occurred while currency exchange processing");
		error.setTimeStamp(Long.valueOf((System.currentTimeMillis())).toString());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
		
	}

}
