package com.acme.acmetrade.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.Company;
import com.acme.acmetrade.repository.CompanyRepository;


@RestController
@RequestMapping("/company")
public class CompanyService {
	
	private CompanyRepository CompanyRepository;
	
	@Autowired
	public CompanyService(CompanyRepository CompanyRepository) {
		this.CompanyRepository = CompanyRepository;
	}
	
	/**
	 * Useful only to get a JSON template of market sector for Postman
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Company getCompany() {
		return new Company();
	}

	/**
	 * Create a Company based on data given by the user
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Company createCompany(@RequestBody Company Company) {
		CompanyRepository.addCompany(Company);
		return Company;
	}
	
	/**
	 * Lists all Company
	 * @return
	 */
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public List<Company> listCompany() {
		return CompanyRepository.listCompany();
	}
	
	/**
	 * updates a Company name
	 * @param Company
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PATCH)
	public Company updateCompanyName(@RequestBody Company Company) {
		CompanyRepository.updateCompanyName(Company);
		return Company;
	}
	
	

}
