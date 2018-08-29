package com.acme.acmetrade.domain.entities;

import java.util.Date;
import java.util.UUID;

import com.acme.acmetrade.domain.OrderSide;
import com.acme.acmetrade.domain.OrderStatus;
import com.acme.acmetrade.domain.OrderType;

public class Order {
	private UUID id=UUID.randomUUID();
	private UUID traderId;
	private UUID companyId;
	private OrderSide OrderSide;
	private OrderType OrderType;
	private double price;
	private int volume;
	private Date placementTime;
	private  OrderStatus OrderStatus;
	
	public Order(UUID traderId, UUID companyId, OrderSide OrderSide, OrderType OrderType, double price, int volume, Date placementTime,
			OrderStatus OrderStatus) {
		super();
		this.traderId = traderId;
		this.companyId = companyId;
		this.OrderSide = OrderSide;
		this.OrderType = OrderType;
		this.price = price;
		this.volume = volume;
		this.placementTime = placementTime;
		this.OrderStatus = OrderStatus;
	}
	
	
	

}
