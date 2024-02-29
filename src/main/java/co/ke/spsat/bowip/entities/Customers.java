package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;


@Entity
@Data
@Table(name = "CUSTOMERS")
@AllArgsConstructor
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "CUSTOMER_ID", updatable = false, nullable = false)
    private Long customerId;
    @Column(name = "FIRSTNAME", nullable = false)
    @Nonnull
    private String firstName;
    @Column(name = "LASTNAME", nullable = false)
    @Nonnull
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;
   // @Column(name = "SHIPPING_ADDRESS", nullable = false)
    @JoinColumn( name = "SHIPPING_ADDRESS", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address shippingAddress;
    @JoinColumn( name = "BILLING_ADDRESS", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @Column(name = "BILLING_ADDRESS", nullable = false)
    private Address billingAddress;

    @JoinColumn( name = "REGIONS", referencedColumnName = "REGION_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Regions regions;

 public Customers() {

 }
}
