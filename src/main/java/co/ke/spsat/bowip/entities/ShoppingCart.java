package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "SHOPPING_CART")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @Column(name = "CUSTOMER_ID", nullable = false)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    private Customers customer;
    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
  //  @Column(name = "CARTITEM_ID", nullable = false)
    @JoinColumn(name = "CARTITEM_ID")//, referencedColumnName = "CARTITEM_ID")
    private List<CartItem> cartItems;
    private BigDecimal totalAmount;
    private LocalDateTime creationDate;
    private boolean isActive;
}
