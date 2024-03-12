package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long> {
}
