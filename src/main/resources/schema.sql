-- 1. Tabela bazowa użytkowników [cite: 512-515]
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       phone VARCHAR(20),
                       is_active BOOLEAN DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabela klientów (dziedziczy logicznie z users) [cite: 516-535]
CREATE TABLE clients (
                         id SERIAL PRIMARY KEY,
                         user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         pesel VARCHAR(11),
                         nip VARCHAR(10),
                         regon VARCHAR(14),
                         address TEXT NOT NULL,
                         loyalty_points INTEGER DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabela pracowników (dziedziczy logicznie z users) [cite: 536-549]
CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           position VARCHAR(100) NOT NULL,
                           hire_date DATE,
                           is_active BOOLEAN DEFAULT TRUE
);

-- 4. Tabela zbiorników paliwa [cite: 550-552]
CREATE TABLE tanks (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       fuel_type VARCHAR(10) NOT NULL,
                       capacity_liters DECIMAL(10,2) NOT NULL,
                       current_level_liters DECIMAL(10,2),
                       pressure_kpa DECIMAL(8,2),
                       temperature_c DECIMAL(5,2),
                       safety_valve_active BOOLEAN DEFAULT FALSE,
                       status VARCHAR(20),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Tabela czujników w zbiornikach [cite: 553-557]
CREATE TABLE sensors (
                         id SERIAL PRIMARY KEY,
                         tank_id INTEGER NOT NULL REFERENCES tanks(id) ON DELETE CASCADE,
                         sensor_type VARCHAR(50) NOT NULL,
                         location VARCHAR(100),
                         status VARCHAR(20),
                         calibration_date DATE
);

-- 6. Tabela pomiarów [cite: 558-560]
CREATE TABLE measurements (
                              id BIGSERIAL PRIMARY KEY,
                              tank_id INTEGER NOT NULL REFERENCES tanks(id),
                              measured_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              level_liters DECIMAL(10,2),
                              pressure_kpa DECIMAL(8,2),
                              temperature_c DECIMAL(5,2),
                              recorded_by_sensor_id INTEGER REFERENCES sensors(id)
);

-- 7. Tabela cennika [cite: 561-564]
CREATE TABLE prices (
                        id SERIAL PRIMARY KEY,
                        item_type VARCHAR(50) NOT NULL,
                        price_pln DECIMAL(8,2) NOT NULL,
                        valid_from DATE NOT NULL,
                        valid_to DATE,
                        updated_by_user_id INTEGER REFERENCES users(id)
);

-- 8. Tabela rezerwacji myjni [cite: 565-567]
CREATE TABLE reservations (
                              id SERIAL PRIMARY KEY,
                              client_id INTEGER NOT NULL REFERENCES clients(id),
                              stand_number INTEGER NOT NULL,
                              wash_type VARCHAR(20) NOT NULL,
                              reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP NOT NULL,
                              status VARCHAR(20),
                              notes TEXT
);

-- 9. Tabela transakcji (zakupy paliwa i myjni) [cite: 568-570]
CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              client_id INTEGER REFERENCES clients(id),
                              employee_id INTEGER REFERENCES employees(id),
                              transaction_type VARCHAR(20) NOT NULL,
                              fuel_wash_type VARCHAR(20),
                              quantity_liters DECIMAL(10,3) NOT NULL,
                              unit_price_pln DECIMAL(8,2),
                              total_amount_pln DECIMAL(10,2) NOT NULL,
                              points_earned INTEGER DEFAULT 0,
                              points_redeemed INTEGER DEFAULT 0,
                              transaction_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              receipt_number VARCHAR(50),
                              status VARCHAR(20)
);

-- 10. Tabela faktur [cite: 571-573]
CREATE TABLE invoices (
                          id SERIAL PRIMARY KEY,
                          transaction_id INTEGER NOT NULL REFERENCES transactions(id),
                          client_nip VARCHAR(10) NOT NULL,
                          client_regon VARCHAR(14),
                          client_name VARCHAR(200) NOT NULL,
                          client_address TEXT NOT NULL,
                          invoice_date DATE DEFAULT CURRENT_DATE,
                          due_date DATE,
                          subtotal_pln DECIMAL(10,2) NOT NULL,
                          vat_rate DECIMAL(5,2),
                          vat_amount_pln DECIMAL(10,2),
                          total_amount_pln DECIMAL(10,2) NOT NULL,
                          status VARCHAR(20),
                          generated_by_employee_id INTEGER NOT NULL REFERENCES employees(id),
                          pdf_path VARCHAR(255)
);

-- 11. Tabela dostaw paliwa [cite: 574-576]
CREATE TABLE deliveries (
                            id SERIAL PRIMARY KEY,
                            fuel_type VARCHAR(10) NOT NULL,
                            quantity_liters DECIMAL(10,2) NOT NULL,
                            supplier_name VARCHAR(100) NOT NULL,
                            delivery_date DATE NOT NULL,
                            actual_delivered_liters DECIMAL(10,2),
                            status VARCHAR(20),
                            ordered_by_user_id INTEGER NOT NULL REFERENCES users(id),
                            notes TEXT
);

-- 12. Tabela powiadomień [cite: 577-580]
CREATE TABLE notifications (
                               id SERIAL PRIMARY KEY,
                               user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               notification_type VARCHAR(50) NOT NULL,
                               title VARCHAR(200),
                               message TEXT NOT NULL,
                               severity VARCHAR(20),
                               sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               read_at TIMESTAMP,
                               is_read BOOLEAN DEFAULT FALSE
);

-- 13. Tabela promocji (program lojalnościowy) [cite: 581-584]
CREATE TABLE promotions (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            points_required INTEGER NOT NULL,
                            discount_pln DECIMAL(8,2),
                            reward_type VARCHAR(20) NOT NULL,
                            valid_from DATE NOT NULL,
                            valid_to DATE,
                            is_active BOOLEAN DEFAULT TRUE,
                            created_by_user_id INTEGER REFERENCES users(id)
);