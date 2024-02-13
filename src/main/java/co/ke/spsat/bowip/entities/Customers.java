package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Customers {
    @Id
    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private Address shippingAddress;
    private Address billingAddress;
    private List<Order> orders;

}
