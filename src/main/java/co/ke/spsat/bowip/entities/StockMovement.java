package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "STOCK_MOVEMENT")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MOVEMENT_ID")
    private Long movementId;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
   // @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PRODUCTS_ID", referencedColumnName = "PRODUCT_ID")
    private Products products;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Column(name = "WAREHOUSE_ID", nullable = false)
    @JoinColumn(name = "WAREHOUSE_ID", referencedColumnName = "WAREHOUSE_ID")
    private Warehouse warehouse;

    private Long quantityMoved;
    private String movementType;

}
