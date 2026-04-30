package pk.zl.paliwex.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.FuelTank;
import pk.zl.paliwex.entity.Transaction;
import pk.zl.paliwex.repository.FuelTankRepository;
import pk.zl.paliwex.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fuel")
public class FuelTankController {

    private final FuelTankRepository fuelTankRepository;
    private final TransactionRepository transactionRepository; // 1. DODAJ TO POLE

    // 2. ZAKTUALIZUJ KONSTRUKTOR
    public FuelTankController(FuelTankRepository fuelTankRepository,
                              TransactionRepository transactionRepository) {
        this.fuelTankRepository = fuelTankRepository;
        this.transactionRepository = transactionRepository;
    }

    // Podgląd wszystkich zbiorników
    @GetMapping
    public List<FuelTank> getAllTanks() {
        return fuelTankRepository.findAll();
    }
    @PostMapping("/sell")
    @Transactional // Ważne: zapewnia, że jeśli transakcja się nie zapisze, to paliwo nie zniknie
    public ResponseEntity<?> sellFuel(
            @RequestParam String fuelType,
            @RequestParam Double liters,
            @RequestParam(required = false) Integer clientId) {

        // 1. Szukamy zbiornika (teraz w tabeli 'tanks' zgodnie z nowym schema.sql)
        FuelTank tank = fuelTankRepository.findByFuelType(fuelType)
                .orElseThrow(() -> new RuntimeException("Brak zbiornika dla: " + fuelType));

        // 2. Weryfikacja ilości paliwa
        BigDecimal amountToSell = BigDecimal.valueOf(liters);
        if (tank.getCurrentLiters().compareTo(amountToSell) < 0) {
            return ResponseEntity.badRequest().body("Błąd: Za mało paliwa! Stan: " + tank.getCurrentLiters());
        }

        // 3. Obliczanie ceny (na sztywno 6.50 zł lub pobranie z tabeli prices w przyszłości)
        BigDecimal unitPrice = new BigDecimal("6.50");
        BigDecimal totalAmount = unitPrice.multiply(amountToSell);

        // 4. Odejmowanie paliwa
        tank.setCurrentLiters(tank.getCurrentLiters().subtract(amountToSell));
        fuelTankRepository.save(tank);

        // 5. ZAPIS TRANSAKCJI (zgodnie z nowym schema.sql)
        Transaction transaction = new Transaction();
        transaction.setClientId(clientId); // Może być null, jeśli klient niezarejestrowany
        transaction.setTransactionType("FUEL");
        transaction.setFuelWashType(fuelType);
        transaction.setQuantityLiters(amountToSell);
        transaction.setUnitPricePln(unitPrice);
        transaction.setTotalAmountPln(totalAmount);
        transaction.setPaymentMethod("CARD"); // Domyślnie karta
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus("COMPLETED");

        transactionRepository.save(transaction);

        return ResponseEntity.ok(String.format(
                "Sprzedano %.2f l paliwa %s. Razem: %.2f PLN. Pozostało: %.2f l",
                liters, fuelType, totalAmount, tank.getCurrentLiters()
        ));
    }
}
