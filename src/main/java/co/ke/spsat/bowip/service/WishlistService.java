package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.entities.Wishlist;
import co.ke.spsat.bowip.repositories.ProductsRepository;
import co.ke.spsat.bowip.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductsRepository productRepository;

    public Wishlist createWishlist(Long customerId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(new Customers());
        wishlist.getCustomer().setCustomerId(customerId);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist addProductToWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        wishlist.getProducts().add(product);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProductFromWishlist(Long wishlistId, Long productId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        wishlist.getProducts().remove(product);
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlists(Long customerId) {
        return wishlistRepository.findByCustomerCustomerId(customerId);
    }
}
