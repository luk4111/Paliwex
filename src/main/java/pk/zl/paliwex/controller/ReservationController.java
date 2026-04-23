package pk.zl.paliwex.controller;

import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.Reservation;
import pk.zl.paliwex.entity.Transaction;
import pk.zl.paliwex.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import pk.zl.paliwex.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    // ZAKTUALIZUJ KONSTRUKTOR:
    public ReservationController(ReservationRepository reservationRepository,
                                 TransactionRepository transactionRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        // 1. Sprawdzamy, czy termin jest zajęty
        boolean isOccupied = reservationRepository.existsByStandNumberAndStartTimeBeforeAndEndTimeAfter(
                reservation.getStandNumber(), // <--- Tu teraz będzie pasować!
                reservation.getEndTime(),
                reservation.getStartTime()
        );

        if (isOccupied) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Błąd: Stanowisko " + reservation.getStandNumber() + " jest już zajęte w tym czasie!");
        }

        // 2. Jeśli wolne, ustawiamy status i zapisujemy
        if (reservation.getStatus() == null) {
            reservation.setStatus("PENDING");
        }

        Reservation saved = reservationRepository.save(reservation);
        return ResponseEntity.ok(saved);
    }

}