package pk.zl.paliwex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.Tank;
import pk.zl.paliwex.entity.Transaction;
import pk.zl.paliwex.repository.TankRepository;
import pk.zl.paliwex.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fuel")
public class FuelController {

    private final TankRepository tankRepository;
    private final TransactionRepository transactionRepository;

    public FuelController(TankRepository tankRepository, TransactionRepository transactionRepository) {
        this.tankRepository = tankRepository;
        this.transactionRepository = transactionRepository;
    }

    // Pobieranie stanu wszystkich zbiorników
    @GetMapping("/status")
    public List<Tank> getAllTanks() {
        return tankRepository.findAll();
    }

    // Metoda sprzedaży paliwa
    @PostMapping("/sell")
    @Transactional
    public ResponseEntity<?> sellFuel(
            @RequestParam String fuelType,
            @RequestParam Double liters,
            @RequestParam(required = false) Integer clientId) {

        // 1. Szukanie zbiornika
        Tank tank = tankRepository.findByFuelType(fuelType)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zbiornika dla paliwa: " + fuelType));

        // 2. Sprawdzanie ilości
        BigDecimal amountToSell = BigDecimal.valueOf(liters);
        if (tank.getCurrentLevelLiters().compareTo(amountToSell) < 0) {
            return ResponseEntity.badRequest().body("Błąd: Za mało paliwa w zbiorniku " + fuelType);
        }

        // 3. Aktualizacja stanu zbiornika
        tank.setCurrentLevelLiters(tank.getCurrentLevelLiters().subtract(amountToSell));
        tankRepository.save(tank);

        // 4. Tworzenie transakcji
        BigDecimal unitPrice = new BigDecimal("6.50"); // Można później brać z tabeli prices
        BigDecimal totalAmount = unitPrice.multiply(amountToSell);

        Transaction transaction = new Transaction();
        transaction.setClientId(clientId);
        transaction.setTransactionType("FUEL");
        transaction.setFuelWashType(fuelType);
        transaction.setQuantityLiters(amountToSell);
        transaction.setUnitPricePln(unitPrice);
        transaction.setTotalAmountPln(totalAmount);
        transaction.setPaymentMethod("CARD");
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus("COMPLETED");

        transactionRepository.save(transaction);

        return ResponseEntity.ok("Sprzedano " + liters + "l " + fuelType + ". Kwota: " + totalAmount + " PLN");
    }
}