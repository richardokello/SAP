package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "STOCK_ADJUSTMENT")
public class StockAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Stock stock;
    private int adjustmentQuantity;
    private String reason;
    private LocalDateTime adjustmentDate;

}
