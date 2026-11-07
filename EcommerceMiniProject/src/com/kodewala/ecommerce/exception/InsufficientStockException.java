package com.kodewala.ecommerce.exception;

/*
 * Used when requested quantity is greater than available stock.
 */
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
