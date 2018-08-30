package com.acme.acmetrade.domain.entities;

import java.util.UUID;

public class Company {

	private UUID id = UUID.randomUUID();
	private String name;
	private MarketSector marketSector;
	private String tickerSymbol;

	public Company() {
	}

	public Company(String name, MarketSector marketSector, String tickerSymbol) {
		this.name = name;
		this.marketSector = marketSector;
		this.tickerSymbol = tickerSymbol;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarketSector getMarketSector() {
		return marketSector;
	}

	public void setMarketSector(MarketSector marketSector) {
		this.marketSector = marketSector;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

}
