package com.acme.acmetrade.domain.entities;

import java.util.UUID;

public class MarketSector {

	private UUID id = UUID.randomUUID();
	private String name;
	private String description;
	
	public MarketSector() {}
	
	public MarketSector(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	
	
}
