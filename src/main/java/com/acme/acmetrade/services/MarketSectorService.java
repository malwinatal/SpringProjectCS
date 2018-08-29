package com.acme.acmetrade.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.MarketSector;
import com.acme.acmetrade.repository.MarketSectorRepository;

@RestController
@RequestMapping("/marketSector")
public class MarketSectorService {
	
	
	private MarketSectorRepository marketSectorRepository;
	
	@Autowired
	public MarketSectorService(MarketSectorRepository marketSectorRepository) {
		this.marketSectorRepository = marketSectorRepository;
	}
	
	/**
	 * Useful only to get a JSON template of market sector for Postman
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public MarketSector getMarketSector() {
		return new MarketSector();
	}

	/**
	 * Create a MarketSector based on data given by the user
	 * @param marketSector
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public MarketSector createMarketSector(@RequestBody MarketSector marketSector) {
		marketSectorRepository.addMarketSector(marketSector);
		return marketSector;
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<MarketSector> listMarketSectors() {
		return marketSectorRepository.listMarketSectors();
	}
	
	
}
