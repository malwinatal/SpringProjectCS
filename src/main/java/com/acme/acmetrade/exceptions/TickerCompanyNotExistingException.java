package com.acme.acmetrade.exceptions;

public class TickerCompanyNotExistingException extends RuntimeException {
	public TickerCompanyNotExistingException(String message) {
		super(message);
	}

}
