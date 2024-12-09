package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.BatchRequest;
import co.ke.spsat.bowip.dtos.ProductRequest;
import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService service;

    @GetMapping("/getAllBatches")
    public ResponseEntity<List<BatchRequest>> getAllBatches() {
        return new ResponseEntity<>(service.getAllBatch(),new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("/getBatchByProduct/{id}")
//    public ResponseEntity<List<BatchRequest>> getBatchByProduct(String id) {
//        return new ResponseEntity<>(service.getBatchByProduct(id),new HttpHeaders(), HttpStatus.OK);
//    }
//    @PutMapping ("/updateBatchesByProduct")
//    public ResponseEntity<BatchRequest> updateBatchesByProduct(BatchRequest batchRequest) {
//        return new ResponseEntity<>(service.updateBatchBYProduct(batchRequest),new HttpHeaders(), HttpStatus.OK);
//    }
//    @PutMapping ("/updateBatchesById")
//    public ResponseEntity<BatchRequest> updateBatchesById( Long id, BatchRequest batchRequest) {
//        return new ResponseEntity<>(service.updateBatchById(id, batchRequest),new HttpHeaders(), HttpStatus.OK);
//    }

    private final BatchService batchService;



    // Get batch by product code
    @GetMapping("/product/{productCode}")
    public ResponseEntity<List<BatchRequest>> getBatchByProduct(@PathVariable String productCode) {
        return ResponseEntity.ok(batchService.getBatchByProduct(productCode));
    }

    // Check for expired batches
    @GetMapping("/expired")
    public ResponseEntity<List<Batch>> getExpiredBatches() {
        return ResponseEntity.ok(batchService.findExpiredBatches());
    }

    // Create a new batch
    @PostMapping("/create")
    public ResponseEntity<Batch> createBatch(@RequestParam String batchNumber,
                                             @RequestParam LocalDate productionDate,
                                             @RequestParam LocalDate expirationDate,
                                             @RequestParam int quantity) {
        Batch batch = batchService.createBatch(batchNumber, productionDate, expirationDate, quantity);
        return ResponseEntity.ok(batch);
    }

    // Update batch by product code
    @PutMapping("/product/update")
    public ResponseEntity<BatchRequest> updateBatchByProduct(@RequestBody BatchRequest batchRequest) {
        return ResponseEntity.ok(batchService.updateBatchBYProduct(batchRequest));
    }

    // Update batch by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<BatchRequest> updateBatchById(@PathVariable Long id, @RequestBody BatchRequest batchRequest) {
        return new ResponseEntity<>(batchService.updateBatchById(id, batchRequest),new HttpHeaders(), HttpStatus.OK);
    }

    // Monitor low-stock batches
    @GetMapping("/low-stock")
    public ResponseEntity<List<Batch>> getLowStockBatches(@RequestParam int threshold) {
        return ResponseEntity.ok(batchService.findLowStockBatches(threshold));
    }

    // Update batch quality status
    @PatchMapping("/{batchId}/quality")
    public ResponseEntity<Batch> updateQualityStatus(@PathVariable Long batchId, @RequestParam String qualityStatus) {
        return ResponseEntity.ok(batchService.updateQualityStatus(batchId, qualityStatus));
    }

    // Replenish stock
    @PatchMapping("/{batchId}/replenish")
    public ResponseEntity<Batch> replenishStock(@PathVariable Long batchId, @RequestParam int quantity) {
        return ResponseEntity.ok(batchService.autoReplenishStock(batchId, quantity));
    }

    // Fulfill order from a batch
    @PostMapping("/{batchId}/fulfill")
    public ResponseEntity<String> fulfillOrder(@PathVariable Long batchId, @RequestParam int quantity) {
        boolean isFulfilled = batchService.fulfillOrder(batchId, quantity);
        return ResponseEntity.ok(isFulfilled ? "Order fulfilled successfully" : "Insufficient stock");
    }

    // Find discount eligible batches
    @GetMapping("/discount-eligible")
    public ResponseEntity<List<Batch>> findDiscountEligibleBatches() {
        return ResponseEntity.ok(batchService.findDiscountEligibleBatches());
    }

    // Split a batch
    @PostMapping("/{batchId}/split")
    public ResponseEntity<Batch> splitBatch(@PathVariable Long batchId, @RequestParam int quantity) {
        return ResponseEntity.ok(batchService.splitBatch(batchId, quantity));
    }

    // Merge two batches
    @PostMapping("/{sourceBatchId}/merge/{targetBatchId}")
    public ResponseEntity<Batch> mergeBatches(@PathVariable Long sourceBatchId, @PathVariable Long targetBatchId) {
        return ResponseEntity.ok(batchService.mergeBatches(sourceBatchId, targetBatchId));
    }

    // Adjust batch quantity
    @PatchMapping("/{batchId}/adjustQuantity")
    public ResponseEntity<Batch> adjustBatchQuantity(@PathVariable Long batchId, @RequestParam int adjustment) {
        return ResponseEntity.ok(batchService.adjustBatchQuantity(batchId, adjustment));
    }
}
