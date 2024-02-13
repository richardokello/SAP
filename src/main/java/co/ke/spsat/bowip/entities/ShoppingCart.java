package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long id;
    @OneToOne
    private Customers customer;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
    private BigDecimal totalAmount;
    private LocalDateTime creationDate;
    private boolean isActive;
}
