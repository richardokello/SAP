package co.ke.spsat.bowip.dtos;
import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.ProductCategory;
import java.math.BigDecimal;
import java.util.List;

public record ProductsData(Long productId, String productName, String brand, String description, String colour,
                           String size, String usageInstructions, String weight, ProductCategory categoryId, Long categoriesId,
                           String length, String productCode, String image, BigDecimal price, Integer quantityInStock,
                           List<Batch>batches, String SKU, String status) {
}
