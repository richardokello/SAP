package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
//    @Query("SELECT p FROM Products p LEFT JOIN FETCH p.batches LEFT JOIN FETCH p.supplier")
//    List<Products> findAllWithBatchesAndSupplier();

    @Query("SELECT p FROM Products p JOIN FETCH p.batches b WHERE b.batchId = :batchId")
    //Optional
    List<Products> findByBatchId(@Param("batchId") Long batchId);
    @Query("SELECT DISTINCT p FROM Products p JOIN FETCH p.supplier JOIN FETCH p.batches")
    List<Products> findAllWithBatchesAndSupplier();
}
