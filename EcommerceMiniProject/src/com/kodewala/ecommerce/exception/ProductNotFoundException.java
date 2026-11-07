package com.kodewala.ecommerce.exception;

/*
 * Used when a product ID does not exist.
 */
public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String message) {
		super(message);
	}
}
