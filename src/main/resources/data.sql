INSERT INTO users (client_number, first_name, last_name, email, password_hash, role, phone)
VALUES ('PAL-SZEF01', 'Jan', 'Kowalski', 'szef@paliwex.pl', '$2a$10$wT8v6Y9.O4XmK/uA8vH/u.3VfO1u2K3D4E5F6G7H8I9J0K1L2M3N.', 'Właściciel', '123-456-789')
ON CONFLICT (email) DO NOTHING;