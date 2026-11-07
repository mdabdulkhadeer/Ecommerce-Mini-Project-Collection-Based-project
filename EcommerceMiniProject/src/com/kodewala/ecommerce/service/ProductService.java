package com.kodewala.ecommerce.service;

import com.kodewala.ecommerce.exception.ProductNotFoundException;
import com.kodewala.ecommerce.model.Product;
import com.kodewala.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

	private ProductRepository productRepository;

	// Constructor
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// Add a new product
	public void addProduct(Product product) {

		if (product.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}

		if (product.getQuantity() < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}

		productRepository.add(product);
	}

	// Get product using product ID
	public Product getProductById(int productId) {

		Product product = productRepository.findById(productId);

		if (product == null) {
			throw new ProductNotFoundException("Product not found: " + productId);
		}

		return product;
	}

	// Get all products
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	// Search product by name
	public List<Product> searchByName(String name) {

		return productRepository.findAll().stream()
				.filter(product -> product.getProductName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
	}

	// Search product by category
	public List<Product> searchByCategory(String category) {

		return productRepository.findAll().stream().filter(product -> product.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());
	}

	// Search product by brand
	public List<Product> searchByBrand(String brand) {

		return productRepository.findAll().stream().filter(product -> product.getBrand().equalsIgnoreCase(brand))
				.collect(Collectors.toList());
	}

	// Search product by price range
	public List<Product> searchByPriceRange(double minPrice, double maxPrice) {

		return productRepository.findAll().stream()
				.filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
				.sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())).collect(Collectors.toList());
	}

	// Update product price
	public void updatePrice(int productId, double newPrice) {

		Product product = getProductById(productId);

		if (newPrice < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}

		product.setPrice(newPrice);
	}

	// Update product quantity
	public void updateQuantity(int productId, int newQuantity) {

		Product product = getProductById(productId);

		if (newQuantity < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative");
		}

		product.setQuantity(newQuantity);
	}

	// Delete product
	public void deleteProduct(int productId) {

		// First check whether product exists
		getProductById(productId);

		// Then delete it
		productRepository.delete(productId);
	}
}