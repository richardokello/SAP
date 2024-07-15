package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
    Optional<Batch> findById(Long id);
    Optional<Batch>findByProduct(Products Id);
    Optional<Batch>findBatchesByProductProductCodeContains(String productCode);
    Optional<Batch>findBatchesByProductProductCode(String productCode);

}
