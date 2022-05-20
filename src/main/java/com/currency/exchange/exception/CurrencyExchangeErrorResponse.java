package com.currency.exchange.exception;

public class CurrencyExchangeErrorResponse {
	
	String errorCode;
	String errorMsg;
	String timeStamp;
	
	public CurrencyExchangeErrorResponse(String errorCode, String errorMsg, String timeStamp) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.timeStamp = timeStamp;
	}

	public CurrencyExchangeErrorResponse() {
		
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
