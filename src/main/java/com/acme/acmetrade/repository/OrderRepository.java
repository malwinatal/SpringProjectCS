package com.acme.acmetrade.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.acmetrade.domain.OrderSide;
import com.acme.acmetrade.domain.OrderStatus;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.entities.MarketSector;
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
		else {
		//make the check if the ticker company name is included in the listed companies	
		jdbcTemplate.update("insert into MARKET_ORDERS(ORDER_ID, TRADER_ID, TICKER_SYMBOL,"
				+ 			"ORDER_SIDE, ORDER_TYPE, PRICE, VOLUME, PLACEMENT_TIME, ORDER_STATUS)"
				+ "			values (?,?,?,?,?,?,?,?,?)", 
							order.getId(), order.getTraderId(), order.getCompanyTickerSymbol(),
							order.getOrderSide().toString(), order.getOrderType().toString(), order.getPrice(),
							order.getVolume(), order.getPlacementTime(), order.getOrderStatus().toString());
		return order.getId();
		}

	}
	@Transactional
	public void cancelOrder(UUID orderId) {
		
		List<Order> selectedOrder = getOrderById(orderId);
		if (selectedOrder.size() ==0){
			System.out.println("Order not found");
		}
		else {
			UUID selectedOrderId = selectedOrder.get(0).getId();
			OrderStatus selectedOrderStatus = selectedOrder.get(0).getOrderStatus();
			if (selectedOrderStatus == OrderStatus.FULFILLED ) {
				System.out.println("Order already fulfilled");
			}
			else if (selectedOrderStatus == OrderStatus.CANCELLED) {
				System.out.println("Order already cancelled");
			}
			else {
				System.out.println("Update the order");
				jdbcTemplate.update("UPDATE MARKET_ORDERS SET ORDER_STATUS=? WHERE ORDER_ID=?",
						OrderStatus.CANCELLED.toString(), selectedOrderId );
			}
		}

	}
	
	public List<Order> getOrderById(UUID id){
		
		List<Order> selectedOrder = jdbcTemplate.query("select * FROM MARKET_ORDERS WHERE ORDER_ID = '" + id + "'",
			new RowMapper<Order>() {
			@Override
			public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setId(UUID.fromString(rs.getString("ORDER_ID")));
				order.setTraderId(UUID.fromString(rs.getString("TRADER_ID")));
				order.setCompanyTickerSymbol(rs.getString("TICKER_SYMBOL"));
				order.setOrderSide(OrderSide.valueOf(rs.getString("ORDER_SIDE")));
				order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
				order.setPrice(Double.parseDouble(rs.getString("PRICE")));
				order.setVolume(Integer.parseInt(rs.getString("VOLUME")));
				order.setOrderStatus(OrderStatus.valueOf(rs.getString("ORDER_STATUS")));
				order.setPlacementTime(new Date());

				return order;
			}
			});	
		if (selectedOrder == null) {
			return new ArrayList<Order>();
		}
		else {
			return selectedOrder;
		}
	}
	
	
}

	
