package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.stockDTOs.StockTransferRequest;
import co.ke.spsat.bowip.entities.InventoryAlert;
import co.ke.spsat.bowip.entities.Stock;
import co.ke.spsat.bowip.entities.Warehouse;
import co.ke.spsat.bowip.service.stockmanager.StockTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-transfer")
public class StockTransferController {
    @Autowired
    private StockTransferService stockTransferService;
    @PostMapping("/request")
    public ResponseEntity<StockTransferRequest> createTransferRequest(
            @RequestBody StockTransferRequest request,
            @RequestParam Integer requesterId)
    {
        StockTransferRequest createdRequest = stockTransferService.createTransferRequest(request, requesterId);
        return ResponseEntity.ok(createdRequest);
    }

    @PostMapping("/approve")
    public ResponseEntity<StockTransferRequest> approveTransferRequest(
            @RequestParam Integer requestId,
            @RequestParam Integer approverId) {

        StockTransferRequest approvedRequest = stockTransferService.approveTransferRequest(requestId, approverId);
        return ResponseEntity.ok(approvedRequest);
    }
    @GetMapping("/requests")
    public ResponseEntity<List<StockTransferRequest>> getAllTransferRequests() {
        List<StockTransferRequest> requests = stockTransferService.getAllTransferRequests();
        return ResponseEntity.ok(requests);
    }
    // Stock Level Management
    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStockLevel(@PathVariable Long id, @RequestParam Long quantity) {
        Stock stock = stockTransferService.updateStockLevel(id, quantity);
        return ResponseEntity.ok(stock);
    }
    @GetMapping("/warehouses")
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = stockTransferService.getAllWarehouses();
        return ResponseEntity.ok(warehouses);
    }

    // Reorder Levels
    @PutMapping("/stocks/{id}/reorder")
    public ResponseEntity<Stock> updateReorderLevel(@PathVariable Long id, @RequestParam Long reorderLevel) {
        Stock stock = stockTransferService.updateReorderLevel(id, reorderLevel);
        return ResponseEntity.ok(stock);
    }
    // Inventory Alerts
    @GetMapping("/alerts")
    public ResponseEntity<List<InventoryAlert>> getAllInventoryAlerts() {
        List<InventoryAlert> alerts = stockTransferService.getAllInventoryAlerts();
        return ResponseEntity.ok(alerts);
    }
}
