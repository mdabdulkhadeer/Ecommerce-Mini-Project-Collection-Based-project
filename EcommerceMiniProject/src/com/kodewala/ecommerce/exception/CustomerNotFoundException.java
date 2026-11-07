package com.kodewala.ecommerce.exception;

/*
 * Used when a customer ID does not exist.
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}