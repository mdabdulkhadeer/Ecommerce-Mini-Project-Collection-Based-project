package com.kodewala.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Order represents a successfully placed purchase.
 */
public class Order {

	private final int orderId;
	private final int customerId;
	private final List<CartItem> items;
	private final double totalAmount;
	private OrderStatus orderStatus;
	private final LocalDateTime orderDate;

	public Order(int orderId, int customerId, List<CartItem> items, double totalAmount) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.items = items;
		this.totalAmount = totalAmount;

		// Every new order starts with PLACED status.
		this.orderStatus = OrderStatus.PLACED;

		// Store the date and time when the order was created.
		this.orderDate = LocalDateTime.now();
	}

	public int getOrderId() {
		return orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "Order ID: " + orderId + " | Customer ID: " + customerId + " | Date: " + orderDate + " | Amount: Rs."
				+ String.format("%.2f", totalAmount) + " | Status: " + orderStatus;
	}
}
