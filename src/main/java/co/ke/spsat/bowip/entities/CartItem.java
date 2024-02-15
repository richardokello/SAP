package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Entity @Data
@Table(name = "CART_ITEM")
public class CartItem {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CART_ITEM_SEQ")
    @SequenceGenerator(name = "CART_ITEM_SEQ", sequenceName = "\"Cart_Item_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "CARTITEM_ID")
    @NonNull
    private Long id;
    @JoinColumn( name = "CART", referencedColumnName = "CART_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ShoppingCart cart;
    @JoinColumn( name = "PRODUCTS", referencedColumnName = "PRODUCT_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Products products;
    @Nonnull
    private int quantity;
    @Nonnull
    private BigDecimal unitPrice;
    @Nonnull
    private BigDecimal totalAmount;

    public CartItem() {

    }
}
