package com.acme.acmetrade.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.acmetrade.domain.entities.MarketSector;
import com.acme.acmetrade.exceptions.MarketSectorNameAlreadyExistsException;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MarketSectorRepositoryTest {

	private final static MarketSector VALID_MARKET_SECTOR = new MarketSector("Energy", "Sector related to energy");
	private final static MarketSector DUPLICATE_NAME_VALID_MARKET_SECTOR = new MarketSector("Energy", "Sector related to energy, same name");

	@Autowired
	private MarketSectorRepository marketSectorRepository;

	@Test
	public void addValidMarketSector() {
		marketSectorRepository.addMarketSector(VALID_MARKET_SECTOR);
	}
	
	@Test(expected=MarketSectorNameAlreadyExistsException.class)
	public void addMarketSector() {
		marketSectorRepository.addMarketSector(VALID_MARKET_SECTOR);
		marketSectorRepository.addMarketSector(DUPLICATE_NAME_VALID_MARKET_SECTOR);
	}
	@Test
	public void getMarketSectorByName() {
		List<MarketSector> marketSectorWithGivenName = marketSectorRepository.getMarketSectorByName(VALID_MARKET_SECTOR.getName());
		assertEquals(0, marketSectorWithGivenName.size());
		
		marketSectorRepository.addMarketSector(VALID_MARKET_SECTOR);
		marketSectorWithGivenName = marketSectorRepository.getMarketSectorByName(VALID_MARKET_SECTOR.getName());
		assertEquals(1, marketSectorWithGivenName.size());
	}
	
	@Test
	public void getMarketSectorById() {
		List<MarketSector> marketSectorWithGivenName = marketSectorRepository.getMarketSectorById(VALID_MARKET_SECTOR.getId());
		assertEquals(0, marketSectorWithGivenName.size());
		
		marketSectorRepository.addMarketSector(VALID_MARKET_SECTOR);
		marketSectorWithGivenName = marketSectorRepository.getMarketSectorById(VALID_MARKET_SECTOR.getId());
		assertEquals(1, marketSectorWithGivenName.size());
	}
	

}
