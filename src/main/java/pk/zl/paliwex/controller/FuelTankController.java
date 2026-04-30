package pk.zl.paliwex.controller;

import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.FuelTank;
import pk.zl.paliwex.repository.FuelTankRepository;
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
}