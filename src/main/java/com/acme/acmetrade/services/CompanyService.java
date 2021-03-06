package com.acme.acmetrade.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.Company;
import com.acme.acmetrade.domain.entities.MarketSector;
import com.acme.acmetrade.exceptions.MarketSectorNotFoundException;
import com.acme.acmetrade.repository.CompanyRepository;
import com.acme.acmetrade.repository.MarketSectorRepository;

@RestController
@RequestMapping("/company")
public class CompanyService {

	private CompanyRepository companyRepository;
	private MarketSectorRepository marketSectorRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository, MarketSectorRepository marketSectorRepository) {
		this.companyRepository = companyRepository;
		this.marketSectorRepository = marketSectorRepository;
	}

	/**
	 * Useful only to get a JSON template of market sector for Postman
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Company getCompany() {
		return new Company();
	}

	/**
	 * Create a Company based on data given by the user in the URL
	 * 
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void createCompany(@RequestParam("name") String companyName,
			@RequestParam("tickersymbol") String tickerSymbol, @RequestParam("sectorId") String marketSectorId) {

		if (companyName == null || companyName.isEmpty())
			throw new IllegalArgumentException("Argument companyName is missing");

		if (tickerSymbol == null || tickerSymbol.isEmpty())
			throw new IllegalArgumentException("Argument tickerSymbol is missing");

		if (marketSectorId == null || marketSectorId.isEmpty()) {
			throw new IllegalArgumentException("Argument sectorId is missing");
		} else {
			try {
				UUID marketSectorUuid = UUID.fromString(marketSectorId);

				List<MarketSector> marketSectorsWithGivenID = marketSectorRepository
						.getMarketSectorById(marketSectorUuid);

				if (marketSectorsWithGivenID.isEmpty()) {
					throw new MarketSectorNotFoundException(marketSectorUuid);
				} else {
					companyRepository.addCompany(companyName, tickerSymbol, marketSectorsWithGivenID.get(0));
				}

			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("MarketSector ID has a bad format");
			}
		}

	}

	/**
	 * Lists all Company
	 * 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Company> listCompany() {
		return companyRepository.listCompanies();
	}

	/**
	 * updates a Company name
	 * 
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	public Company updateCompanyName(@RequestBody Company company) {
		// companyRepository.updateCompanyName(Company);
		return company;
	}

}