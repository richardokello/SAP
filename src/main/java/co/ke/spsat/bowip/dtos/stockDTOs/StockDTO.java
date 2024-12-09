package co.ke.spsat.bowip.dtos.stockDTOs;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.Location;
import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.entities.Supplier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class StockDTO {
    private Long productId; // Reference to the corresponding product
    private Long quantity;
    private Long reorder_point;
    private Location location;
    private Long minStockLevel;
    private Long reorderPoint;// Use java.util.Date for simplicity
    private Batch batchNumber;
    private Supplier supplierId;
    private Long warehouseId;
    private Long batchId;
    private long quantityOnHand;
    private Double unitPrice;
    private Long safetyStock;
    private String serialNumber;





//    public class Stock {
//        private Long stockId; // Unique identifier for the stock item
//        private String itemName; // Name of the item
//        private String description; // Description of the item
//        private String category; // Category of the item
//        private int quantityOnHand; // Current stock level
//        private int reorderPoint; // Reorder point for the item
//        private int safetyStock; // Safety stock level
//        private double unitPrice; // Unit price of the item
//        private String batchNumber; // Optional batch/lot number
//        private String serialNumber; // Optional serial number
//        private Long warehouseId; // Identifier for the warehouse storing the item
//        private Date expiryDate; // Expiry date for perishable items
//        private Date lastUpdated; // Last updated timestamp
//        private Date createdDate; // Created timestamp
//
//        // Getters and setters for all fields
//        // Constructor, equals, hashCode, and toString methods
//    }
}
