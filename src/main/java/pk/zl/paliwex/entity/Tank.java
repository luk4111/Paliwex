package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tanks")
public class Tank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "capacity_liters", nullable = false)
    private BigDecimal capacityLiters;

    @Column(name = "current_level_liters")
    private BigDecimal currentLevelLiters;

    @Column(name = "pressure_kpa")
    private BigDecimal pressureKpa;

    @Column(name = "temperature_c")
    private BigDecimal temperatureC;

    private String status;

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
    public BigDecimal getPressureKpa() { return pressureKpa; }
    public void setPressureKpa(BigDecimal pressureKpa) { this.pressureKpa = pressureKpa; }
    public BigDecimal getTemperatureC() { return temperatureC; }
    public void setTemperatureC(BigDecimal temperatureC) { this.temperatureC = temperatureC; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}