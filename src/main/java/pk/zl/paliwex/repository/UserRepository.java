package pk.zl.paliwex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.zl.paliwex.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // NOWA LINIJKA: Szukanie użytkownika po adresie email
    Optional<User> findByEmail(String email);
}