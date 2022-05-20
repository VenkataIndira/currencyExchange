package com.currency.exchange.exception;

public class CurrencyExchangeProcessingException extends RuntimeException{
	
	public CurrencyExchangeProcessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public CurrencyExchangeProcessingException(String message) {
		super(message);
	}

	public CurrencyExchangeProcessingException(Throwable cause) {
		super(cause);
	}


}
