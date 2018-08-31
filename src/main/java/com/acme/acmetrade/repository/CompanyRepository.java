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

	@Autowired
	public CompanyRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
	public void addCompany(String companyName, String tickerSymbol, MarketSector marketSector) {

		List<Company> companiesWithGivenNameAndTicker = getCompaniesByNameAndTicker(companyName, tickerSymbol,
				marketSector);

		if (companiesWithGivenNameAndTicker.isEmpty()) {
			jdbcTemplate.update("insert into COMPANY(ID, NAME, MARKET_SECTOR_ID, TICKER_SYMBOL) values (?,?,?,?)",
					UUID.randomUUID(), companyName, marketSector.getId(), tickerSymbol);
		} else {
			throw new CompanyNameOrTickerAlreadyExistsException();
		}
	}


/**
 * Looks up for a company by name and ticker symbol
 * @param companyName
 * @param tickerSymbol
 * @param marketSector
 * @return
 */
	@Transactional
	public List<Company> getCompaniesByNameAndTicker(String companyName, String tickerSymbol,
			MarketSector marketSector) {
		List<Company> companies = jdbcTemplate.query(
				"select * from COMPANY where NAME = '" + companyName + "' OR TICKER_SYMBOL = '" + tickerSymbol + "'",
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
		if (companies == null) {
			return new ArrayList<Company>();
		}
		return companies;
	}

	/**
	 * Lists all companies in the database
	 * @return
	 */
	public List<Company> listCompanies() {
		List<Company> companies = jdbcTemplate.query(
				"select * from COMPANY c, MARKET_SECTOR ms where c.market_sector_id = ms.id",
				new RowMapper<Company>() {
					@Override
					public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
						Company Company = new Company();
						MarketSector marketSector = new MarketSector();
						marketSector.setId(UUID.fromString(rs.getString("MARKET_SECTOR.ID")));
						marketSector.setName(rs.getString("MARKET_SECTOR.NAME"));
						marketSector.setDescription(rs.getString("MARKET_SECTOR.DESCRIPTION"));
						Company.setId(UUID.fromString(rs.getString("COMPANY.ID")));
						Company.setName(rs.getString("COMPANY.NAME"));
						Company.setMarketSector(marketSector);
						Company.setTickerSymbol(rs.getString("COMPANY.TICKER_SYMBOL"));
						return Company;
					}
				});
		if (companies == null) {
			return new ArrayList<Company>();
		}
		return companies;
	}

}
