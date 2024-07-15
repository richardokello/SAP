package co.ke.spsat.bowip.dtos.stockDTOs;

import lombok.Data;

@Data
public class StockRemovalRequest {
    private Long productId;
    private Long warehouseId;
    private Long quantity;
    private Long userId;
    private String reason;
}
