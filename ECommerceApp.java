import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(String id, String name, String description, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int quantity) { this.stockQuantity = quantity; }

    @Override
    public String toString() {
        return String.format("%s - %s ($%.2f, %d in stock)", id, name, price, stockQuantity);
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s x%d = $%.2f", product.getName(), quantity, getTotalPrice());
    }
}

class ShoppingCart {
    private List<CartItem> items;
    private double totalPrice;
    private double taxRate;

    public ShoppingCart(double taxRate) {
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.taxRate = taxRate;
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                updateTotalPrice();
                return;
            }
        }
        
        if (product.getStockQuantity() >= quantity) {
            items.add(new CartItem(product, quantity));
            updateTotalPrice();
        } else {
            System.out.println("Not enough stock available for " + product.getName());
        }
    }

    public void removeItem(String productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
        updateTotalPrice();
    }

    public void updateQuantity(String productId, int newQuantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                if (newQuantity <= 0) {
                    removeItem(productId);
                } else if (item.getProduct().getStockQuantity() >= newQuantity) {
                    item.setQuantity(newQuantity);
                } else {
                    System.out.println("Not enough stock available for " + item.getProduct().getName());
                }
                break;
            }
        }
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public double getSubtotal() {
        return totalPrice;
    }

    public double getTax() {
        return totalPrice * taxRate;
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your shopping cart is empty.");
            return;
        }

        System.out.println("Shopping Cart Contents:");
        System.out.println("-----------------------");
        items.forEach(item -> System.out.println(item));
        System.out.println("-----------------------");
        System.out.printf("Subtotal: $%.2f%n", getSubtotal());
        System.out.printf("Tax (%.1f%%): $%.2f%n", taxRate * 100, getTax());
        System.out.printf("Total: $%.2f%n", getTotal());
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clearCart() {
        items.clear();
        totalPrice = 0.0;
    }
}

class Order {
    private String orderId;
    private List<CartItem> items;
    private double subtotal;
    private double tax;
    private double total;
    private LocalDateTime orderDate;
    private String customerId;
    private String status;

    public Order(String orderId, ShoppingCart cart, String customerId) {
        this.orderId = orderId;
        this.items = cart.getItems();
        this.subtotal = cart.getSubtotal();
        this.tax = cart.getTax();
        this.total = cart.getTotal();
        this.orderDate = LocalDateTime.now();
        this.customerId = customerId;
        this.status = "Processing";
    }

    public String getOrderId() { return orderId; }
    public List<CartItem> getItems() { return items; }
    public double getSubtotal() { return subtotal; }
    public double getTax() { return tax; }
    public double getTotal() { return total; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void displayOrder() {
        System.out.println("Order Details:");
        System.out.println("-----------------------");
        System.out.println("Order ID: " + orderId);
        System.out.println("Date: " + orderDate);
        System.out.println("Status: " + status);
        System.out.println("Items:");
        items.forEach(item -> System.out.println("  " + item));
        System.out.println("-----------------------");
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Tax: $%.2f%n", tax);
        System.out.printf("Total: $%.2f%n", total);
    }
}

class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        this.products = new HashMap<>();
        initializeSampleProducts();
    }

    private void initializeSampleProducts() {
        addProduct(new Product("P001", "Laptop", "High-performance laptop", 999.99, 10));
        addProduct(new Product("P002", "Smartphone", "Latest smartphone model", 699.99, 15));
        addProduct(new Product("P003", "Headphones", "Noise-cancelling headphones", 199.99, 20));
        addProduct(new Product("P004", "Smart Watch", "Fitness tracking smartwatch", 249.99, 8));
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public void displayAvailableProducts() {
        System.out.println("Available Products:");
        System.out.println("-----------------------");
        products.values().forEach(System.out::println);
        System.out.println("-----------------------");
    }

    public boolean checkStock(String productId, int quantity) {
        Product product = products.get(productId);
        return product != null && product.getStockQuantity() >= quantity;
    }

    public void updateStock(String productId, int quantitySold) {
        Product product = products.get(productId);
        if (product != null) {
            int newQuantity = product.getStockQuantity() - quantitySold;
            if (newQuantity >= 0) {
                product.setStockQuantity(newQuantity);
            }
        }
    }
}

class PaymentDetails {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public PaymentDetails(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCardHolderName() { return cardHolderName; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
}

interface PaymentProcessor {
    boolean processPayment(PaymentDetails paymentDetails, double amount);
}

class SimplePaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(PaymentDetails paymentDetails, double amount) {
        // Validate card number length
        if (paymentDetails.getCardNumber().length() < 13) {
            System.out.println("Invalid card number: must be at least 13 digits");
            return false;
        }
        
        // Validate CVV is numeric
        if (!paymentDetails.getCvv().matches("\\d{3,4}")) {
            System.out.println("Invalid CVV: must be 3 or 4 digits");
            return false;
        }
        
        // Validate expiry date format
        if (!paymentDetails.getExpiryDate().matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            System.out.println("Invalid expiry date: must be in MM/YY format");
            return false;
        }

        // Get last 4 digits safely
        String cardNumber = paymentDetails.getCardNumber();
        String lastFour = cardNumber.substring(Math.max(0, cardNumber.length() - 4));
        
        System.out.printf("Processing payment of $%.2f using card ending with %s%n", 
                         amount, lastFour);
        return true;
    }
}

class CheckoutService {
    private Inventory inventory;
    private PaymentProcessor paymentProcessor;

    public CheckoutService(Inventory inventory, PaymentProcessor paymentProcessor) {
        this.inventory = inventory;
        this.paymentProcessor = paymentProcessor;
    }

    public Order processOrder(ShoppingCart cart, String customerId, PaymentDetails paymentDetails) {
        for (CartItem item : cart.getItems()) {
            if (!inventory.checkStock(item.getProduct().getId(), item.getQuantity())) {
                throw new IllegalStateException("Not enough stock for product: " + item.getProduct().getName());
            }
        }

        boolean paymentSuccess = paymentProcessor.processPayment(paymentDetails, cart.getTotal());
        if (!paymentSuccess) {
            throw new IllegalStateException("Payment processing failed");
        }

        for (CartItem item : cart.getItems()) {
            inventory.updateStock(item.getProduct().getId(), item.getQuantity());
        }

        String orderId = "ORD" + System.currentTimeMillis();
        Order order = new Order(orderId, cart, customerId);
        cart.clearCart();
        return order;
    }
}

public class ECommerceApp {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        ShoppingCart cart = new ShoppingCart(0.08);
        PaymentProcessor paymentProcessor = new SimplePaymentProcessor();
        CheckoutService checkoutService = new CheckoutService(inventory, paymentProcessor);
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nE-Commerce System Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Update Cart Item Quantity");
            System.out.println("5. Remove Item from Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    inventory.displayAvailableProducts();
                    break;
                    
                case 2:
                    System.out.print("Enter product ID to add: ");
                    String productId = scanner.nextLine();
                    Product product = inventory.getProduct(productId);
                    if (product != null) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        cart.addItem(product, quantity);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                    
                case 3:
                    cart.displayCart();
                    break;
                    
                case 4:
                    System.out.print("Enter product ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    cart.updateQuantity(updateId, newQuantity);
                    break;
                    
                case 5:
                    System.out.print("Enter product ID to remove: ");
                    String removeId = scanner.nextLine();
                    cart.removeItem(removeId);
                    System.out.println("Item removed from cart.");
                    break;
                    
                case 6:
                    if (cart.getItems().isEmpty()) {
                        System.out.println("Your cart is empty. Nothing to checkout.");
                        break;
                    }
                    
                    System.out.println("\nProceeding to checkout...");
                    cart.displayCart();
                    
                    System.out.println("\nEnter payment details:");
                    System.out.print("Card Number: ");
                    String cardNumber = scanner.nextLine();
                    System.out.print("Card Holder Name: ");
                    String cardHolder = scanner.nextLine();
                    System.out.print("Expiry Date (MM/YY): ");
                    String expiry = scanner.nextLine();
                    System.out.print("CVV: ");
                    String cvv = scanner.nextLine();
                    
                    PaymentDetails paymentDetails = new PaymentDetails(
                        cardNumber, cardHolder, expiry, cvv);
                    
                    try {
                        Order order = checkoutService.processOrder(cart, "CUST001", paymentDetails);
                        if (order != null) {
                            System.out.println("\nOrder placed successfully!");
                            order.displayOrder();
                        } else {
                            System.out.println("\nPayment failed. Please try again with valid payment details.");
                        }
                    } catch (IllegalStateException e) {
                        System.out.println("Checkout failed: " + e.getMessage());
                    }
                    break;
                    
                case 7:
                    System.out.println("Thank you for shopping with us!");
                    scanner.close();
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}