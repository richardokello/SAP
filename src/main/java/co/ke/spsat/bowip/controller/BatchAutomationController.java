package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.service.BatchAutomationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batches/automation")
public class BatchAutomationController {
    private final BatchAutomationService batchAutomationService;
    // Constructor for dependency injection
    public BatchAutomationController(BatchAutomationService batchAutomationService) {
        this.batchAutomationService = batchAutomationService;
    }



    // Endpoint to trigger quality and expiration check
    @GetMapping("/quality-control")
    public ResponseEntity<String> triggerQualityControlCheck() {
        batchAutomationService.automateQualityControl();
        return ResponseEntity.ok("Quality and expiration check triggered.");
    }

}
