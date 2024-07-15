package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.entities.Stock;
import co.ke.spsat.bowip.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
@Query("SELECT s FROM Stock s JOIN s.warehouseId w WHERE w.warehouseName = :warehouseName")
    List<Stock> findByWarehouseId(@Param("warehouseName") String warehouseName);

    List<Stock> findByProductId(Products productId);

    Optional<Stock> findByProductIdAndWarehouseId(Products productId, Warehouse fromWarehouseId);

    List<Stock> findByWarehouseId(Warehouse warehouseId);
 // Stock findByProductId(Long productId);

    //Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long  fromWarehouseId);
}
