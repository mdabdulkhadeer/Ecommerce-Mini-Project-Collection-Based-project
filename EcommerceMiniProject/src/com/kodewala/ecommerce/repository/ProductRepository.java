package com.kodewala.ecommerce.repository;

import com.kodewala.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Repository is responsible only for storing and retrieving products.
 *
 * HashSet is used to make Product IDs unique.
 */
public class ProductRepository {

	private final List<Product> products = new ArrayList<>();
	private final Set<Integer> productIds = new HashSet<>();

	public void add(Product product) {

		// HashSet.add() returns false if the ID already exists.
		if (!productIds.add(product.getProductId())) {
			throw new IllegalArgumentException("Product ID already exists: " + product.getProductId());
		}

		products.add(product);
	}

	public List<Product> findAll() {
		return new ArrayList<>(products);
	}

	public Product findById(int productId) {

		// Simple loop: easier to understand.
		for (Product product : products) {
			if (product.getProductId() == productId) {
				return product;
			}
		}

		return null;
	}

	public boolean delete(int productId) {

		for (Product product : products) {
			if (product.getProductId() == productId) {
				products.remove(product);
				productIds.remove(productId);
				return true;
			}
		}

		return false;
	}
}
