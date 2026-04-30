package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.FuelTank;
import java.util.Optional;

@Repository
public interface FuelTankRepository extends JpaRepository<FuelTank, Integer> {
    // Dodatkowo dodajemy szukanie po typie paliwa (przyda się przy tankowaniu)
    Optional<FuelTank> findByFuelType(String fuelType);
}