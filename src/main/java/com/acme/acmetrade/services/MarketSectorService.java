package com.acme.acmetrade.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.Company;
import com.acme.acmetrade.domain.entities.MarketSector;
import com.acme.acmetrade.exceptions.CompaniesExistForGivenSectorException;
import com.acme.acmetrade.exceptions.MarketSectorNotFoundException;
import com.acme.acmetrade.repository.CompanyRepository;
import com.acme.acmetrade.repository.MarketSectorRepository;

@RestController
@RequestMapping("/marketSector")
public class MarketSectorService {

	private MarketSectorRepository marketSectorRepository;
	
	private CompanyRepository companyRepository;

	@Autowired
	public MarketSectorService(MarketSectorRepository marketSectorRepository, CompanyRepository companyRepository) {
		this.marketSectorRepository = marketSectorRepository;
		this.companyRepository = companyRepository;
	}

	/**
	 * Useful only to get a JSON template of market sector for Postman
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public MarketSector getMarketSector() {
		return new MarketSector();
	}

	/**
	 * Get a market sector by id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/byId/{marketSectorId}", method = RequestMethod.GET)
	public List getMarketSectorById(@PathVariable("marketSectorId") String marketSectorId) {

		if (marketSectorId == null || marketSectorId.isEmpty()) {
			throw new IllegalArgumentException("The given marketSectorId is not specified");
		}

		try {
			UUID marketSectorUUID = UUID.fromString(marketSectorId);
			List<MarketSector> marketSectors = marketSectorRepository.getMarketSectorById(marketSectorUUID);
			
			List marketSectorAndAssociatedCompanies = new ArrayList<>();
			
			if(!marketSectors.isEmpty()) {
				marketSectorAndAssociatedCompanies.add(marketSectors.get(0));
				List<Company> companiesOfGiveSector = companyRepository.getCompaniesBySector(marketSectors.get(0));
				
				if(!companiesOfGiveSector.isEmpty()) {
					marketSectorAndAssociatedCompanies.add(companiesOfGiveSector);
				}
			} 

			
			
			return marketSectorAndAssociatedCompanies;

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The given marketSectorId does not have the right pattern");
		}


	}

	/**
	 * Create a MarketSector based on data given by the user
	 * 
	 * @param marketSector
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public MarketSector createMarketSector(@RequestBody MarketSector marketSector) {
		marketSectorRepository.addMarketSector(marketSector);
		return marketSector;
	}

	/**
	 * Lists all market sectors
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<MarketSector> listMarketSectors() {
		return marketSectorRepository.listMarketSectors();
	}

	/**
	 * updates a market order's description
	 * 
	 * @param marketSector
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	public MarketSector updateMarketSectorDescription(@RequestBody MarketSector marketSector) {
		marketSectorRepository.updateMarketSectorDescription(marketSector);
		return marketSector;
	}

	/**
	 * Deletes a market sector by id
	 * @param id
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteMarketOrderById(@RequestParam("id") String id) {
		if (id == null || id.trim().isEmpty())
			throw new IllegalArgumentException("Invalid market order id provided");

		UUID marketSectorUUID = null;
		try {
			marketSectorUUID = UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("id not parsable");
		}

		List<MarketSector> marketSectorsWithGivenId = marketSectorRepository.getMarketSectorById(marketSectorUUID);

	
		if (marketSectorsWithGivenId.isEmpty()) {
			throw new MarketSectorNotFoundException(marketSectorUUID);
		} else {
			// we only care about the first market sector in the list (there should be only
			// one due to unicity)
			List<Company> companiesOfGivenSector = companyRepository.getCompaniesBySector(marketSectorsWithGivenId.get(0));
			
			if (companiesOfGivenSector.isEmpty()) {
				marketSectorRepository.deleteMarketSectorById(marketSectorUUID);
			} else {
				throw new CompaniesExistForGivenSectorException(marketSectorUUID);
			}

		}
		// marketSectorRepository.deleteMarketSectorById(UUID.fromString(id));
	}

}
