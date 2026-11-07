package com.kodewala.ecommerce.service;

import com.kodewala.ecommerce.exception.InvalidQuantityException;
import com.kodewala.ecommerce.exception.InsufficientStockException;
import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartService {

	// =====================================================
	// Store carts of all customers
	// Customer ID -> List of CartItems
	// =====================================================

	private Map<Integer, List<CartItem>> customerCarts = new HashMap<>();

	// =====================================================
	// 1. GET CUSTOMER CART
	// =====================================================

	public List<CartItem> getCart(int customerId) {

		// If customer does not have a cart,
		// create an empty cart for that customer
		if (!customerCarts.containsKey(customerId)) {

			customerCarts.put(customerId, new ArrayList<CartItem>());
		}

		// Return customer's cart
		return customerCarts.get(customerId);
	}

	// =====================================================
	// 2. ADD PRODUCT TO CART
	// =====================================================

	public void addProductToCart(int customerId, Product product, int quantity) {

		// Quantity must be greater than 0
		if (quantity <= 0) {

			throw new InvalidQuantityException("Quantity must be greater than 0");
		}

		// Check whether requested quantity
		// is available in product stock
		if (quantity > product.getQuantity()) {

			throw new InsufficientStockException("Only " + product.getQuantity() + " items available in stock");
		}

		// Get customer's cart
		List<CartItem> cart = getCart(customerId);

		// Check whether product is already in cart
		CartItem existingItem = findCartItem(cart, product.getProductId());

		if (existingItem != null) {

			// If product already exists,
			// increase its quantity
			int newQuantity = existingItem.getQuantity() + quantity;

			// Check total quantity against stock
			if (newQuantity > product.getQuantity()) {

				throw new InsufficientStockException("Requested quantity is more than available stock");
			}

			existingItem.setQuantity(newQuantity);

		} else {

			// Product is not already in cart,
			// so create a new CartItem
			CartItem cartItem = new CartItem(product.getProductId(), product.getProductName(), product.getPrice(),
					quantity);

			// Add CartItem to customer's cart
			cart.add(cartItem);
		}
	}

	// =====================================================
	// 3. REMOVE PRODUCT FROM CART
	// =====================================================

	public void removeProductFromCart(int customerId, int productId) {

		// Get customer's cart
		List<CartItem> cart = getCart(customerId);

		// Find product in cart
		CartItem item = findCartItem(cart, productId);

		if (item == null) {

			throw new IllegalArgumentException("Product not found in cart");
		}

		// Remove product from cart
		cart.remove(item);
	}

	// =====================================================
	// 4. INCREASE QUANTITY
	// =====================================================

	public void increaseQuantity(int customerId, int productId, int amount, Product product) {

		// Amount must be positive
		if (amount <= 0) {

			throw new InvalidQuantityException("Quantity must be greater than 0");
		}

		// Get cart
		List<CartItem> cart = getCart(customerId);

		// Find item
		CartItem item = findCartItem(cart, productId);

		if (item == null) {

			throw new IllegalArgumentException("Product not found in cart");
		}

		// Calculate new quantity
		int newQuantity = item.getQuantity() + amount;

		// Check stock
		if (newQuantity > product.getQuantity()) {

			throw new InsufficientStockException("Cannot increase quantity beyond available stock");
		}

		// Update quantity
		item.setQuantity(newQuantity);
	}

	// =====================================================
	// 5. DECREASE QUANTITY
	// =====================================================

	public void decreaseQuantity(int customerId, int productId, int amount) {

		// Amount must be positive
		if (amount <= 0) {

			throw new InvalidQuantityException("Quantity must be greater than 0");
		}

		// Get cart
		List<CartItem> cart = getCart(customerId);

		// Find item
		CartItem item = findCartItem(cart, productId);

		if (item == null) {

			throw new IllegalArgumentException("Product not found in cart");
		}

		// Calculate new quantity
		int newQuantity = item.getQuantity() - amount;

		// Quantity cannot become zero or negative
		if (newQuantity <= 0) {

			// Remove the item completely
			cart.remove(item);

		} else {

			// Otherwise update quantity
			item.setQuantity(newQuantity);
		}
	}

	// =====================================================
	// 6. CALCULATE CART TOTAL
	// =====================================================

	public double calculateTotal(int customerId) {

		// Get customer's cart
		List<CartItem> cart = getCart(customerId);

		// Use Stream API to calculate total
		return cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
	}

	// =====================================================
	// 7. CLEAR CART
	// =====================================================

	public void clearCart(int customerId) {

		// Get customer's cart
		List<CartItem> cart = getCart(customerId);

		// Remove all items
		cart.clear();
	}

	// =====================================================
	// 8. CREATE ORDER SNAPSHOT
	// =====================================================

	public List<CartItem> createOrderSnapshot(int customerId) {

		// Get customer's cart
		List<CartItem> cart = getCart(customerId);

		// Create a new list
		List<CartItem> copy = new ArrayList<>();

		// Copy every CartItem
		for (CartItem item : cart) {

			copy.add(item.copy());
		}

		// Return copied cart
		return copy;
	}

	// =====================================================
	// HELPER METHOD
	// =====================================================

	private CartItem findCartItem(List<CartItem> cart, int productId) {

		// Search each item in the cart
		for (CartItem item : cart) {

			if (item.getProductId() == productId) {

				return item;
			}
		}

		// Product not found
		return null;
	}
}