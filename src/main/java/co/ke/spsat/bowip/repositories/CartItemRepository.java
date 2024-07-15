package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
