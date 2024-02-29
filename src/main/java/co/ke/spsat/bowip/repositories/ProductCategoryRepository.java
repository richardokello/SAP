package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
ProductCategory findByCategoryId(Long Id);
}
