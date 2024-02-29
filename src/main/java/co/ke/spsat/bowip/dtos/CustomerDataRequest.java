package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Address;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.Regions;
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
    private String firstName;
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
     private Address address;
     private Regions regions;
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    private String phone;
    private String email;

    private Address shippingAddress;
    private String businessName;
    private String localRegistrationNumber;
    private String businessLicenseNumber;
    private String KRAPIN;
    private String businessPrimaryContactNo;
    private String businessSecondaryContactNo;
}
