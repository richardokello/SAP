package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
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
    @ManyToOne
    private Products product;
    private int availableQuantity;
    @ManyToOne
    private Location location;
   public Inventory(){}
}
