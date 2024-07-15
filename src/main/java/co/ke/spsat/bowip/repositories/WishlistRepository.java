package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByCustomerCustomerId(Long customerId);
}
