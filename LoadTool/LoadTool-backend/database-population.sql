USE load_tool;

-- Inserindo usuários (`users`)
INSERT INTO users (name, email, password, phone, address, created_at) VALUES
('João Silva', 'joao.silva@example.com', MD5('senha123'), '(11) 98765-4321', 'Rua das Flores, 123, São Paulo, SP', UNIX_TIMESTAMP() * 1000),
('Maria Oliveira', 'maria.oliveira@example.com', MD5('senha456'), '(21) 98765-1234', 'Avenida Brasil, 456, Rio de Janeiro, RJ', UNIX_TIMESTAMP() * 1000),
('Carlos Souza', 'carlos.souza@example.com', MD5('senha789'), '(31) 98765-5678', 'Rua dos Pinheiros, 789, Belo Horizonte, MG', UNIX_TIMESTAMP() * 1000),
('Ana Costa', 'ana.costa@example.com', MD5('senha101'), '(41) 98765-8765', 'Avenida Paulista, 1010, Curitiba, PR', UNIX_TIMESTAMP() * 1000),
('Pedro Alves', 'pedro.alves@example.com', MD5('senha112'), '(51) 98765-4321', 'Rua da Praia, 112, Porto Alegre, RS', UNIX_TIMESTAMP() * 1000);

-- Inserindo categorias (`categories`)
INSERT INTO categories (name) VALUES
('Ferramentas Manuais'),
('Ferramentas Elétricas'),
('Equipamentos de Jardim'),
('Ferramentas de Medição'),
('Equipamentos de Segurança');

-- Inserindo ferramentas (`tools`)
INSERT INTO tools (name, description, category_id, owner_id, daily_price, created_at) VALUES
('Martelo de Carpinteiro', 'Martelo resistente para trabalhos de carpintaria.', 1, 1, 15.00, UNIX_TIMESTAMP() * 1000),
('Furadeira Elétrica', 'Furadeira potente com várias velocidades.', 2, 2, 30.00, UNIX_TIMESTAMP() * 1000),
('Cortador de Grama', 'Cortador de grama a gasolina para grandes áreas.', 3, 3, 50.00, UNIX_TIMESTAMP() * 1000),
('Trena a Laser', 'Trena a laser com precisão de 0.5mm.', 4, 4, 20.00, UNIX_TIMESTAMP() * 1000),
('Capacete de Segurança', 'Capacete de segurança com ajuste fácil.', 5, 5, 10.00, UNIX_TIMESTAMP() * 1000);

-- Inserindo aluguéis (`rentals`)
INSERT INTO rentals (tool_id, renter_id, start_date, end_date, total_price, status, created_at) VALUES
(1, 2, '2023-10-01', '2023-10-03', 30.00, 'completed', UNIX_TIMESTAMP() * 1000),
(2, 3, '2023-10-05', '2023-10-07', 60.00, 'active', UNIX_TIMESTAMP() * 1000),
(3, 4, '2023-10-10', '2023-10-12', 100.00, 'pending', UNIX_TIMESTAMP() * 1000),
(4, 5, '2023-10-15', '2023-10-17', 40.00, 'completed', UNIX_TIMESTAMP() * 1000),
(5, 1, '2023-10-20', '2023-10-22', 20.00, 'canceled', UNIX_TIMESTAMP() * 1000);

-- Inserindo avaliações (`reviews`)
INSERT INTO reviews (rental_id, reviewer_id, reviewed_id, rating, comment, created_at) VALUES
(1, 2, 1, 5, 'Excelente ferramenta, entrega rápida!', UNIX_TIMESTAMP() * 1000),
(2, 3, 2, 4, 'Boa furadeira, mas um pouco barulhenta.', UNIX_TIMESTAMP() * 1000),
(3, 4, 3, 5, 'Cortador de grama muito eficiente.', UNIX_TIMESTAMP() * 1000),
(4, 5, 4, 3, 'Trena funcionou bem, mas a bateria acabou rápido.', UNIX_TIMESTAMP() * 1000),
(5, 1, 5, 2, 'Capacete confortável, mas o aluguel foi cancelado.', UNIX_TIMESTAMP() * 1000);

-- Inserindo pagamentos (`payments`)
INSERT INTO payments (rental_id, amount, payment_method, status, payment_date) VALUES
(1, 30.00, 'Cartão de Crédito', 'paid', UNIX_TIMESTAMP() * 1000),
(2, 60.00, 'Pix', 'paid', UNIX_TIMESTAMP() * 1000),
(3, 100.00, 'Boleto', 'pending', UNIX_TIMESTAMP() * 1000),
(4, 40.00, 'Cartão de Débito', 'paid', UNIX_TIMESTAMP() * 1000),
(5, 20.00, 'Cartão de Crédito', 'failed', UNIX_TIMESTAMP() * 1000);

-- Listar todos os usuários
SELECT * FROM users;

-- Listar todas as ferramentas disponíveis
SELECT * FROM tools WHERE available = TRUE;

-- Listar aluguéis ativos
SELECT * FROM rentals WHERE status = 'active';