package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "PRODUCTS")
@Data
//@SQLDelete(sql = "UPDATE PRODUCTS SET deleted = true WHERE productId=?")
//@FilterDef(name = "deletedProductsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProductsFilter", condition = "deleted = :isDeleted")
@Entity
@AllArgsConstructor
public class Products {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "\"Product_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "PRODUCT_ID")
    @NonNull
    private  Long productId;
    @Column(name = "PRODUCT_NAME")
    private  String productName;
    @Column(name = "BRAND")
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

    @JoinColumn(name = "CATEGORYID", referencedColumnName = "categoryID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductCategory categoryId;
    @Column(name = "ITEM_LENGTH")
    private String length;
    @NonNull
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @NonNull
    @Column()
    private String image;
    @Column(name = "PRICE")
    private BigDecimal price;
    private Integer quantityInStock;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Batch> batches;

    private String SKU;
    @Column(name = "STATUS")
    private  String status="Active";
    private  Boolean deleted=Boolean.FALSE;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "SUPPLIER_ID")//, referencedColumnName = "BATCH_ID")
    private Supplier supplier;

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
        this.length = length;
        this.productCode = productCode;
        this.price = price;
        this.status = status;
        this.deleted = deleted;
    }

    public Products() {

    }
}