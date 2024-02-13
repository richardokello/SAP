package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

@Table(name = "INENTORY")
@Data
@Entity
public class Inventory {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "INVENTORY_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "\"Product_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "INVENTORY_ID")
    @NonNull
    private String inventoryId;
    @JoinColumn( name = "PRODUCTS_ID", referencedColumnName = "PRODUCTS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "PRODUCTS_ID", nullable = false)
    private Products productID;
    @Column(name = "QUANTITY_AVAILABLE")
    @NonNull
    private int availableQuantity;
    @JoinColumn( name = "LOCATION", referencedColumnName = "LOCATION_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "LOCATION", nullable = false)
    private Location location;
   public Inventory(){}
}
