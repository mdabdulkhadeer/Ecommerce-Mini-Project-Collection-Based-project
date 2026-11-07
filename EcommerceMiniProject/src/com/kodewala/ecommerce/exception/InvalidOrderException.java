package com.kodewala.ecommerce.exception;

/*
 * Used when an order operation is not valid.
 */
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
