package com.kodewala.ecommerce.model;

/*
 * CartItem represents a product selected by a particular customer.
 *
 * Product = product available in the shop.
 * CartItem = that product inside a customer's cart.
 */
public class CartItem {

	private final int productId;
	private final String productName;
	private final double price;
	private int quantity;

	public CartItem(int productId, String productName, double price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// totalPrice = price * quantity
	public double getTotalPrice() {
		return price * quantity;
	}

	// Make a copy before putting cart items into an Order.
	public CartItem copy() {
		return new CartItem(productId, productName, price, quantity);
	}

	@Override
	public String toString() {
		return "Product ID: " + productId + " | " + productName + " | Price: Rs." + String.format("%.2f", price)
				+ " | Qty: " + quantity + " | Total: Rs." + String.format("%.2f", getTotalPrice());
	}
}
