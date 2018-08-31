package com.acme.acmetrade.exceptions;


public class OrderNotUpdatedFulfilledOrCancelledException extends RuntimeException {
	public OrderNotUpdatedFulfilledOrCancelledException(String message) {
		super(message);
	}
}
