package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "employee_id")
    private Integer employee_id;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "fuel_wash_type")
    private String fuelWashType;

    @Column(name = "quantity_liters", nullable = false)
    private BigDecimal quantityLiters;

    @Column(name = "unit_price_pln")
    private BigDecimal unitPricePln;

    @Column(name = "total_amount_pln", nullable = false)
    private BigDecimal totalAmountPln;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_at")
    private LocalDateTime transactionAt;

    @Column(name = "status")
    private String status;

    // --- GETTERY I SETTERY (Generuj je przez Alt+Insert) ---

    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public void setFuelWashType(String fuelWashType) { this.fuelWashType = fuelWashType; }
    public void setQuantityLiters(BigDecimal quantityLiters) { this.quantityLiters = quantityLiters; }
    public void setUnitPricePln(BigDecimal unitPricePln) { this.unitPricePln = unitPricePln; }
    public void setTotalAmountPln(BigDecimal totalAmountPln) { this.totalAmountPln = totalAmountPln; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setTransactionAt(LocalDateTime transactionAt) { this.transactionAt = transactionAt; }
    public void setStatus(String status) { this.status = status; }
}