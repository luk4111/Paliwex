package pk.zl.paliwex.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_name", unique = true)
    private String itemName; // np. "WASH_PREMIUM", "WASH_BASIC"

    private BigDecimal price;

    private String category; // np. "MYJNIA", "PALIWO"

    // --- Gettery i Settery ---
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}