package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class StockBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;
    private Double unitCostPrice;
    private Long quantityAvailable;

    private LocalDateTime receivedDate;

    public StockBatch(Long quantity, Double unitCostPrice, LocalDateTime receivedDate) {
        this.quantityAvailable = quantity;
        this.unitCostPrice = unitCostPrice;
        this.receivedDate = receivedDate;

    }

    public StockBatch() {

    }
}
