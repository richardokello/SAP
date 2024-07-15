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

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService service;
    @GetMapping("/getAllBatches")
    public ResponseEntity<List<BatchRequest>> getAllBatches() {
        return new ResponseEntity<>(service.getAllBatch(),new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/getBatchesProduct/{id}")
    public ResponseEntity<List<BatchRequest>> getBatchesByProduct(String id) {
        return new ResponseEntity<>(service.getBatchByProduct(id),new HttpHeaders(), HttpStatus.OK);
    }
    @PutMapping ("/updateBatchesProduct")
    public ResponseEntity<BatchRequest> updateBatchesByProduct(BatchRequest batchRequest) {
        return new ResponseEntity<>(service.updateBatchBYProduct(batchRequest),new HttpHeaders(), HttpStatus.OK);
    }
    @PutMapping ("/updateBatchesById")
    public ResponseEntity<BatchRequest> updateBatchesById( Long id, BatchRequest batchRequest) {
        return new ResponseEntity<>(service.updateBatchById(id, batchRequest),new HttpHeaders(), HttpStatus.OK);
    }

}
