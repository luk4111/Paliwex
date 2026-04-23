package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {
    // Tutaj Spring automatycznie doda metody typu save, findAll, findById
}