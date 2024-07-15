package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CustomerInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInteractionRepository extends JpaRepository<CustomerInteraction, Long> {
}
