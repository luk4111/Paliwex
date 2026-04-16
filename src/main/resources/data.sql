INSERT INTO users (first_name, last_name, email, password_hash, role, phone)
VALUES ('Jan', 'Kowalski', 'szef@paliwex.pl', 'tajnehaslo123', 'Właściciel', '123-456-789')
ON CONFLICT (email) DO NOTHING;

INSERT INTO tanks (name, fuel_type, capacity_liters, current_level_liters, pressure_kpa, temperature_c, safety_valve_active, status)
VALUES ('Zbiornik Główny PB95', 'PB95', 20000.00, 15400.50, 101.3, 14.5, false, 'OK')
ON CONFLICT DO NOTHING;
-- (Uwaga: dla tabeli tanks trzeba by dodać unikalny klucz na nazwę zbiornika, żeby ON CONFLICT zadziałało w pełni poprawnie)