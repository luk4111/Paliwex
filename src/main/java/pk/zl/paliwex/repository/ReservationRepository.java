package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Szukamy rezerwacji, które nakładają się czasowo na to samo stanowisko
    boolean existsByStandNumberAndStartTimeBeforeAndEndTimeAfter(
            Integer standNumber,
            LocalDateTime endTime,
            LocalDateTime startTime
    );
}