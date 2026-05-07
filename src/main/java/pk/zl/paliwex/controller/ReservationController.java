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
        // Sprawdzamy nową, precyzyjną metodą
        boolean occupied = reservationRepository.isOccupied(
                reservation.getStandNumber(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );

        if (occupied) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Błąd: Stanowisko " + reservation.getStandNumber() + " jest zajęte w tym terminie!");
        }

        if (reservation.getStatus() == null) {
            reservation.setStatus("PENDING");
        }

        return ResponseEntity.ok(reservationRepository.save(reservation));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus("CANCELLED");
                    reservationRepository.save(reservation);

                    return ResponseEntity.ok("Rezerwacja odwołana pomyślnie.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}