-- Remove o banco de dados existente, se houver
DROP DATABASE IF EXISTS load_tool;

-- Criação do banco de dados
CREATE DATABASE load_tool;
USE load_tool;

-- Tabela `users`
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL, -- MD5 gera um hash de 32 caracteres
    phone VARCHAR(15),
    address VARCHAR(255),
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000)
);

-- Tabela `categories`
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela `tools`
CREATE TABLE tools (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category_id INT,
    owner_id INT,
    daily_price DECIMAL(10, 2) NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (owner_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Tabela `rentals`
CREATE TABLE rentals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tool_id INT,
    renter_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'active', 'completed', 'canceled') DEFAULT 'pending',
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (tool_id) REFERENCES tools(id),
    FOREIGN KEY (renter_id) REFERENCES users(id)
);

-- Tabela `reviews`
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT,
    reviewer_id INT,
    reviewed_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (rental_id) REFERENCES rentals(id),
    FOREIGN KEY (reviewer_id) REFERENCES users(id),
    FOREIGN KEY (reviewed_id) REFERENCES users(id)
);

-- Tabela `payments`
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status ENUM('pending', 'paid', 'failed') DEFAULT 'pending',
    payment_date BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);