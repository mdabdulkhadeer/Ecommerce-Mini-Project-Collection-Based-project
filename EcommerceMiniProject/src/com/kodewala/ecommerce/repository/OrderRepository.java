package com.kodewala.ecommerce.repository;

import com.kodewala.ecommerce.model.Order;

import java.util.ArrayList;
import java.util.List;

/*
 * Stores all orders in an ArrayList.
 */
public class OrderRepository {

	private final List<Order> orders = new ArrayList<>();

	public void add(Order order) {
		orders.add(order);
	}

	public List<Order> findAll() {
		return new ArrayList<>(orders);
	}

	public Order findById(int orderId) {

		for (Order order : orders) {
			if (order.getOrderId() == orderId) {
				return order;
			}
		}

		return null;
	}

	public List<Order> findByCustomerId(int customerId) {

		List<Order> customerOrders = new ArrayList<>();

		for (Order order : orders) {
			if (order.getCustomerId() == customerId) {
				customerOrders.add(order);
			}
		}

		return customerOrders;
	}
}
