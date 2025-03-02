USE load_tool;

-- Inserindo usuários (senhas em MD5)
INSERT INTO users (name, email, password, phone, address, created_at) VALUES
('John Doe', 'john.doe@example.com', MD5('password123'), '11987654321', '123 Main St', UNIX_TIMESTAMP() * 1000),
('Jane Smith', 'jane.smith@example.com', MD5('password456'), '21987654321', '456 Elm St', UNIX_TIMESTAMP() * 1000),
('Carlos Brown', 'carlos.brown@example.com', MD5('password789'), '31987654321', '789 Oak St', UNIX_TIMESTAMP() * 1000);

-- Inserindo categorias
INSERT INTO categories (name) VALUES
('Power Tools'),
('Hand Tools'),
('Garden Tools');

-- Inserindo ferramentas
INSERT INTO tools (name, description, category_id, owner_id, daily_price, created_at) VALUES
('Drill', 'High-power drill', 1, 1, 25.00, UNIX_TIMESTAMP() * 1000),
('Hammer', 'Claw hammer', 2, 2, 10.00, UNIX_TIMESTAMP() * 1000),
('Lawn Mower', 'Electric lawn mower', 3, 3, 50.00, UNIX_TIMESTAMP() * 1000);

-- Inserindo aluguéis
INSERT INTO rentals (tool_id, renter_id, start_date, end_date, total_price, status, created_at) VALUES
(1, 2, '2023-10-01', '2023-10-03', 50.00, 'completed', UNIX_TIMESTAMP() * 1000),
(2, 3, '2023-10-05', '2023-10-07', 20.00, 'active', UNIX_TIMESTAMP() * 1000),
(3, 1, '2023-10-10', '2023-10-12', 100.00, 'pending', UNIX_TIMESTAMP() * 1000);

-- Inserindo avaliações
INSERT INTO reviews (rental_id, reviewer_id, reviewed_id, rating, comment, created_at) VALUES
(1, 2, 1, 5, 'Great tool, worked perfectly!', UNIX_TIMESTAMP() * 1000),
(2, 3, 2, 4, 'Hammer was in good condition, but could be lighter.', UNIX_TIMESTAMP() * 1000);

-- Inserindo pagamentos
INSERT INTO payments (rental_id, amount, payment_method, status, payment_date) VALUES
(1, 50.00, 'credit_card', 'paid', UNIX_TIMESTAMP() * 1000),
(2, 20.00, 'pix', 'paid', UNIX_TIMESTAMP() * 1000),
(3, 100.00, 'credit_card', 'pending', UNIX_TIMESTAMP() * 1000);

-- Listar todos os usuários
SELECT * FROM users;

-- Listar todas as ferramentas disponíveis
SELECT * FROM tools WHERE available = TRUE;

-- Listar aluguéis ativos
SELECT * FROM rentals WHERE status = 'active';