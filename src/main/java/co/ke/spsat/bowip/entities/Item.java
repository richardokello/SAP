package co.ke.spsat.bowip.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Item {
    private Long stockItemId;         // Unique identifier for the stock item
    private Long stockId;             // Foreign key linking to the Stock class
    private int quantityReceived;     // Quantity of items received
    private double unitPrice;         // Price per unit of the item received
    private String batchNumber;       // Batch/lot number of the item
    private String serialNumber;      // Serial number of the item (if applicable)
    private Date expiryDate;          // Expiration date of the item (if applicable)
    private Date receivedDate;
}
