package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.service.BatchMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batches/metrics")
public class BatchMetricsController {
    @Autowired
    private BatchMetricsService batchMetricsService;

    // Endpoint to calculate the inventory turnover rate for a specific batch
    @GetMapping("/inventory-turnover")
    public ResponseEntity<Double> calculateInventoryTurnover(Long batchId) {
        double turnoverRate = batchMetricsService.calculateInventoryTurnover(batchId);
        return ResponseEntity.ok(turnoverRate);
    }

    // Endpoint to get the average time in inventory for all batches
    @GetMapping("/average-time-in-inventory")
    public ResponseEntity<Double> getAverageTimeInInventory() {
        double averageTimeInInventory = batchMetricsService.calculateAverageTimeInInventory();
        return ResponseEntity.ok(averageTimeInInventory);
    }
}
