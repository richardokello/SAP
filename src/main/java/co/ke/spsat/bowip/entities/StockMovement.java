package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "STOCK_MOVEMENT")
public class StockMovement {
    @Id
    private Long movementId;
    private Products products;
    private Warehouse warehouse;
    private Long quantityMoved;
    private String movementType;

}
