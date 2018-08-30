package com.acme.acmetrade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CompaniesExistForGivenSectorException extends RuntimeException{
	
	public CompaniesExistForGivenSectorException(String message) {
		super(message);
		
	}

}
