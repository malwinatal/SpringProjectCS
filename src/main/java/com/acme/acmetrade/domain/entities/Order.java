package com.acme.acmetrade.domain.entities;

import java.util.Date;
import java.util.UUID;

import com.acme.acmetrade.domain.OrderSide;
import com.acme.acmetrade.domain.OrderStatus;
import com.acme.acmetrade.domain.OrderType;



public class Order {
	private UUID id=UUID.randomUUID();
	private UUID traderId;
	private String companyTickerSymbol;
	private OrderSide OrderSide;
	private OrderType OrderType;
	private double price;
	private int volume;
	private Date placementTime;
	private  OrderStatus OrderStatus;
	
	public Order() {
		
	}
	
	public Order(UUID traderId, String companyTickerSymbol, OrderSide OrderSide, OrderType OrderType, double price, int volume, Date placementTime,
			OrderStatus OrderStatus) {
		
		this.traderId = traderId;
		this.companyTickerSymbol = companyTickerSymbol;
		this.OrderSide = OrderSide;
		this.OrderType = OrderType;
		this.price = price;
		this.volume = volume;
		this.placementTime = placementTime;
		this.OrderStatus = OrderStatus;
	}

	public UUID getTraderId() {
		return traderId;
	}
	

	public void setTraderId(UUID traderId) {
		this.traderId = traderId;
	}

	public String getCompanyTickerSymbol() {
		return companyTickerSymbol;
	}

	public void setCompanyTickerSymbol(String companyTickerSymbol) {
		this.companyTickerSymbol = companyTickerSymbol;
	}

	public OrderSide getOrderSide() {
		return OrderSide;
	}

	public void setOrderSide(OrderSide orderSide) {
		OrderSide = orderSide;
	}

	public OrderType getOrderType() {
		return OrderType;
	}

	public void setOrderType(OrderType orderType) {
		OrderType = orderType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Date getPlacementTime() {
		return placementTime;
	}

	public void setPlacementTime(Date placementTime) {
		this.placementTime = placementTime;
	}

	public OrderStatus getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		OrderStatus = orderStatus;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	
	
	
	

}
