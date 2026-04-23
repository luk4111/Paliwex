package pk.zl.paliwex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.Tank;
import pk.zl.paliwex.repository.TankRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tanks")
public class TankController {

    private final TankRepository tankRepository;

    public TankController(TankRepository tankRepository) {
        this.tankRepository = tankRepository;
    }

    // Pobierz listę wszystkich zbiorników
    @GetMapping
    public List<Tank> getAllTanks() {
        return tankRepository.findAll();
    }

    // Pobierz dane konkretnego zbiornika (np. /api/tanks/1)
    @GetMapping("/{id}")
    public ResponseEntity<Tank> getTankById(@PathVariable Integer id) {
        return tankRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}