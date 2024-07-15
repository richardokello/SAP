package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findById(Long shoppingCartId);
    Optional<ShoppingCart>findByCustomerCustomerId(Long customerId);
}
