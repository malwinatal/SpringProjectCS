package com.acme.acmetrade.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.acmetrade.domain.entities.Order;


@Repository
@Component
public class OrderRepository {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public OrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
	public void placeOrder(Order order) {
		jdbcTemplate.update("insert into MARKET_ORDERS(ORDER_ID, TRADER_ID, COMPANY_ID,"
				+ 			"ORDER_SIDE, ORDER_TYPE, PRICE, VOLUME, PLACEMENT_TIME, ORDER_STATUS)"
				+ "			values (?,?,?,?,?,?,?,?,?)", 
							order.getId(), order.getTraderId(), order.getCompanyId(),
							order.getOrderSide(), order.getOrderType(), order.getPrice(),
							order.getVolume(), order.getPlacementTime(), order.getOrderStatus());
		
	}

}