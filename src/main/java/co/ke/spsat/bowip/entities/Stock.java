package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@Entity
@Table(name = "STOCK")
public class Stock {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "\"Product_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "STOCK_ID")
    @NonNull
    private String stockId;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
  //  @Column(name = "PRODUCT_ID", nullable = false)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Products productId; // Reference to the corresponding product
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Column(name = "LOCATIONID", nullable = false)
    @JoinColumn(name = "LOCATIONID", referencedColumnName = "LOCATION_ID")
    private Location location;
    private int minStockLevel;
    private int maxStockLevel;
    private int reorderPoint;
    private Date arrivalDate; // Use java.util.Date for simplicity
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Column(name = "BATCH_ID", nullable = false)
    @JoinColumn(name = "BATCH_ID", referencedColumnName = "BATCH_ID")
    private Batch batchNumber;

    public Stock() {

    }
}
