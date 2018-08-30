package com.acme.acmetrade.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OrderAlreadyCancelledException extends RuntimeException{
	public OrderAlreadyCancelledException(String message) {
		super(message);
	}

}
