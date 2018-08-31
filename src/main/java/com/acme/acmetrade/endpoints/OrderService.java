package com.acme.acmetrade.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.acmetrade.domain.entities.Order;
import com.acme.acmetrade.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderService {
	private List<Order> orderList = new ArrayList<Order>();
	@Autowired
	private OrderRepository myDB;
	
	@RequestMapping(value="/order", method = RequestMethod.POST)
	public void placeOrder(@RequestBody Order order) {
			orderList.add(order);
			myDB.placeOrder(order);

	}
	
	@RequestMapping(value="/order", method = RequestMethod.PATCH)
	public void cancelOrder(@RequestParam("orderID") String orderID) {
			myDB.cancelOrder(UUID.fromString(orderID));
	}
	
	@RequestMapping(value="/order/updatingWithId", method = RequestMethod.PATCH) //change URI
	public void updateOrder(@RequestBody Order order) {
			myDB.updateOrder(order);
	}
//	@RequestMapping(value="/order/", method = RequestMethod.GET) //change URI
//	public void filterOrder(@RequestParam Map<String, String> customQuery) {
//		System.out.println(customQuery.containsKey("price"));
//		System.out.println(customQuery.get("price"));
//		System.out.println(customQuery.get("volume"));
//		myDB.filterOrder(customQuery);
//		return;
//
//	}
	
	
}