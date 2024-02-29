package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.validation.constraints.Pattern;
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
    @Column(name = "PHONE", nullable = false)
    @Nonnull
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    private String phone;
    @Column(name = "EMAIL", nullable = false)
    private String email;
   // @Column(name = "SHIPPING_ADDRESS", nullable = false)
    @JoinColumn( name = "SHIPPING_ADDRESS", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address shippingAddress;
    private String businessName;
    private String localRegistrationNumber;
    private String businessLicenseNumber;
    private String KRAPIN;
    private String businessPrimaryContactNo;
    private String businessSecondaryContactNo;
    @JoinColumn( name = "REGIONS", referencedColumnName = "REGION_ID")
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Regions regions;

 public Customers() {

 }
}
