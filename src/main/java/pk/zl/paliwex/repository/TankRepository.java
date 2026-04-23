package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.Tank;

@Repository
public interface TankRepository extends JpaRepository<Tank, Integer> {
}