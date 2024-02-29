package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Address;
import co.ke.spsat.bowip.entities.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRequest {

    private Long customerId;

    private String firstName;

    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;

    private Address shippingAddress;

    private Address billingAddress;

    private List<Order> orders;
}
