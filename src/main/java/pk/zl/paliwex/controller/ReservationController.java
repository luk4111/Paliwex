package pk.zl.paliwex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.entity.Reservation;
import pk.zl.paliwex.repository.ReservationRepository;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 1. POBIERANIE - To dzięki temu lista rezerwacji pojawi się na stronie
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // 2. DODAWANIE - Z weryfikacją zajętości stanowiska
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        // Sprawdzamy czy stanowisko jest zajęte w podanym czasie
        boolean occupied = reservationRepository.isOccupied(
                reservation.getStandNumber(),
                reservation.getStartTime(),
                reservation.getEndTime()
        );

        if (occupied) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Błąd: Stanowisko " + reservation.getStandNumber() + " jest już zajęte w tym terminie!");
        }

        // Domyślny status jeśli nie przesłano
        if (reservation.getStatus() == null) {
            reservation.setStatus("OCZEKUJE");
        }

        return ResponseEntity.ok(reservationRepository.save(reservation));
    }

    // 3. ODWOŁYWANIE (USUWANIE) - Tego pewnie brakowało frontendowi
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer id) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setStatus("ANULOWANE");
            reservationRepository.save(reservation);
            return ResponseEntity.ok().body("Rezerwacja została anulowana.");
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/client/{clientId}")
    public List<Reservation> getClientReservations(@PathVariable Integer clientId) {
        return reservationRepository.findByClientId(clientId);
    }
}