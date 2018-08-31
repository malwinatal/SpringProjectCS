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
import com.acme.acmetrade.exceptions.CompaniesExistForGivenSectorException;
import com.acme.acmetrade.exceptions.MarketOrderNotFoundException;
import com.acme.acmetrade.exceptions.MarketSectorNameAlreadyExistsException;
import com.acme.acmetrade.exceptions.MarketSectorNotFoundException;

@Repository
public class MarketSectorRepository {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MarketSectorRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Checks if market sector exists with given name, and if it does not, creates
	 * it
	 * 
	 * @param marketSector
	 */
	@Transactional
	public void addMarketSector(MarketSector marketSector) {

		List<MarketSector> marketSectorsWithGivenName = getMarketSectorByName(marketSector.getName());

		if (marketSectorsWithGivenName.isEmpty()) {
			jdbcTemplate.update("insert into MARKET_SECTOR(ID, NAME, DESCRIPTION) values (?,?,?)", marketSector.getId(),
					marketSector.getName(), marketSector.getDescription());
		} else {
			throw new MarketSectorNameAlreadyExistsException(marketSector.getName());
		}

	}

	/**
	 * Lists all market orders
	 * 
	 * @return
	 */
	@Transactional
	public List<MarketSector> listMarketSectors() {
		return jdbcTemplate.query("select * from MARKET_SECTOR", new RowMapper<MarketSector>() {
			@Override
			public MarketSector mapRow(ResultSet rs, int rowNum) throws SQLException {
				MarketSector marketSector = new MarketSector();
				marketSector.setId(UUID.fromString(rs.getString("ID")));
				marketSector.setName(rs.getString("NAME"));
				marketSector.setDescription(rs.getString("DESCRIPTION"));
				return marketSector;
			}
		});
	}

	/**
	 * Looks up for a market sector by name
	 * 
	 * @param name
	 * @return
	 */
	@Transactional
	public List<MarketSector> getMarketSectorByName(String name) {
		List<MarketSector> marketSectors = jdbcTemplate.query("select * from MARKET_SECTOR where NAME = '" + name + "'",
				new RowMapper<MarketSector>() {
					@Override
					public MarketSector mapRow(ResultSet rs, int rowNum) throws SQLException {
						MarketSector marketSector = new MarketSector();
						marketSector.setId(UUID.fromString(rs.getString("ID")));
						marketSector.setName(rs.getString("NAME"));
						marketSector.setDescription(rs.getString("DESCRIPTION"));
						return marketSector;
					}
				});
		if (marketSectors == null) {
			return new ArrayList<MarketSector>();
		}
		return marketSectors;
	}

	/**
	 * Looks for a market sector based on given name. If it exists, updates it. Else
	 * throws exception.
	 * 
	 * @param marketSector
	 */
	public void updateMarketSectorDescription(MarketSector marketSector) {
		List<MarketSector> marketSectorsWithGivenName = getMarketSectorByName(marketSector.getName());

		if (marketSectorsWithGivenName.isEmpty()) {
			throw new MarketOrderNotFoundException("Error: the market sector with name " + marketSector.getName()
					+ " could not be found therefore not updated.");
		} else {
			jdbcTemplate.update("update MARKET_SECTOR SET DESCRIPTION = ? where name = ?",
					marketSector.getDescription(), marketSector.getName());
		}

	}

	public void deleteMarketSectorById(UUID marketSectorID) {

//		if (companiesOfGivenSector.isEmpty()) {
//			jdbcTemplate.update("DELETE FROM MARKET_SECTOR where ID = ?", marketSectorID);
//		} else {
//			throw new CompaniesExistForGivenSectorException(marketSectorID);
//		}


	}

	/**
	 * Looks up for a market sector by id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public List<MarketSector> getMarketSectorById(UUID id) {
		List<MarketSector> marketSectors = jdbcTemplate.query("select * from MARKET_SECTOR where ID = '" + id + "'",
				new RowMapper<MarketSector>() {
					@Override
					public MarketSector mapRow(ResultSet rs, int rowNum) throws SQLException {
						MarketSector marketSector = new MarketSector();
						marketSector.setId(UUID.fromString(rs.getString("ID")));
						marketSector.setName(rs.getString("NAME"));
						marketSector.setDescription(rs.getString("DESCRIPTION"));
						return marketSector;
					}
				});
		if (marketSectors == null) {
			return new ArrayList<MarketSector>();
		}
		return marketSectors;
	}

}
