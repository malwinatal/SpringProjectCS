package com.acme.acmetrade.exceptions;

public class MarketSectorNameAlreadyExistsException extends Exception{
	
	public MarketSectorNameAlreadyExistsException(String message) {
		super("Error: a market sector already exists with this name. Could not insert in the DB.");
		
	}

}
