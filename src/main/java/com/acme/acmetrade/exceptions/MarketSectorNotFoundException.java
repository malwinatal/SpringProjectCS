package com.acme.acmetrade.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MarketSectorNotFoundException extends RuntimeException{
	
	public MarketSectorNotFoundException(UUID id) {
		super("Could not find Market Sector with id " + id);	
	}

}
