package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "WISHLISTS")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customers customer;

    @ManyToMany
    @JoinTable(
            name = "WISHLIST_ITEMS",
            joinColumns = @JoinColumn(name = "WISHLIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<Products> products;
}
