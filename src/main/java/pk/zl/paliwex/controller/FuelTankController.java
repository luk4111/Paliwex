package pk.zl.paliwex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.FuelTank;
import pk.zl.paliwex.repository.FuelTankRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/fuel")
public class FuelTankController {

    private final FuelTankRepository fuelTankRepository;

    public FuelTankController(FuelTankRepository fuelTankRepository) {
        this.fuelTankRepository = fuelTankRepository;
    }

    // Podgląd wszystkich zbiorników
    @GetMapping
    public List<FuelTank> getAllTanks() {
        return fuelTankRepository.findAll();
    }
    @PostMapping("/sell")
    public ResponseEntity<?> sellFuel(@RequestParam String fuelType, @RequestParam Double liters) {
        // 1. Szukamy odpowiedniego zbiornika w bazie
        FuelTank tank = fuelTankRepository.findByFuelType(fuelType)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono zbiornika dla: " + fuelType));

        // 2. Sprawdzamy, czy mamy tyle paliwa na stanie
        BigDecimal amountToSell = BigDecimal.valueOf(liters);
        if (tank.getCurrentLiters().compareTo(amountToSell) < 0) {
            return ResponseEntity.badRequest().body("Błąd: Za mało paliwa w zbiorniku! Zostało tylko: " + tank.getCurrentLiters() + " l");
        }

        // 3. Odejmowanie paliwa ze zbiornika
        tank.setCurrentLiters(tank.getCurrentLiters().subtract(amountToSell));
        fuelTankRepository.save(tank);

        // 4. (Opcjonalnie) Tu moglibyśmy dodać tworzenie nowej Transakcji,
        // ale na razie sprawdźmy, czy samo odejmowanie litrów działa.

        return ResponseEntity.ok("Sprzedano " + liters + " l paliwa " + fuelType + ". Pozostało w zbiorniku: " + tank.getCurrentLiters() + " l");
    }
}
