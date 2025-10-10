
-- Очищаем таблицы перед добавлением данных (опционально)
DELETE FROM credit_applications;
DELETE FROM clients;
DELETE FROM scoring_rules;

-- Сбрасываем последовательности (auto-increment)
ALTER SEQUENCE clients_id_seq RESTART WITH 1;
ALTER SEQUENCE credit_applications_id_seq RESTART WITH 1;
ALTER SEQUENCE scoring_rules_id_seq RESTART WITH 1;

-- Добавляем тестовых клиентов
INSERT INTO clients (first_name, last_name, passport_data, phone_number, email) VALUES
('Иван', 'Петров', '4501123456', '+79161234567', 'ivan.petrov@mail.ru'),
('Мария', 'Сидорова', '4501654321', '+79167654321', 'maria.sidorova@gmail.com'),
('Алексей', 'Козлов', '4501987654', '+79169874563', 'alex.kozlov@yandex.ru');

-- Добавляем скоринговые правила
INSERT INTO scoring_rules (rule_name, condition_description, points, active) VALUES
('Высокий доход', 'Сумма заявки > 500000', 10, true),
('Средний доход', 'Сумма заявки между 100000 и 500000', 20, true),
('Низкий доход', 'Сумма заявки < 100000', 30, true),
('Новый клиент', 'Первый кредит у банка', 15, true),
('Постоянный клиент', 'Второй и более кредит', 25, true);

-- Добавляем тестовые кредитные заявки
INSERT INTO credit_applications (client_id, amount, status, creation_date, score) VALUES
(1, 150000.00, 'APPROVED', '2024-01-15 10:00:00', 85),
(2, 750000.00, 'SCORING', '2024-01-16 11:30:00', NULL),
(3, 50000.00, 'REJECTED', '2024-01-14 09:15:00', 45);
