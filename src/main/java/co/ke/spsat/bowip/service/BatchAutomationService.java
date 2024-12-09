package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.repositories.BatchRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BatchAutomationService {
    @Autowired
    private BatchRepository batchRepository;
@Autowired
    private NotificationService notificationService;

    public void automateReorder(int reorderThreshold) {
        List<Batch> lowStockBatches = batchRepository.findLowStockBatches(reorderThreshold);
        lowStockBatches.forEach(batch -> {
            System.out.println("Automating reorder for batch: " + batch.getBatchNo());
            // Trigger reorder logic here, e.g., generate purchase order, send notification
        });
    }

    // Method to check and handle quality control issues for batches
    public void automateQualityControl() {
        // Find batches that are nearing expiration, e.g., within 30 days
        LocalDate expirationThreshold = LocalDate.now().plusDays(30);
        List<Batch> expiringBatches = batchRepository.findExpiringBatches(expirationThreshold);
        if (!expiringBatches.isEmpty()) {
            expiringBatches.forEach(batch -> {
                System.out.println("Batch nearing expiration: " + batch.getBatchNo());
                notificationService.sendNotification("pass email here");
                // Additional logic for quality control if needed
            });
        } else {
            System.out.println("No batches near expiration today.");

        }
    }

}
