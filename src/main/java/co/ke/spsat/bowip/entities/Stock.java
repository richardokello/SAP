package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "STOCK")
public class Stock {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "STOCK_SEQ")
    @SequenceGenerator(name = "STOCK_SEQ", sequenceName = "\"Stock_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "STOCK_ID")
    @NonNull
    private String stockId;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
  //  @Column(name = "PRODUCT_ID", nullable = false)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Products productId;
    // Reference to the corresponding product// Use java.util.Date for simplicity
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "BATCH_ID", referencedColumnName = "BATCH_ID")
    private Batch batchNumber;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "id")
//  //  @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "transactionId")
//    @JsonIgnore
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<StockTransaction> transactions;

    private Long safetyStock;
    //private String warehouse;
    public Stock() {

    }

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id")
    private Warehouse warehouseId;

    @Column(name = "quantity_on_hand")
    private Long quantityOnHand;

    @Column(name = "min_stock_level")
    private Long minStockLevel;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "reorder_point")
    private Long reorderPoint;

    @OneToMany(mappedBy = "stocks", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StockTransaction> transactions;
}
