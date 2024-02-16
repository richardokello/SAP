package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@AllArgsConstructor
public class ProductDTO {
    private  Long productId;

    private  String productName;

    private  String brand;

    private  String description;

    private String colour;

    private String size;

    private String usageInstructions;

    private  String weight;

    private Categories categoryId;

    private Long categoriesId;

    private String length;

    private String productCode;

    private String image;

    private BigDecimal price;
    private Integer quantityInStock;
    private List<Batch> batches;
    private String SKU;

    private  String status="Active";

}
