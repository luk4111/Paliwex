package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.Tank;
import java.util.Optional;

@Repository
public interface TankRepository extends JpaRepository<Tank, Integer> {
    // Metoda do szukania zbiornika po typie paliwa (np. PB95)
    Optional<Tank> findByFuelType(String fuelType);
}