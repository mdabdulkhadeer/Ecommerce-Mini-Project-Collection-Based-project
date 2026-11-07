9package com.kodewala.ecommerce.main;

import java.util.List;
import java.util.Scanner;

import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Customer;
import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.Product;

import com.kodewala.ecommerce.repository.CustomerRepository;
import com.kodewala.ecommerce.repository.OrderRepository;
import com.kodewala.ecommerce.repository.ProductRepository;

import com.kodewala.ecommerce.service.CartService;
import com.kodewala.ecommerce.service.CustomerService;
import com.kodewala.ecommerce.service.OrderService;
import com.kodewala.ecommerce.service.ProductService;

public class EcommerceApplication {

	// Scanner is used to take input from user
	static Scanner scanner = new Scanner(System.in);

	// Service objects
	static ProductService productService;
	static CustomerService customerService;
	static CartService cartService;
	static OrderService orderService;

	// Stores logged-in customer's ID
	static int loggedInCustomerId = 0;

	public static void main(String[] args) {

		// Create Repository objects
		ProductRepository productRepository = new ProductRepository();

		CustomerRepository customerRepository = new CustomerRepository();

		OrderRepository orderRepository = new OrderRepository();

		// Create Service objects
		productService = new ProductService(productRepository);

		customerService = new CustomerService(customerRepository);

		cartService = new CartService();

		orderService = new OrderService(orderRepository, customerService, productService, cartService);

		// Start the application
		mainMenu();
	}

	// =====================================================
	// MAIN MENU
	// =====================================================

	static void mainMenu() {

		while (true) {

			System.out.println("\n===== E-COMMERCE SYSTEM =====");
			System.out.println("1. Admin");
			System.out.println("2. Customer");
			System.out.println("3. Exit");

			int choice = readInt("Enter choice: ");

			try {

				if (choice == 1) {

					adminMenu();

				} else if (choice == 2) {

					customerMenu();

				} else if (choice == 3) {

					System.out.println("Thank you!");
					break;

				} else {

					System.out.println("Invalid choice.");
				}

			} catch (Exception e) {

				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// =====================================================
	// ADMIN MENU
	// =====================================================

	static void adminMenu() {

		while (true) {

			System.out.println("\n===== ADMIN MENU =====");

			System.out.println("1. Add Product");
			System.out.println("2. View Products");
			System.out.println("3. Search Product");
			System.out.println("4. Update Product");
			System.out.println("5. Delete Product");
			System.out.println("6. View Customers");
			System.out.println("7. View All Orders");
			System.out.println("8. Exit");

			int choice = readInt("Enter choice: ");

			try {

				switch (choice) {

				case 1:
					addProduct();
					break;

				case 2:
					viewProducts();
					break;

				case 3:
					searchProduct();
					break;

				case 4:
					updateProduct();
					break;

				case 5:
					deleteProduct();
					break;

				case 6:
					viewCustomers();
					break;

				case 7:
					viewAllOrders();
					break;

				case 8:
					return;

				default:
					System.out.println("Invalid choice.");
				}

			} catch (Exception e) {

				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// =====================================================
	// 1. ADD PRODUCT
	// =====================================================

	static void addProduct() {

		int id = readInt("Enter product ID: ");

		String name = readLine("Enter product name: ");

		String category = readLine("Enter category: ");

		double price = readDouble("Enter price: ");

		int quantity = readInt("Enter quantity: ");

		String brand = readLine("Enter brand: ");

		// Create Product object
		Product product = new Product(id, name, category, price, quantity, brand);

		// Send product to ProductService
		productService.addProduct(product);

		System.out.println("Product added successfully.");
	}

	// =====================================================
	// 2. VIEW PRODUCTS
	// =====================================================

	static void viewProducts() {

		List<Product> products = productService.getAllProducts();

		if (products.isEmpty()) {

			System.out.println("No products found.");

			return;
		}

		for (Product product : products) {

			System.out.println(product);
		}
	}

	// =====================================================
	// 3. SEARCH PRODUCT
	// =====================================================

	static void searchProduct() {

		System.out.println("\n--- Search Product ---");

		System.out.println("1. Search by ID");
		System.out.println("2. Search by Name");
		System.out.println("3. Search by Category");
		System.out.println("4. Search by Brand");
		System.out.println("5. Search by Price Range");

		int choice = readInt("Enter choice: ");

		if (choice == 1) {

			int id = readInt("Enter product ID: ");

			Product product = productService.getProductById(id);

			System.out.println(product);

		} else if (choice == 2) {

			String name = readLine("Enter product name: ");

			List<Product> products = productService.searchByName(name);

			printProducts(products);

		} else if (choice == 3) {

			String category = readLine("Enter category: ");

			List<Product> products = productService.searchByCategory(category);

			printProducts(products);

		} else if (choice == 4) {

			String brand = readLine("Enter brand: ");

			List<Product> products = productService.searchByBrand(brand);

			printProducts(products);

		} else if (choice == 5) {

			double min = readDouble("Enter minimum price: ");

			double max = readDouble("Enter maximum price: ");

			List<Product> products = productService.searchByPriceRange(min, max);

			printProducts(products);

		} else {

			System.out.println("Invalid choice.");
		}
	}

	// =====================================================
	// 4. UPDATE PRODUCT
	// =====================================================

	static void updateProduct() {

		int id = readInt("Enter product ID: ");

		System.out.println("1. Update Price");
		System.out.println("2. Update Quantity");

		int choice = readInt("Enter choice: ");

		if (choice == 1) {

			double price = readDouble("Enter new price: ");

			productService.updatePrice(id, price);

			System.out.println("Price updated successfully.");

		} else if (choice == 2) {

			int quantity = readInt("Enter new quantity: ");

			productService.updateQuantity(id, quantity);

			System.out.println("Quantity updated successfully.");

		} else {

			System.out.println("Invalid choice.");
		}
	}

	// =====================================================
	// 5. DELETE PRODUCT
	// =====================================================

	static void deleteProduct() {

		int id = readInt("Enter product ID: ");

		productService.deleteProduct(id);

		System.out.println("Product deleted successfully.");
	}

	// =====================================================
	// 6. VIEW CUSTOMERS
	// =====================================================

	static void viewCustomers() {

		List<Customer> customers = customerService.getAllCustomers();

		if (customers.isEmpty()) {

			System.out.println("No customers found.");

			return;
		}

		for (Customer customer : customers) {

			System.out.println(customer);
		}
	}

	// =====================================================
	// 7. VIEW ALL ORDERS
	// =====================================================

	static void viewAllOrders() {

		List<Order> orders = orderService.getAllOrders();

		if (orders.isEmpty()) {

			System.out.println("No orders found.");

			return;
		}

		for (Order order : orders) {

			printOrder(order);
		}
	}

	// =====================================================
	// CUSTOMER MENU
	// =====================================================

	static void customerMenu() {

		while (true) {

			System.out.println("\n===== CUSTOMER MENU =====");

			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. View Products");
			System.out.println("4. Search Product");
			System.out.println("5. Add Product to Cart");
			System.out.println("6. View Cart");
			System.out.println("7. Remove Product from Cart");
			System.out.println("8. Place Order");
			System.out.println("9. View My Orders");
			System.out.println("10. Cancel Order");
			System.out.println("11. Logout");

			int choice = readInt("Enter choice: ");

			try {

				switch (choice) {

				case 1:
					registerCustomer();
					break;

				case 2:
					login();
					break;

				case 3:
					viewProducts();
					break;

				case 4:
					searchProduct();
					break;

				case 5:
					addToCart();
					break;

				case 6:
					viewCart();
					break;

				case 7:
					removeFromCart();
					break;

				case 8:
					placeOrder();
					break;

				case 9:
					viewMyOrders();
					break;

				case 10:
					cancelOrder();
					break;

				case 11:

					loggedInCustomerId = 0;

					System.out.println("Logged out successfully.");

					return;

				default:

					System.out.println("Invalid choice.");
				}

			} catch (Exception e) {

				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// =====================================================
	// 1. REGISTER CUSTOMER
	// =====================================================

	static void registerCustomer() {

		int id = readInt("Enter customer ID: ");

		String name = readLine("Enter name: ");

		String email = readLine("Enter email: ");

		String mobile = readLine("Enter mobile: ");

		String address = readLine("Enter address: ");

		// Create Customer object
		Customer customer = new Customer(id, name, email, mobile, address);

		// Send customer to CustomerService
		customerService.registerCustomer(customer);

		System.out.println("Customer registered successfully.");
	}

	// =====================================================
	// 2. LOGIN
	// =====================================================

	static void login() {

		int id = readInt("Enter customer ID: ");

		// Find customer using Service
		Customer customer = customerService.getCustomerById(id);

		// Store logged-in customer ID
		loggedInCustomerId = customer.getCustomerId();

		System.out.println("Login successful. Welcome " + customer.getName());
	}

	// =====================================================
	// 5. ADD PRODUCT TO CART
	// =====================================================

	static void addToCart() {

		checkLogin();

		int productId = readInt("Enter product ID: ");

		int quantity = readInt("Enter quantity: ");

		// Find product
		Product product = productService.getProductById(productId);

		// Add product to customer's cart
		cartService.addProductToCart(loggedInCustomerId, product, quantity);

		System.out.println("Product added to cart.");
	}

	// =====================================================
	// 6. VIEW CART
	// =====================================================

	static void viewCart() {

		checkLogin();

		List<CartItem> cart = cartService.getCart(loggedInCustomerId);

		if (cart.isEmpty()) {

			System.out.println("Cart is empty.");

			return;
		}

		System.out.println("\n===== YOUR CART =====");

		for (CartItem item : cart) {

			System.out.println(item);
		}

		double total = cartService.calculateTotal(loggedInCustomerId);

		System.out.println("Total Amount: ₹" + total);
	}

	// =====================================================
	// 7. REMOVE PRODUCT FROM CART
	// =====================================================

	static void removeFromCart() {

		checkLogin();

		int productId = readInt("Enter product ID: ");

		cartService.removeProductFromCart(loggedInCustomerId, productId);

		System.out.println("Product removed from cart.");
	}

	// =====================================================
	// 8. PLACE ORDER
	// =====================================================

	static void placeOrder() {

		checkLogin();

		// OrderService handles:
		// stock checking
		// total calculation
		// order creation
		// inventory update
		// cart clearing

		Order order = orderService.placeOrder(loggedInCustomerId);

		System.out.println("Order placed successfully.");

		System.out.println("Order ID: " + order.getOrderId());

		System.out.println("Total Amount: ₹" + order.getTotalAmount());
	}

	// =====================================================
	// 9. VIEW MY ORDERS
	// =====================================================

	static void viewMyOrders() {

		checkLogin();

		List<Order> orders = orderService.getCustomerOrders(loggedInCustomerId);

		if (orders.isEmpty()) {

			System.out.println("No orders found.");

			return;
		}

		for (Order order : orders) {

			printOrder(order);
		}
	}

	// =====================================================
	// 10. CANCEL ORDER
	// =====================================================

	static void cancelOrder() {

		checkLogin();

		int orderId = readInt("Enter order ID: ");

		orderService.cancelOrder(loggedInCustomerId, orderId);

		System.out.println("Order cancelled successfully.");
	}

	// =====================================================
	// CHECK LOGIN
	// =====================================================

	static void checkLogin() {

		if (loggedInCustomerId == 0) {

			throw new RuntimeException("Please login first.");
		}
	}

	// =====================================================
	// PRINT PRODUCTS
	// =====================================================

	static void printProducts(List<Product> products) {

		if (products.isEmpty()) {

			System.out.println("No products found.");

			return;
		}

		for (Product product : products) {

			System.out.println(product);
		}
	}

	// =====================================================
	// PRINT ORDER
	// =====================================================

	static void printOrder(Order order) {

		System.out.println("\n-------------------------");

		System.out.println("Order ID: " + order.getOrderId());

		System.out.println("Customer ID: " + order.getCustomerId());

		System.out.println("Amount: ₹" + order.getTotalAmount());

		System.out.println("Status: " + order.getOrderStatus());

		System.out.println("Date: " + order.getOrderDate());

		System.out.println("Items:");

		for (CartItem item : order.getItems()) {

			System.out.println(item);
		}

		System.out.println("-------------------------");
	}

	// =====================================================
	// INPUT METHODS
	// =====================================================

	static int readInt(String message) {

		System.out.print(message);

		return Integer.parseInt(scanner.nextLine());
	}

	static double readDouble(String message) {

		System.out.print(message);

		return Double.parseDouble(scanner.nextLine());
	}

	static String readLine(String message) {

		System.out.print(message);

		return scanner.nextLine();
	}
}