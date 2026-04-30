-- 1. KLIENCI
CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    points_balance INTEGER DEFAULT 0
    );

-- 2. ZBIORNIKI PALIWA
CREATE TABLE IF NOT EXISTS fuel_tanks (
    id SERIAL PRIMARY KEY,
    fuel_type VARCHAR(20) NOT NULL,
    current_liters DECIMAL(12,2) NOT NULL,
    max_capacity DECIMAL(12,2) NOT NULL
    );

-- 3. REZERWACJE MYJNI
CREATE TABLE IF NOT EXISTS reservations (
    id SERIAL PRIMARY KEY,
    stand_number INTEGER NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    client_id INTEGER REFERENCES clients(id),
    wash_type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'PENDING'
    );

-- 4. TRANSAKCJE (Zaktualizowane o Twoje potrzeby)
CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients(id),
    transaction_type VARCHAR(20) NOT NULL, -- 'FUEL' lub 'WASH'
    fuel_wash_type VARCHAR(50),            -- np. 'Premium' lub 'PB95'
    quantity_liters DECIMAL(10,2),         -- dla paliwa
    total_amount_pln DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20),            -- 'CARD', 'CASH', 'POINTS'
    transaction_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
    );

-- 5. CENNIK (Prosty i czytelny)
CREATE TABLE IF NOT EXISTS prices (
    id SERIAL PRIMARY KEY,
    item_name VARCHAR(50) NOT NULL,        -- 'PB95', 'ON', 'WASH_BASIC', 'WASH_PREMIUM'
    price_per_unit DECIMAL(8,2) NOT NULL,
    unit_type VARCHAR(10)                  -- 'L' (litr) lub 'PCS' (sztuka/mycie)
    );