# E-Commerce-System
<p>A comprehensive implementation of an e-commerce shopping cart system in Java, featuring product selection, cart management, and checkout functionality.</p>
  
  <h2>✨ Key Features</h2>
        <ul>
            <li><strong>Product Management:</strong> Catalog with product details and stock tracking</li>
            <li><strong>Shopping Cart:</strong> Add/remove items, quantity updates, and real-time total calculation</li>
            <li><strong>Checkout System:</strong> Payment processing with validation and order confirmation</li>
            <li><strong>Inventory Control:</strong> Automatic stock updates after purchases</li>
            <li><strong>Error Handling:</strong> Comprehensive validation for payment and stock availability</li>
        </ul>
    </div>

  <h2>🚀 Getting Started</h2>
    
  <h3>Prerequisites</h3>
    <ul>
        <li>Java JDK 8 or higher</li>
  </ul>
    
  <h3>Installation</h3>
    <ol>
        <li>Clone the repository</li>
        <li>Compile all Java files:
            <pre><code>javac *.java</code></pre>
        </li>
        <li>Run the application:
            <pre><code>java ECommerceApp</code></pre>
        </li>
  </ol>
    
  <h2>🛠️ Technical Implementation</h2>
    
  <h3>Class Structure</h3>
  <ul>
        <li><code>Product.java</code> - Product entity with details</li>
        <li><code>CartItem.java</code> - Items in shopping cart</li>
        <li><code>ShoppingCart.java</code> - Cart operations and calculations</li>
        <li><code>Inventory.java</code> - Product catalog and stock management</li>
        <li><code>Order.java</code> - Order processing and confirmation</li>
        <li><code>PaymentProcessor.java</code> - Interface for payment processing</li>
    </ul>
    
  <div class="tech-stack">
        <span class="tech-item">Java</span>
        <span class="tech-item">OOP</span>
        <span class="tech-item">SOLID Principles</span>
        <span class="tech-item">Collections</span>
        <span class="tech-item">Streams API</span>
    </div>
    
  <h2>📸 Sample Output</h2>
  <pre>
E-Commerce System Menu:
1. View Products
2. Add Product to Cart
3. View Cart
4. Update Cart Item Quantity
5. Remove Item from Cart
6. Checkout
7. Exit
Enter your choice: 1

Available Products:
-----------------------
P001 - Laptop ($999.99, 10 in stock)
P002 - Smartphone ($699.99, 15 in stock)
P003 - Headphones ($199.99, 20 in stock)
-----------------------
  </pre>
    
  <h2>📈 Future Enhancements</h2>
    <ul>
        <li>Database integration for persistent storage</li>
        <li>Spring Boot conversion for web deployment</li>
        <li>User authentication system</li>
        <li>Admin dashboard for inventory management</li>
    </ul>
    
  <h2>🤝 Contributing</h2>
    <p>Contributions are welcome! Please fork the repository and submit a pull request.</p>
    
  <h2>📄 License</h2>
    <p>This project is licensed under the MIT License.</p>
</body>
</html>
