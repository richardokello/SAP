package co.ke.spsat.bowip.service.stockmanager;

import co.ke.spsat.bowip.dtos.stockDTOs.StockDTO;
import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.entities.Stock;
import co.ke.spsat.bowip.entities.Warehouse;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class StockMapper {
    public Stock mapToStock(StockDTO stockRequest, Products product, Warehouse warehouse, Batch batch) {
        Stock stock = new Stock();
        stock.setProductId(product);
        stock.setWarehouseId(warehouse);
        stock.setBatchNumber(batch);
        stock.setUnitPrice(stockRequest.getUnitPrice());
        stock.setReorderPoint(stockRequest.getReorderPoint());
        stock.setSafetyStock(stockRequest.getSafetyStock());
        stock.setLastUpdated(new Date());
        return stock;
    }

    public void updateStockFromRequest(Stock stock, StockDTO stockRequest) {
        stock.setQuantityOnHand(stockRequest.getQuantityOnHand());
        stock.setUnitPrice(stockRequest.getUnitPrice());
        stock.setReorderPoint(stockRequest.getReorderPoint());
        stock.setSafetyStock(stockRequest.getSafetyStock());
        stock.setLastUpdated(new Date());
    }
}
