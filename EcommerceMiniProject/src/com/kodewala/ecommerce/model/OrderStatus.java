package com.kodewala.ecommerce.model;

/*
 * Only these order statuses are allowed by the project.
 */
public enum OrderStatus {
    PLACED,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
