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
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
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
import com.acme.acmetrade.exceptions.MarketOrderNotFoundException;
import com.acme.acmetrade.exceptions.OrderAlreadyCancelledException;
import com.acme.acmetrade.exceptions.OrderAlreadyFulfilledException;
import com.acme.acmetrade.exceptions.OrderNegativeVolumeException;
import com.acme.acmetrade.exceptions.OrderNotFoundException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedFulfilledOrCancelledException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedTickerSymbolException;



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
			throw new OrderNegativeVolumeException("The volume of the traded order has to be a positive integer");
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
			throw new OrderNotFoundException("The order: " + orderId + " does not exist");
		}
		else {
			UUID selectedOrderId = selectedOrder.get(0).getId();
			OrderStatus selectedOrderStatus = selectedOrder.get(0).getOrderStatus();
			if (selectedOrderStatus == OrderStatus.FULFILLED ) {
				throw new OrderAlreadyFulfilledException("The order: " + selectedOrderId + " has already been fulfilled");
			}
			else if (selectedOrderStatus == OrderStatus.CANCELLED) {
				throw new OrderAlreadyCancelledException("The order: " + selectedOrderId + " has already been cancelled");
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

	/**
	 * Looks for an order based on given id. If it exists, it updates only price, volume and order type. Else throws exception.
	 * @param order
	 */
	@Transactional
	public void updateOrder(Order order) {
		List<Order> ordersWithGivenId = getOrderById(order.getId());
		if (ordersWithGivenId.isEmpty()) {
			throw new OrderNotFoundException("Error: the order with id " + order.getId()
					+ " could not be found therefore not updated.");
		} else {
			if (ordersWithGivenId.get(0).getCompanyTickerSymbol() != order.getCompanyTickerSymbol()
					&& ordersWithGivenId.get(0).getPrice() == order.getPrice()
					&& ordersWithGivenId.get(0).getVolume() == order.getVolume()
					&& ordersWithGivenId.get(0).getOrderType().toString() == order.getOrderType().toString()) {
				throw new OrderNotUpdatedTickerSymbolException("Error: Cannot update stock ticker");
			}
			else {
				if (ordersWithGivenId.get(0).getOrderStatus() == OrderStatus.CANCELLED || ordersWithGivenId.get(0).getOrderStatus() == OrderStatus.FULFILLED) {
					throw new OrderNotUpdatedFulfilledOrCancelledException("Error: Cannot update an already fulfilled/cancelled order");
				}
				else {
					jdbcTemplate.update("update MARKET_ORDERS SET VOLUME = ?, PRICE = ?, ORDER_TYPE = ? where ORDER_ID = ?",
							order.getVolume(), order.getPrice(), order.getOrderType().toString(), order.getId());
				}
				
			}
			
		}
	}


//	public void filterOrder(Map<String, String> customQuery) {
//		// TODO Auto-generated method stub
//		//check how to get multiple rows from a query
//		
//	}

	
//	@Transactional(readOnly=true)
//    public List<Order> findAllOrders() {
//        return jdbcTemplate.query("select * from MARKET_ORDERS",
//                (new RowMapper<Order>() {
//
//                    @Override
//                    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
//                      Order order = new Order();
//                      order.setId(UUID.fromString(rs.getString("ORDER_ID")));
//                      order.setTraderId(UUID.fromString(rs.getString("TRADER_ID")));
//                      order.setCompanyTickerSymbol(rs.getString("TICKER_SYMBOL"));
//                      order.setOrderSide(OrderSide.valueOf(rs.getString("ORDER_SIDE")));
//                      order.setOrderType(OrderType.valueOf(rs.getString("ORDER_TYPE")));
//                      order.setOrderStatus(OrderStatus.valueOf(rs.getString("ORDER_STATUS")));
//                      order.setPrice(rs.getDouble("PRICE"));
//                      order.setVolume(rs.getInt("VOLUME"));
//                      //order.setPlacementTime(placementTime);
//                      return order;
//                    }
//
//                }));
//    }

		
}

	
