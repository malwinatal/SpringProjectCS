package com.acme.acmetrade.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.Order;
import com.acme.acmetrade.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderService {
	private List<Order> orderList = new ArrayList<Order>();
	@Autowired
	private OrderRepository myDB;
	
	@RequestMapping(value="/order", method = RequestMethod.POST)
	public void placeOrder(@RequestBody Order order) {
			orderList.add(order);
			//Order myOrder  = new MarketOrder(Currency.EUR, 100, Side.BUY);
			myDB.placeOrder(order);

	}
	
	@RequestMapping(value="/order", method = RequestMethod.PATCH)
	public void cancelOrder(@RequestParam("orderID") String orderID) {
			//orderList.add(order);
			//Order myOrder  = new MarketOrder(Currency.EUR, 100, Side.BUY);
			
			myDB.cancelOrder(UUID.fromString(orderID));

	}
}