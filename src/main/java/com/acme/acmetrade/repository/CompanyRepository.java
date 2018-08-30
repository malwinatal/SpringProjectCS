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
import com.acme.acmetrade.exceptions.MarketOrderNotFoundException;
import com.acme.acmetrade.exceptions.MarketSectorNameAlreadyExistsException;

@Repository
public class CompanyRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CompanyRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

	/**
	 * Lists all companies that belong to a given sector
	 * @return
	 */
	@Transactional
	public List<Company> getCompaniesBySectorId(MarketSector marketSector) {
		return jdbcTemplate.query("select * from COMPANY WHERE MARKET_SECTOR_ID = '" + marketSector.getId() + "'", new RowMapper<Company>() {
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

	

}
