package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.Date;
@Table(name = "BATCH")
@Data
@Entity
public class Batch {
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BATCH_SEQ")
//    @SequenceGenerator(name = "BATCH_SEQ", sequenceName = "\"Batch_Seq\"",allocationSize = 1)
//    @Id
//    @Column(name = "BATCH_ID")
//    @NonNull
//    private Long batchId;
//
//
//    @ManyToOne
//    @JoinColumn(name = "BATCH_ID", insertable = false, updatable = false)
//    private Products product;
//    @javax.validation.constraints.NotNull
//    @Column(name = "BATCH_NAME")
//    private String batchName;

    public Batch() {

    }

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_SEQ")
    @SequenceGenerator(name = "BATCH_SEQ", sequenceName = "\"Batch_Seq\"", allocationSize = 1)
    @Id
    @Column(name = "BATCH_ID")
    @NonNull
    private Long batchId;

    @Column(name = "BATCH_NAME")
    private String batchName;

    @Column(name = "MANUFACTURING_DATE")
    private Date manufacturingDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private Products product;


}
