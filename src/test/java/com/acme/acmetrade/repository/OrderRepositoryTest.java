package com.acme.acmetrade.repository;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.acmetrade.TradeApplication;
import com.acme.acmetrade.domain.OrderSide;
import com.acme.acmetrade.domain.OrderStatus;
import com.acme.acmetrade.domain.OrderType;
import com.acme.acmetrade.domain.entities.Order;
import com.acme.acmetrade.repository.OrderRepository;
import com.acme.acmetrade.exceptions.OrderNotFoundException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedFulfilledOrCancelledException;
import com.acme.acmetrade.exceptions.OrderNotUpdatedTickerSymbolException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TradeApplication.class})
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	public void updatingOrderTypeSuccessfully() {
	
		Order order = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.OPEN);
		orderRepository.placeOrder(order);
		List<Order> marketOrders = orderRepository.getOrderById(order.getId());
		Order marketOrder = marketOrders.get(0);
		Order updatedOrder = new Order(marketOrder.getTraderId(),marketOrder.getCompanyTickerSymbol(),marketOrder.getOrderSide(),OrderType.MARKET,marketOrder.getPrice(),marketOrder.getVolume(),marketOrder.getPlacementTime(), marketOrder.getOrderStatus());
		updatedOrder.setId(marketOrder.getId());
		orderRepository.updateOrder(marketOrder);
	}
	
	@Test(expected= OrderNotUpdatedFulfilledOrCancelledException.class)
	public void updatingOrderAlreadyFulfilled() {
	
		Order order = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.FULFILLED);
		orderRepository.placeOrder(order);
		List<Order> marketOrders = orderRepository.getOrderById(order.getId());
		Order marketOrder = marketOrders.get(0);
		Order updatedOrder = new Order(marketOrder.getTraderId(),marketOrder.getCompanyTickerSymbol(),marketOrder.getOrderSide(),OrderType.MARKET,marketOrder.getPrice(),marketOrder.getVolume(),marketOrder.getPlacementTime(), marketOrder.getOrderStatus());
		updatedOrder.setId(marketOrder.getId());
		orderRepository.updateOrder(updatedOrder);
	}
	
	@Test(expected= OrderNotUpdatedFulfilledOrCancelledException.class)
	public void updatingOrderAlreadyCancelled() {
	
		Order order = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.CANCELLED);
		orderRepository.placeOrder(order);
		List<Order> marketOrders = orderRepository.getOrderById(order.getId());
		Order marketOrder = marketOrders.get(0);
		Order updatedOrder = new Order(marketOrder.getTraderId(),marketOrder.getCompanyTickerSymbol(),marketOrder.getOrderSide(),OrderType.MARKET,marketOrder.getPrice(),marketOrder.getVolume(),marketOrder.getPlacementTime(), marketOrder.getOrderStatus());
		updatedOrder.setId(marketOrder.getId());
		orderRepository.updateOrder(updatedOrder);
	}
	
	@Test(expected= OrderNotUpdatedTickerSymbolException.class)
	public void updatingOrderTickerSymbol() {
	
		Order order = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.CANCELLED);
		orderRepository.placeOrder(order);
		List<Order> marketOrders = orderRepository.getOrderById(order.getId());
		Order marketOrder = marketOrders.get(0);
		Order updatedOrder = new Order(marketOrder.getTraderId(),"GOOGL",marketOrder.getOrderSide(),marketOrder.getOrderType(),marketOrder.getPrice(),marketOrder.getVolume(),marketOrder.getPlacementTime(), marketOrder.getOrderStatus());
		updatedOrder.setId(marketOrder.getId());
		orderRepository.updateOrder(updatedOrder);
	}
	
	@Test(expected= OrderNotFoundException.class)
	public void updatingNonExistingOrder() {
	
		Order order = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.CANCELLED);
		orderRepository.placeOrder(order);
		List<Order> marketOrders = orderRepository.getOrderById(order.getId());
		Order marketOrder = marketOrders.get(0);
		System.out.println(marketOrder.getId());
		Order updatedOrder = new Order(UUID.fromString("163713cd-842c-4979-9f0a-f5def81bdbd4"),"AAPL",OrderSide.BUY,OrderType.LIMITED,3,100,new Date(), OrderStatus.CANCELLED);
		orderRepository.updateOrder(updatedOrder);
	}

}
