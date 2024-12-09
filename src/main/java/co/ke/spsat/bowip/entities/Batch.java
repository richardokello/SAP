package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Table(name = "BATCH")
@Data
@Entity
public class Batch {



    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_SEQ")
    @SequenceGenerator(name = "BATCH_SEQ", sequenceName = "\"Batch_Seq\"", allocationSize = 1)
    @Id
    @Column(name = "BATCH_ID")
    @NonNull
    private Long batchId;

    @Column(name = "BATCH_NUMBER")
     private String batchNo;

    @Column(name = "MANUFACTURING_DATE")
    private LocalDate manufacturingDate;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;
    private int initialQuantity;
    private int currentQuantity;
    private String status;  // "available", "expired", "low_stock", "defective"
    private String qualityStatus;  // "ok", "damaged", etc.
    private Long quantity; // Total quantity in this batch

   // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
   private List<Products> products;
    public Batch() {



    }
}
