package com.acme.acmetrade.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class CompanyNameOrTickerAlreadyExistsException extends RuntimeException {

	public CompanyNameOrTickerAlreadyExistsException() {
		super("Error: a company with this name or ticker symbol already exists. Could not be inserted into the DB.");
		
	}
	
}
