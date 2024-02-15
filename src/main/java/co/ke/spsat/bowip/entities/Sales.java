package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SALES_TABLE")
public class Sales {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SALES_SEQ")
    @SequenceGenerator(name = "SALES_SEQ", sequenceName = "\"Sales_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "SALES_ID")
    @NonNull
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "PRODUCT_ID", nullable = false)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", insertable = false,updatable = false)
    private Products productID;
    @Column(name = "QUANTITY")
    @NonNull
    private int quantitySold;
    @Column(name = "SALESDATE")
    @NonNull
    private LocalDateTime salesDate;
    @Column(name = "SALES_AMOUNT")
    @NonNull
    private Double salesAmount;

    public Sales() {

    }
}
