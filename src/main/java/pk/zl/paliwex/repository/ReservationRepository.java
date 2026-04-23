package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.Reservation;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.standNumber = :stand " +
            "AND (:start < r.endTime AND :end > r.startTime)")
    boolean isOccupied(@Param("stand") Integer stand,
                       @Param("start") LocalDateTime start,
                       @Param("end") LocalDateTime end);
}