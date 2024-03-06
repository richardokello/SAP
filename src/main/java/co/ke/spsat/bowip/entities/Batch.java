package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.lang.NonNull;

import java.util.Date;
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

    @Column(name = "BATCH_NAME")
    private String batchName;
    @Column(name = "BATCH_NUMBER")
     private String batchNo;

    @Column(name = "MANUFACTURING_DATE")
    private Date manufacturingDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Products product;
    public Batch() {

    }
}
