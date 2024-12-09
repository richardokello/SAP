package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.stockDTOs.StockDTO;
import co.ke.spsat.bowip.dtos.stockDTOs.StockRemovalRequest;
import co.ke.spsat.bowip.dtos.stockDTOs.StockTransferRequest;
import co.ke.spsat.bowip.entities.Stock;
import co.ke.spsat.bowip.service.stockmanager.StockService;
import co.ke.spsat.bowip.service.stockmanager.StockTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;
    private final StockTransferService stockTransferService;

    @Autowired
    public StockController(StockService stockService, StockTransferService stockTransferService) {
        this.stockService = stockService;
        this.stockTransferService = stockTransferService;
    }

//    @GetMapping("/product/{productId}/warehouse/{warehouseId}")
//    public List<Stock> getStockByProductAndWarehouse(@PathVariable Products productId, @PathVariable Warehouse warehouseId) {
//        return stockService.transferStock(productId, warehouseId);
//    }
    // Endpoint to transfer stock
    @PostMapping("/transfer")
    public ResponseEntity<Stock> transferStock(@RequestBody StockTransferRequest request) {
        Stock stock = stockService.transferStock(request,  request.getQuantity(), request.getRequestedBy());
        return ResponseEntity.ok(stock);
    }

    // Endpoint to add stock
    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestBody StockDTO request) {
        Stock stock = stockService.addStock(request);
        return ResponseEntity.ok(stock);
    }

    // Endpoint to receive stock
    @PostMapping("/receive")
    public ResponseEntity<Stock> receiveStock(@RequestBody StockDTO request) {
        Stock stock = stockService.receiveStock(request);
        return ResponseEntity.ok(stock);
    }



    // Endpoint to remove stock
    @PostMapping("/remove")
    public ResponseEntity<Stock> removeStock(@RequestBody StockRemovalRequest request) {
        Stock stock = stockService.removeStock(request.getProductId(), request.getWarehouseId(), request.getQuantity(), request.getUserId(), request.getReason());
        return ResponseEntity.ok(stock);
    }

    // Endpoint to get stock level
    @GetMapping("/level")
    public ResponseEntity<Long> getStockLevel(@RequestParam Long productId, @RequestParam Long warehouseId) {
        Long stockLevel = stockService.getStockLevel(productId, warehouseId);
        return ResponseEntity.ok(stockLevel);
    }
    @GetMapping("/stock")
    public ResponseEntity<List<Stock>> getAllStocks(@RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(defaultValue = "id") String sortBy) {
        List<Stock> stockList = stockService.getAllStocks(pageNo, pageSize,sortBy);
        return ResponseEntity.ok(stockList);
    }
    // Endpoint to transfer stock
//    @PostMapping("/transfer")
//    public ResponseEntity<Stock> transferStock(@RequestBody StockTransferRequest request) {
//        Stock stock = stockService.transferStock(request.getFromWarehouseId(), request.getToWarehouseId(), request.getProductId(), request.getQuantity());
//        return ResponseEntity.ok(stock);
//    }

    // Endpoint to get all stock by product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Stock>> getStockByProduct(@PathVariable Long productId) {
        List<Stock> stockList = stockService.getStockByProduct(productId);
        return ResponseEntity.ok(stockList);
    }

    // Endpoint to get all stock by warehouse
    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Stock>> getStockByWarehouse(@PathVariable Long warehouseId) {
        List<Stock> stockList = stockService.getStockByWarehouse(warehouseId);
        return ResponseEntity.ok(stockList);
    }
}
