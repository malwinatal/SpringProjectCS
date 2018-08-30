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
import com.acme.acmetrade.repository.CompanyRepository;

@RestController
@RequestMapping("/company")
public class CompanyService {

	private CompanyRepository companyRepository;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
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
	 * Create a Company based on data given by the user
	 * 
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Company createCompany(@RequestBody Company Company) {
		// CompanyRepository.addCompany(Company);
		return Company;
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
		// TODO: If at least one parameter is missing or does not have a value, throw
		// exception
		if (companyName == null || companyName.isEmpty())
			throw new IllegalArgumentException("Argument Companyname is missing");

		if (tickerSymbol == null || tickerSymbol.isEmpty())
			throw new IllegalArgumentException("Argument Tickersymbol is missing");

		if (marketSectorId == null || marketSectorId.isEmpty()) {

		} else {
			try {
				UUID marketSectorUuid = UUID.fromString(marketSectorId);
				companyRepository.addCompany(companyName, tickerSymbol, marketSectorUuid);
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
		return companyRepository.listCompany();
	}

	/**
	 * updates a Company name
	 * 
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	public Company updateCompanyName(@RequestBody Company Company) {
		//companyRepository.updateCompanyName(Company);
		return Company;
	}

}