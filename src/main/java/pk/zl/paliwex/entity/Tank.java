package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tanks")
public class Tank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "capacity_liters", nullable = false)
    private BigDecimal capacityLiters;

    @Column(name = "current_level_liters")
    private BigDecimal currentLevelLiters;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- Gettery i Settery ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public BigDecimal getCapacityLiters() { return capacityLiters; }
    public void setCapacityLiters(BigDecimal capacityLiters) { this.capacityLiters = capacityLiters; }
    public BigDecimal getCurrentLevelLiters() { return currentLevelLiters; }
    public void setCurrentLevelLiters(BigDecimal currentLevelLiters) { this.currentLevelLiters = currentLevelLiters; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}