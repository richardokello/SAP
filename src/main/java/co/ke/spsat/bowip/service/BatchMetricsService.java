package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Batch;
import co.ke.spsat.bowip.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class BatchMetricsService {

    @Autowired
    private BatchRepository batchRepository;
    // Calculate average time in inventory for all batches
    public double calculateAverageTimeInInventory() {
        List<Batch> batches = batchRepository.findAll();
        return batches.stream()
                .mapToLong(batch -> java.time.temporal.ChronoUnit.DAYS.between(batch.getManufacturingDate(), LocalDate.now()))
                .average()
                .orElse(0.0);
    }
    // Calculate inventory turnover rate for a specific batch
    public double calculateInventoryTurnover(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        int soldQuantity = batch.getInitialQuantity() - batch.getCurrentQuantity();

        if (batch.getInitialQuantity() == 0) {
            throw new ArithmeticException("Initial quantity is zero; cannot calculate turnover rate.");
        }

        return (double) soldQuantity / batch.getInitialQuantity();
    }
}
