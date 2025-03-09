-- Remove o banco de dados existente, se houver
DROP DATABASE IF EXISTS load_tool;

-- Criação do banco de dados
CREATE DATABASE load_tool;
USE load_tool;

-- Tabela `users`
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL, -- MD5 gera um hash de 32 caracteres
    phone VARCHAR(15),
    address VARCHAR(255),
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    deleted_at BIGINT DEFAULT NULL -- Nova coluna para soft delete
);

-- Tabela `categories`
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela `tools`
CREATE TABLE tools (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category_id BIGINT,
    owner_id BIGINT,
    daily_price DECIMAL(10, 2) NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    deleted_at BIGINT DEFAULT NULL, -- Nova coluna para soft delete
    FOREIGN KEY (owner_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Tabela `rentals`
CREATE TABLE rentals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_id BIGINT,
    renter_id BIGINT,
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
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rental_id BIGINT,
    reviewer_id BIGINT,
    reviewed_id BIGINT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (rental_id) REFERENCES rentals(id),
    FOREIGN KEY (reviewer_id) REFERENCES users(id),
    FOREIGN KEY (reviewed_id) REFERENCES users(id)
);

-- Tabela `payments`
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rental_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    status ENUM('pending', 'paid', 'failed') DEFAULT 'pending',
    payment_date BIGINT DEFAULT (UNIX_TIMESTAMP() * 1000),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);