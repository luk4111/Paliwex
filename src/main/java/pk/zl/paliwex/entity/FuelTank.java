package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tanks") // Zmieniliśmy z fuel_tanks na tanks
public class FuelTank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "current_level_liters") // Dopasowane do schematu
    private BigDecimal currentLiters;

    @Column(name = "capacity_liters") // Dopasowane do schematu
    private BigDecimal maxCapacity;

    // --- Konstruktory ---
    public FuelTank() {}

    public FuelTank(String fuelType, BigDecimal currentLiters, BigDecimal maxCapacity) {
        this.fuelType = fuelType;
        this.currentLiters = currentLiters;
        this.maxCapacity = maxCapacity;
    }

    // --- Gettery i Settery ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public BigDecimal getCurrentLiters() { return currentLiters; }
    public void setCurrentLiters(BigDecimal currentLiters) { this.currentLiters = currentLiters; }

    public BigDecimal getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(BigDecimal maxCapacity) { this.maxCapacity = maxCapacity; }
}