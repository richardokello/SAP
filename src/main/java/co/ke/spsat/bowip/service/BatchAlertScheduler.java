package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchAlertScheduler {
    @Autowired
    private BatchService batchService;

    @Scheduled(cron = "0 0 12 * * ?") // Run every day at noon
    public void checkBatchExpirations() {
        List<Batch> expiredBatches = batchService.findExpiredBatches();
        expiredBatches.forEach(batch -> System.out.println("ALERT: Expired batch found - " + batch.getBatchNo()));
    }

    @Scheduled(cron = "0 0 9 * * ?") // Run every day at 9 am
    public void checkLowStockBatches() {
        List<Batch> lowStockBatches = batchService.findLowStockBatches(50); // Threshold of 50
        lowStockBatches.forEach(batch -> System.out.println("ALERT: Low stock for batch - " + batch.getBatchNo()));
    }
    @Autowired
    private BatchAutomationService automationService;

    // Run daily to check for quality issues
    @Scheduled(cron = "0 0 6 * * ?")
    public void monitorQualityAndExpiration() {
        System.out.println("Running daily quality and expiration check...");
        automationService.automateQualityControl();
    }

}
