package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Discount;
import co.ke.spsat.bowip.entities.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount>findByCode(String code);
    Optional<Discount>findByIsActive(Boolean isActive);
}
