package pk.zl.paliwex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pk.zl.paliwex.dto.LoginRequest;
import pk.zl.paliwex.dto.RegisterRequest;
import pk.zl.paliwex.entity.User;
import pk.zl.paliwex.repository.UserRepository;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Dodajemy nasz szyfrator

    // Spring sam wstrzyknie tu repozytorium i szyfrator
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błąd: Nie znaleziono użytkownika.");
        }

        User user = userOptional.get();

        // ZMIANA: Używamy maszynki do porównania wpisanego hasła z tym zaszyfrowanym w bazie
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błąd: Nieprawidłowe hasło.");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Błąd: Ten adres e-mail jest już zajęty.");
        }

        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setRole("Klient");

        // ZMIANA: Szyfrujemy hasło przed zapisaniem go do bazy!
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        // Generujemy losowy, unikalny numer (np. PAL-4F8A2D)
        String generatedNumber = "PAL-" + java.util.UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        newUser.setClientNumber(generatedNumber);
        userRepository.save(newUser);
        return ResponseEntity.ok("Rejestracja zakończona sukcesem!");
    }
}