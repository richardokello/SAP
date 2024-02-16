package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
