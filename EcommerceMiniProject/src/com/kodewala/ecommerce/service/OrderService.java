package com.kodewala.ecommerce.service;

import com.kodewala.ecommerce.exception.InsufficientStockException;
import com.kodewala.ecommerce.exception.InvalidOrderException;
import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.OrderStatus;
import com.kodewala.ecommerce.model.Product;
import com.kodewala.ecommerce.repository.OrderRepository;

import java.util.List;

public class OrderService {

	// Repository used to store orders
	private OrderRepository orderRepository;

	// Service objects used to work with customers,
	// products and carts
	private CustomerService customerService;
	private ProductService productService;
	private CartService cartService;

	// Used to generate unique order IDs
	private int nextOrderId = 1000;

	// =====================================================
	// CONSTRUCTOR
	// =====================================================

	public OrderService(OrderRepository orderRepository, CustomerService customerService, ProductService productService,
			CartService cartService) {

		this.orderRepository = orderRepository;
		this.customerService = customerService;
		this.productService = productService;
		this.cartService = cartService;
	}

	// =====================================================
	// 1. PLACE ORDER
	// =====================================================

	public Order placeOrder(int customerId) {

		// First check whether customer exists
		customerService.getCustomerById(customerId);

		// Get a copy of customer's cart
		// We create a copy because original cart
		// will be cleared after successful order
		List<CartItem> items = cartService.createOrderSnapshot(customerId);

		// Cart should not be empty
		if (items.isEmpty()) {

			throw new InvalidOrderException("Cannot place order. Cart is empty.");
		}

		// -------------------------------------------------
		// Check stock for ALL products first
		// -------------------------------------------------

		for (CartItem item : items) {

			Product product = productService.getProductById(item.getProductId());

			if (item.getQuantity() > product.getQuantity()) {

				throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName());
			}
		}

		// -------------------------------------------------
		// Calculate total amount
		// -------------------------------------------------

		double totalAmount = 0;

		for (CartItem item : items) {

			totalAmount += item.getTotalPrice();
		}

		// -------------------------------------------------
		// Reduce product stock
		// -------------------------------------------------

		for (CartItem item : items) {

			Product product = productService.getProductById(item.getProductId());

			int newStock = product.getQuantity() - item.getQuantity();

			productService.updateQuantity(product.getProductId(), newStock);
		}

		// -------------------------------------------------
		// Generate unique order ID
		// -------------------------------------------------

		int orderId = ++nextOrderId;

		// -------------------------------------------------
		// Create Order object
		// -------------------------------------------------

		Order order = new Order(orderId, customerId, items, totalAmount);

		// -------------------------------------------------
		// Save order
		// -------------------------------------------------

		orderRepository.add(order);

		// -------------------------------------------------
		// Clear customer's cart
		// -------------------------------------------------

		cartService.clearCart(customerId);

		// Return created order
		return order;
	}

	// =====================================================
	// 2. GET ORDER BY ID
	// =====================================================

	public Order getOrderById(int orderId) {

		Order order = orderRepository.findById(orderId);

		if (order == null) {

			throw new InvalidOrderException("Order not found with ID: " + orderId);
		}

		return order;
	}

	// =====================================================
	// 3. GET ALL ORDERS
	// =====================================================

	public List<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	// =====================================================
	// 4. GET CUSTOMER ORDERS
	// =====================================================

	public List<Order> getCustomerOrders(int customerId) {

		// First check customer exists
		customerService.getCustomerById(customerId);

		// Get orders belonging to customer
		return orderRepository.findByCustomerId(customerId);
	}

	// =====================================================
	// 5. CANCEL ORDER
	// =====================================================

	public void cancelOrder(int customerId, int orderId) {

		// Find order
		Order order = getOrderById(orderId);

		// Check whether order belongs to customer
		if (order.getCustomerId() != customerId) {

			throw new InvalidOrderException("This order does not belong to this customer");
		}

		// Already cancelled?
		if (order.getOrderStatus() == OrderStatus.CANCELLED) {

			throw new InvalidOrderException("Order is already cancelled");
		}

		// Delivered order cannot be cancelled
		if (order.getOrderStatus() == OrderStatus.DELIVERED) {

			throw new InvalidOrderException("Delivered order cannot be cancelled");
		}

		// -------------------------------------------------
		// Return products back to inventory
		// -------------------------------------------------

		for (CartItem item : order.getItems()) {

			Product product = productService.getProductById(item.getProductId());

			int newStock = product.getQuantity() + item.getQuantity();

			productService.updateQuantity(product.getProductId(), newStock);
		}

		// Change order status
		order.setOrderStatus(OrderStatus.CANCELLED);
	}
}