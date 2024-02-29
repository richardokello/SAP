package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.ProductCategory;
import co.ke.spsat.bowip.entities.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private String productName;
    private String brand;
    private String description;
    private String colour;
    private String size;
    private String usageInstructions;
    private String weight;
    private ProductCategory categoryId;  // Assuming categoryId is a Long representing the ID of ProductCategory
    private String length;
    private String productCode;
    private String image;
    private BigDecimal price;
    private Integer quantityInStock;
    private List<BatchRequest> batchRequests;  // List of BatchRequest objects
    private String SKU;
    private String status = "Active";
    private Boolean deleted = Boolean.FALSE;
    private Supplier supplier;

}
