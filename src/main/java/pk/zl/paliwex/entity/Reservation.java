package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id", nullable = false)
    private Integer clientId; // Na razie uprościmy to do samego ID klienta

    @Column(name = "stand_number", nullable = false)
    private Integer standNumber;

    @Column(name = "wash_type", nullable = false)
    private String washType;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private String status; // np. "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"

    // --- Gettery i Settery ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getClientId() { return clientId; }
    public void setClientId(Integer clientId) { this.clientId = clientId; }
    public Integer getStandNumber() { return standNumber; }
    public void setStandNumber(Integer standNumber) { this.standNumber = standNumber; }
    public String getWashType() { return washType; }
    public void setWashType(String washType) { this.washType = washType; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}