package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @JoinColumn( name = "ORDER_ITEM_ID", referencedColumnName = "ORDERS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
  //  @Column(name = "ORDER_ID", nullable = false)
    private Order order;
    @JoinColumn( name = "PRODUCTS_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
   // @Column(name = "PRODUCTS_ID", nullable = false)
    private Products productNO;
    @JoinColumn( name = "ORDER_NO", referencedColumnName = "ORDERS_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Column(name = "ORDER_NO", nullable = false)
    private Order orderNo;
    private int quantity;
    @Column(name = "UNITY_PRICE")
    @Nonnull
    private BigDecimal unitPrice;

}
