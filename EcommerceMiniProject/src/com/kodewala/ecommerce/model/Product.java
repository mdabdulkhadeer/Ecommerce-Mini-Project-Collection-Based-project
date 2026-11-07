package com.kodewala.ecommerce.model;

/*
 * Product represents one product in our shop.
 */
public class Product {

	private final int productId;
	private String productName;
	private String category;
	private double price;
	private int quantity;
	private String brand;

	public Product(int productId, String productName, String category, double price, int quantity, String brand) {
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.brand = brand;
	}

	// Getters are used to read private data.
	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getBrand() {
		return brand;
	}

	// Setters are used when admin updates price or stock.
	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Used after a successful order.
	public void reduceStock(int quantity) {
		this.quantity = this.quantity - quantity;
	}

	// Used when an order is cancelled.
	public void increaseStock(int quantity) {
		this.quantity = this.quantity + quantity;
	}

	@Override
	public String toString() {
		return "[ ID: " + productId + " | Name: " + productName + " | Category: " + category + " | Brand: " + brand
				+ " | Price: Rs." + String.format("%.2f", price) + " | Stock: " + quantity + "]";
	}
}
