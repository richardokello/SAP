package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "PRODUCTS")
@Data
//@SQLDelete(sql = "UPDATE PRODUCTS SET deleted = true WHERE productId=?")
//@FilterDef(name = "deletedProductsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductsFilter", condition = "deleted = :isDeleted")
@Entity
public class Products {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "\"Product_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "PRODUCT_ID")
    @NonNull
    private  Long productId;
    @Column(name = "PRODUCT_NAME")
    private  String productName;
    @Column(name = "BBRAND")
    private  String brand;
    @Column(name = "DESCRIPTION")
    private  String description;
    @Column(name = "ITEM_COLOUR")
    private String colour;
    @Column(name = "SIZE")
    private String size;
    @Column(name = "USAGE_INSTRUCTIONS")
    private String usageInstructions;
    @Column(name = "WEIGHT")
    private  String weight;
    @OneToMany
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "categoryID", insertable = false,updatable = false)
    private Categories categoryId;
    @Column(name = "CATEGORYID")
    private Long categoriesId;
    @Column(name = "ITEM_LENGTH")
    private String lenth;
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    private String image;
    @Column(name = "PRICE")
    private BigDecimal price;
    private Integer quantityInStock;
    private List<Batch> batches;

    @Column(name = "STATUS")
    private  String status="Active";
    private  Boolean deleted=Boolean.FALSE;

    public Products(@NonNull Long productId,
                    String productName,
                    String brand, String description,
                    String colour, String size,
                    String usageInstructions,
                    String weight, String length,
                    String productCode, BigDecimal price,
                    String status, Boolean deleted) {
        this.productId = productId;
        this.productName = productName;
        this.brand = brand;
        this.description = description;
        this.colour = colour;
        this.size = size;
        this.usageInstructions = usageInstructions;
        this.weight = weight;
        this.lenth = length;
        this.productCode = productCode;
        this.price = price;
        this.status = status;
        this.deleted = deleted;
    }

    public Products() {

    }
}