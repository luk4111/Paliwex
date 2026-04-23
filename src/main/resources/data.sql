INSERT INTO users (client_number, first_name, last_name, email, password_hash, role, phone)
VALUES ('PAL-SZEF01', 'Jan', 'Kowalski', 'szef@paliwex.pl', '$2a$10$wT8v6Y9.O4XmK/uA8vH/u.3VfO1u2K3D4E5F6G7H8I9J0K1L2M3N.', 'Właściciel', '123-456-789')
ON CONFLICT (email) DO NOTHING;

INSERT INTO tanks (name, fuel_type, capacity_liters, current_level_liters, pressure_kpa, temperature_c, status)
VALUES ('Główny PB95', 'PB95', 20000.00, 15000.00, 101.3, 15.2, 'OK'),
       ('Główny ON', 'ON', 30000.00, 2500.00, 102.1, 14.8, 'LOW_LEVEL');

INSERT INTO clients (user_id, address, loyalty_points)
VALUES (1, 'ul. Sezamkowa 1, Kraków', 0)
ON CONFLICT DO NOTHING;

INSERT INTO reservations (client_id, stand_number, wash_type, start_time, end_time, status)
VALUES (1, 1, 'Premium', '2026-04-23 10:00:00', '2026-04-23 10:45:00', 'CONFIRMED');



