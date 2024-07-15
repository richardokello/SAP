package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CustomerSupport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {
    List<CustomerSupport> findByCustomerCustomerId(Long customerId);
}
