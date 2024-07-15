package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
}
