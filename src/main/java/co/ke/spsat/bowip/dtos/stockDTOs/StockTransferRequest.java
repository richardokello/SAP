package co.ke.spsat.bowip.dtos.stockDTOs;

import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.entities.Users;
import co.ke.spsat.bowip.entities.Warehouse;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class StockTransferRequest {
    private Warehouse fromWarehouseId;
    private Warehouse toWarehouseId;
    private Products productId;
    private Long quantity;
    private Long requestedBy;
    private String status;
    private Long approvedBy;

    private Date requestDate;
    private Date approvalDate;

}
