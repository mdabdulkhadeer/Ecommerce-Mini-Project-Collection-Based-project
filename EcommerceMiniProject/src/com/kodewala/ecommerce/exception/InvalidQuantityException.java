package com.kodewala.ecommerce.exception;

/*
 * Used when cart quantity is invalid.
 */
public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(String message) {
        super(message);
    }
}
