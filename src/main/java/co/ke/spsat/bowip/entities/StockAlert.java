package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "STOCK_ALERT")
public class StockAlert {
    @Id
    private Long productId;
    private int threshold;
    private boolean alertActive;

}
