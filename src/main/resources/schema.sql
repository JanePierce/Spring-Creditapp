-- Удаляем таблицы если они существуют (для чистого запуска)
DROP TABLE IF EXISTS credit_applications CASCADE;
DROP TABLE IF EXISTS clients CASCADE;
DROP TABLE IF EXISTS scoring_rules CASCADE;

-- Создаем таблицу clients (клиенты)
CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    passport_data VARCHAR(20) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    email VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создаем таблицу credit_applications (кредитные заявки)
CREATE TABLE IF NOT EXISTS credit_applications (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NEW',
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    decision_date TIMESTAMP NULL,
    score INTEGER NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

-- Создаем таблицу scoring_rules (скоринговые правила)
CREATE TABLE IF NOT EXISTS scoring_rules (
    id BIGSERIAL PRIMARY KEY,
    rule_name VARCHAR(200) NOT NULL,
    condition_description VARCHAR(500) NOT NULL,
    points INTEGER NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

