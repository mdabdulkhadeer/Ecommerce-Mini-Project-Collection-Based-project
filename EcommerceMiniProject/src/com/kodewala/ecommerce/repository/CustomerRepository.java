package com.kodewala.ecommerce.repository;

import com.kodewala.ecommerce.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Customer data is stored in:
 *
 * HashMap<CustomerId, Customer>
 *
 * Example:
 * 1 -> Abdul
 * 2 -> nadeem
 */
public class CustomerRepository {

    private final Map<Integer, Customer> customers = new HashMap<>();

    public void add(Customer customer) {

        if (customers.containsKey(customer.getCustomerId())) {
            throw new IllegalArgumentException(
                    "Customer ID already exists: " + customer.getCustomerId());
        }

        customers.put(customer.getCustomerId(), customer);
    }

    public Customer findById(int customerId) {
        return customers.get(customerId);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }
}
