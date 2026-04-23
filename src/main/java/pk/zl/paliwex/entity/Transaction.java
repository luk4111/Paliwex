package pk.zl.paliwex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime; // TO JEST KLUCZOWE DLA DATY

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;
    private BigDecimal amount;

    @Column(name = "payment_method")
    private String paymentMethod; // "CARD", "CASH", "POINTS"

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate = LocalDateTime.now();

    // Dodaj to pole do klasy Transaction
    @Column(name = "transaction_type")
    private String transactionType;

    // I oczywiście Getter i Setter (możesz wygenerować Alt+Insert)
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    private String description;

    // --- Gettery i Settery ---
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setDescription(String description) { this.description = description; }
}