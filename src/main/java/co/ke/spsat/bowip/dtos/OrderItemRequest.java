package co.ke.spsat.bowip.dtos;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private Long quantity;
    private Long useId;

    public OrderItemRequest(Long productId, long quantity) {
    }
}
