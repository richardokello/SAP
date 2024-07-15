package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CUSTOMER_SUPPORT")
public class    CustomerSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customers customer;

    @Column(name = "QUERY")
    private String query;

    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "STATUS")
    private String status;
}
