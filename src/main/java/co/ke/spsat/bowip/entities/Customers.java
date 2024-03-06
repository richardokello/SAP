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
    @Column(name = "OWNER", nullable = false)
//    @Nonnull
    private String directorName;
    @Column(name = "EMAIL", nullable = false)
    private String businessEmail;
   // @Column(name = "SHIPPING_ADDRESS", nullable = false)
    @JoinColumn( name = "SHIPPING_ADDRESS", referencedColumnName = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address shippingAddress;
 @Column(name = "CUSTOMER_CODE", nullable = false)
 @Nonnull
    private String customerCode;
    @Column(name = "BUSINESS_NAME", nullable = false)
    @Nonnull
    private String businessName;
    private String localRegistrationNumber;
    private String businessLicenseNumber;
    @Column(name = "KRA_PIN", nullable = false)
    @Nonnull
    private String KRA_PIN;
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    private String businessPrimaryContactNo;
    @JoinColumn( name = "REGIONS", referencedColumnName = "REGION_ID")
    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Regions regions;

 public Customers() {

 }
}
