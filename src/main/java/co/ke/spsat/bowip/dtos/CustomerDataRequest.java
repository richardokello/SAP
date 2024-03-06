package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Address;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.Regions;
import co.ke.spsat.bowip.entities.Routes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.validation.constraints.Pattern;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequest {
    private Long customerId;
     private Regions regions;

    private Address shippingAddress;
    private String businessName;
    private String localRegistrationNumber;
    private String businessLicenseNumber;
    private String KRAPIN;
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    private String businessPrimaryContactNo;
    private String directorName;
    @Column(name = "EMAIL", nullable = false)
    private String businessEmail;
    private String customerCode;
    private Routes roues;

}
