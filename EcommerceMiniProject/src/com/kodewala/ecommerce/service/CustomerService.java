package com.kodewala.ecommerce.service;

import com.kodewala.ecommerce.model.Customer;
import com.kodewala.ecommerce.repository.CustomerRepository;
import com.kodewala.ecommerce.exception.CustomerNotFoundException;

import java.util.List;

public class CustomerService {

	// Repository object
	// We use this object to store and retrieve customers
	private CustomerRepository customerRepository;

	// Constructor
	// We receive CustomerRepository from outside
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	// =====================================================
	// 1. REGISTER CUSTOMER
	// =====================================================

	public void registerCustomer(Customer customer) {

		// Basic validation
		if (customer.getName() == null || customer.getName().trim().isEmpty()) {
			throw new IllegalArgumentException("Customer name cannot be empty");
		}

		if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
			throw new IllegalArgumentException("Customer email cannot be empty");
		}

		if (customer.getMobile() == null || customer.getMobile().trim().isEmpty()) {
			throw new IllegalArgumentException("Customer mobile cannot be empty");
		}

		if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
			throw new IllegalArgumentException("Customer address cannot be empty");
		}

		// If all validations are successful,
		// send the customer to repository
		customerRepository.add(customer);
	}

	// =====================================================
	// 2. GET CUSTOMER BY ID
	// =====================================================

	public Customer getCustomerById(int customerId) {

		// Ask repository to find customer
		Customer customer = customerRepository.findById(customerId);

		// If customer is not found
		// throw custom exception
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
		}

		// Return customer
		return customer;
	}

	// =====================================================
	// 3. GET ALL CUSTOMERS
	// =====================================================

	public List<Customer> getAllCustomers() {

		// Get all customers from repository
		return customerRepository.findAll();
	}

	// =====================================================
	// 4. UPDATE CUSTOMER ADDRESS
	// =====================================================

	public void updateAddress(int customerId, String newAddress) {

		// First find the customer
		// This also checks whether customer exists
		Customer customer = getCustomerById(customerId);

		// Validate new address
		if (newAddress == null || newAddress.trim().isEmpty()) {
			throw new IllegalArgumentException("Address cannot be empty");
		}

		// Update address
		customer.setAddress(newAddress);
	}
}