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
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BATCH_SEQ")
    @SequenceGenerator(name = "BATCH_SEQ", sequenceName = "\"Batch_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "BATCH_ID")
    @NonNull
    private Integer batchId;

    @JoinColumn( name = "PRODUCTS", referencedColumnName = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Products products;
    @NotNull
    private int quantity;
    @NotNull
    private Date manufacturingDate;
    @NotNull
    private Date expirationDate;
    public Batch() {

    }

    // Constructors, getters, setters, and other methods

}
