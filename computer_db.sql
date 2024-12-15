-- Tạo database
-- CREATE DATABASE IF NOT EXISTS computer_db;
-- USE computer_db;

-- Tạo bảng người dùng
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('customer', 'admin') NOT NULL DEFAULT 'customer',
    profile_image VARCHAR(255), -- Cột để lưu ảnh người dùng
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng danh mục sản phẩm
CREATE TABLE IF NOT EXISTS categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_image VARCHAR(255), -- Cột để lưu ảnh của danh mục
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng sản phẩm
CREATE TABLE IF NOT EXISTS products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    category_id INT, -- Thêm khóa ngoại liên kết với bảng categories
    product_image VARCHAR(255), -- Cột để lưu ảnh của sản phẩm
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Tạo bảng đơn hàng
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'pending',
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

-- Tạo bảng chi tiết đơn hàng
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Tạo bảng thanh toán
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status VARCHAR(50) DEFAULT 'pending',
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- Tạo bảng giao hàng
CREATE TABLE IF NOT EXISTS shipping (
    shipping_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    shipping_address VARCHAR(255) NOT NULL,
    shipping_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- ngày giao hàng mặc định là thời điểm hiện tại
    delivery_date TIMESTAMP NULL, -- giá trị mặc định là NULL
    status VARCHAR(50) DEFAULT 'pending',
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- Tạo bảng giỏ hàng
CREATE TABLE IF NOT EXISTS carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

-- Tạo bảng sản phẩm trong giỏ hàng
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT,
    product_id INT,
    quantity INT NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(cart_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Tạo bảng danh sách yêu thích
CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

-- Tạo bảng sản phẩm trong danh sách yêu thích
CREATE TABLE IF NOT EXISTS wishlist_items (
    wishlist_item_id INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id INT,
    product_id INT,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS public_key (
    key_id INT AUTO_INCREMENT PRIMARY KEY, 
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    name VARCHAR(2000) NOT NULL,
    createdTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endTime TIMESTAMP NULL DEFAULT NULL,
    status ENUM('Enabled', 'Disabled', 'Expired') NOT NULL DEFAULT 'Enabled',
    reportTime TIMESTAMP NULL DEFAULT NULL
);


-- Đặt thời gian cho endTime 
DELIMITER $$

CREATE TRIGGER set_end_time
BEFORE INSERT ON `public_key`
FOR EACH ROW
BEGIN
    SET NEW.endTime = DATE_ADD(NEW.createdTime, INTERVAL 7 DAY);
END$$

DELIMITER ;


-- Cập nhật trạng thái của key

SET GLOBAL event_scheduler = ON;
SET GLOBAL event_scheduler = OFF;

SHOW VARIABLES LIKE 'event_scheduler';



CREATE EVENT IF NOT EXISTS update_key_status
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
    UPDATE `public_key`
    SET status = 'Expired'
    WHERE endTime <= NOW() AND status = 'Enabled';
END;

-- SELECT NOW();












-- Select the database
USE computer_db;

-- Insert sample users with adjusted information
INSERT INTO users (name, email, phone, address, password, role, profile_image) VALUES 
('John Doe', 'john@example.com', '1234567890', 'TP. Hồ Chí Minh', 'hashed_password1', 'customer', '/images/avatars/img1.jpg'),
('Huy', 'huy@example.com', '1234567890', 'TP. Hồ Chí Minh', 'hashed_password2', 'customer', '/images/avatars/img2.jpg'),
('Admin User', 'admin@example.com', '0987654321', 'Bình Định', 'hashed_admin_password', 'admin', '/images/avatars/img1.jpg');

-- Insert sample categories with suitable descriptions
INSERT INTO categories (name, description, category_image) VALUES 
('CPU', 'Central Processing Units for high performance', '/images/categories/cpu.jpg'),
('Mainboard', 'Motherboards to support CPU and other components', '/images/categories/mainboard.jpg'),
('RAM', 'Memory for efficient data handling', '/images/categories/ram.jpg'),
('Storage', 'Hard drives and SSDs for data storage', '/images/categories/ocung.jpg'),
('Graphics Card', 'High-performance GPUs for visuals and gaming', '/images/categories/carddohoa.jpg'),
('Cooling System', 'Systems to cool down hardware', '/images/categories/tannhiet.jpg');

-- Insert sample products with prices and stock quantities
INSERT INTO products (name, description, price, stock_quantity, category_id, product_image) VALUES 
('Intel Core i9-12900K', '16-core high-performance CPU for gaming and tasks', 12000000, 50, 1, '/images/products/img1.jpg'),
('AMD Ryzen 9 5900X', '12-core CPU for high-end performance', 11000000, 75, 1, '/images/products/img2.jpg'),
('NVIDIA GeForce RTX 3080', 'High-end graphics card with ray tracing', 20000000, 30, 5, '/images/products/img3.jpg'),
('ASUS ROG Strix B550-F', 'Gaming motherboard with Ryzen support', 4000000, 120, 2, '/images/products/img4.jpg'),
('Corsair Vengeance LPX 16GB', 'DDR4 RAM, 3200MHz, optimized for gaming', 1500000, 200, 3, '/images/products/img5.jpg'),
('Samsung 970 EVO Plus 1TB', 'Fast NVMe SSD for storage', 3500000, 100, 4, '/images/products/img6.jpg');

-- Insert sample orders, setting total_amount consistent with products
INSERT INTO orders (customer_id, status, total_amount) VALUES 
(1, 'pending', 12000000), -- For Intel Core i9-12900K
(2, 'pending', 11000000); -- For AMD Ryzen 9 5900X

-- Insert sample order items, matching products in orders
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES 
(1, 1, 1, 12000000), -- Intel Core i9-12900K
(2, 2, 1, 11000000); -- AMD Ryzen 9 5900X

-- Insert sample payments, setting amounts and methods
INSERT INTO payments (order_id, amount, payment_method, status) VALUES 
(1, 12000000, 'credit card', 'completed'),
(2, 11000000, 'paypal', 'completed');

-- Insert sample shipping records, with delivery dates and status
INSERT INTO shipping (order_id, shipping_address, delivery_date, status) VALUES 
(1, '123 Main St, TP. Hồ Chí Minh', '2023-12-01', 'shipped'),
(2, '456 Admin St, Bình Định', '2023-12-05', 'delivered');

-- Insert sample carts for customers
INSERT INTO carts (customer_id) VALUES 
(1),
(2);

-- Insert sample cart items, linking products to customer carts
INSERT INTO cart_items (cart_id, product_id, quantity) VALUES 
(1, 1, 1), -- Intel Core i9-12900K
(2, 2, 1); -- AMD Ryzen 9 5900X

-- Insert sample wishlists for customers
INSERT INTO wishlist (customer_id) VALUES 
(1),
(2);

-- Insert sample wishlist items, associating products to wishlists
INSERT INTO wishlist_items (wishlist_id, product_id) VALUES 
(1, 3), -- NVIDIA GeForce RTX 3080
(2, 5); -- Corsair Vengeance LPX 16GB RAM
