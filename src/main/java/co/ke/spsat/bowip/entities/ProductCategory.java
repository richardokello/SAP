package co.ke.spsat.bowip.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Table(name = "PRODUCT_CATEGORY")
@Data
@Entity
@AllArgsConstructor

public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;
    @Column(name = "CATEGORY_NAME")
    @Nonnull
    private String categoryName;
//    @Nonnull
//    @Column(name = "DESCRIPTION")
//    private String description;

    public ProductCategory() {

    }
}
