package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
    Optional<Batch> findById(Long id);

    Optional<Batch>findByProducts(Products Id);
    Optional<Batch>findByBatchNo(String batch);
    List<Batch>findByExpirationDate(LocalDate expiredDate);
    List<Batch> findBatchesByExpirationDate(Date expirationDate);
  //  Optional<Batch>findBatchesByProductProductCodeContains(String productCode);

    @Query("SELECT b FROM Batch b WHERE b.currentQuantity < :threshold")
    List<Batch> findLowStockBatches(int threshold);

    @Query("SELECT b FROM Batch b WHERE b.expirationDate < :expirationThreshold")
    List<Batch> findExpiringBatches(LocalDate expirationThreshold);
    Optional<Batch>findBatchesByProductsProductCode(String productCode);
    List<Batch> findByStatus(String status);
    List<Batch> findByExpirationDateBefore(LocalDate date);
    List<Batch> findByCurrentQuantityLessThan(int quantity);
    List<Batch> findByQualityStatus(String qualityStatus);
    @Query("SELECT b FROM Batch b WHERE b.manufacturingDate < :ageThreshold")
    List<Batch> findAgedBatches(LocalDate ageThreshold);    // Cycle counting discrepancy
    @Query("SELECT b FROM Batch b WHERE b.currentQuantity != :physicalCount")
    List<Batch> findDiscrepancies(int physicalCount);
    @Query("SELECT b, (b.initialQuantity - b.currentQuantity) / b.initialQuantity * 100 AS sellThroughRate FROM Batch b")
    List<Object[]> calculateSellThroughRate();
}
