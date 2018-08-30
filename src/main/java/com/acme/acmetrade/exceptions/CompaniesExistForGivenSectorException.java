package com.acme.acmetrade.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CompaniesExistForGivenSectorException extends RuntimeException{
	
	public CompaniesExistForGivenSectorException(UUID marketSectorID) {
		super("Error: the market sector with id " + marketSectorID
				+ " is referenced by companies. It could therefore not be deleted.");
	}

}
