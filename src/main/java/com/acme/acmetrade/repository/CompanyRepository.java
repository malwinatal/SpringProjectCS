package com.acme.acmetrade.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.acmetrade.domain.entities.Company;
import com.acme.acmetrade.domain.entities.MarketSector;
import com.acme.acmetrade.exceptions.CompanyNameOrTickerAlreadyExistsException;
import com.acme.acmetrade.exceptions.MarketOrderNotFoundException;
import com.acme.acmetrade.exceptions.MarketSectorNotFoundException;
import com.acme.acmetrade.exceptions.MarketSectorNameAlreadyExistsException;
import com.acme.acmetrade.exceptions.CompanyNameOrTickerAlreadyExistsException;

@Repository
public class CompanyRepository {

	private final JdbcTemplate jdbcTemplate;
	private final MarketSectorRepository marketSectorRepository;

	@Autowired
	public CompanyRepository(JdbcTemplate jdbcTemplate, MarketSectorRepository marketSectorRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.marketSectorRepository = marketSectorRepository;
	}

	/**
	 * Lists all companies that belong to a given sector
	 * 
	 * @return
	 */
	@Transactional
	public List<Company> getCompaniesBySector(MarketSector marketSector) {
		return jdbcTemplate.query("select * from COMPANY WHERE MARKET_SECTOR_ID = '" + marketSector.getId() + "'",
				new RowMapper<Company>() {
					@Override
					public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
						Company company = new Company();
						company.setId(UUID.fromString(rs.getString("ID")));
						company.setName(rs.getString("NAME"));
						company.setMarketSector(marketSector);
						company.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
						return company;
					}
				});
	}

	/**
	 * Checks if company exists with given name, and if it does not, creates it
	 * 
	 * @param company
	 */
	@Transactional
	public void addCompany(String companyName, String tickerSymbol, UUID sectorId) {

		List<MarketSector> marketSectorsWithGivenID = marketSectorRepository.getMarketSectorById(sectorId);

		if (marketSectorsWithGivenID.isEmpty()) {
			throw new MarketSectorNotFoundException(sectorId);
		} else {
			List<Company> companiesWithGivenNameAndTicker = getCompaniesByNameAndTicker(companyName,tickerSymbol,marketSectorsWithGivenID.get(0));

			if (companiesWithGivenNameAndTicker.isEmpty()) {
				jdbcTemplate.update("insert into COMPANY(ID, NAME, MARKET_SECTOR_ID, TICKER_SYMBOL) values (?,?,?,?)",
						UUID.randomUUID(), companyName, sectorId, tickerSymbol);
			} else {
				throw new CompanyNameOrTickerAlreadyExistsException();
			}

		}

	}

	/**
	 * Looks up for a company by name and ticker symbol
	 * 
	 * @param name
	 * @return
	 */
	@Transactional
	public List<Company> getCompaniesByNameAndTicker(String companyName, String tickerSymbol, MarketSector marketSector) {
		List<Company> Company = jdbcTemplate.query("select * from COMPANY where NAME = '" + companyName + "' OR TICKER_SYMBOL = '" + tickerSymbol + "'",
				new RowMapper<Company>() {
					@Override
					public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
						Company Company = new Company();
						Company.setId(UUID.fromString(rs.getString("ID")));
						Company.setName(rs.getString("NAME"));
						Company.setMarketSector(marketSector);
						Company.setTickerSymbol(rs.getString("TICKER_SYMBOL"));
						return Company;
					}
				});
		if (Company == null) {
			return new ArrayList<Company>();
		}
		return Company;
	}


	public List<Company> listCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateCompanyName(Company company) {
		// TODO Auto-generated method stub

	}

}
