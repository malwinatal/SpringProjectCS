package com.acme.acmetrade.repository;

import java.util.UUID;

import org.apache.http.HttpException;
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
	public UUID placeOrder(Order order) {
		if (order.getVolume() < 0) {
			System.out.println("ERROR 400: BAD REQUEST");
			return null;
			
		}
		
	
		else
		{
		jdbcTemplate.update("insert into MARKET_ORDERS(ORDER_ID, TRADER_ID, TICKER_SYMBOL,"
				+ 			"ORDER_SIDE, ORDER_TYPE, PRICE, VOLUME, PLACEMENT_TIME, ORDER_STATUS)"
				+ "			values (?,?,?,?,?,?,?,?,?)", 
							order.getId(), order.getTraderId(), order.getCompanyTickerSymbol(),
							order.getOrderSide().toString(), order.getOrderType().toString(), order.getPrice(),
							order.getVolume(), order.getPlacementTime(), order.getOrderStatus().toString());
		return order.getId();
		}
	}


}