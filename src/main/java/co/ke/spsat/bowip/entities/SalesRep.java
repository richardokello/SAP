package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.NonNull;

@Entity
@Data
@Table(name = "SALES_REP")

public class SalesRep {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SALES_REP_SEQ")
    @SequenceGenerator(name = "SALES_REP_SEQ", sequenceName = "\"Sales_rep_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "SALES_REP_ID")
    @NonNull
    private Long id;
    @Column(name = "SALES_REP_NAME")
    @NonNull
    private String name;
    public SalesRep() {

    }
}
