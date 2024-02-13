package co.ke.spsat.bowip.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "ORDERS TABLE")
@Data
public class Order {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ORDERS_SEQ")
    @SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "\"Orders_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "ORDERS_ID")
    @NonNull
    private Long orderId;
    @JoinColumn( name = "PRODUCTS_ID", referencedColumnName = "PRODUCTS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "PRODUCTS_ID", nullable = false)
    private Customers customer;
    @JoinColumn( name = "ORDERITEM", referencedColumnName = "PRODUCTS_ID")
    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "PRODUCTS_ID", nullable = false)
    private List<OrderItem> orderItems;
    @Column(name = "ORDER_DATE")
    private Date orderDate;
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "ORDERSTATUS", nullable = false)
    @Enumerated
    private OrderStatus orderStatus;
    @JoinColumn( name = "SHOPPING_ID", referencedColumnName = "CART_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "SHOPPING_ID", nullable = false)
    private ShoppingCart shoppingCart;

    public Order() {

    }
}
