package com.acme.acmetrade.exceptions;

public class OrderNotUpdatedTickerSymbolException extends RuntimeException{
	public OrderNotUpdatedTickerSymbolException(String message) {
		super(message);
	}
}
