package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.BatchRequest;
import co.ke.spsat.bowip.entities.Batch;

import co.ke.spsat.bowip.repositories.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class BatchService {
    @Autowired
    private final BatchRepository batchRepo;
    public List<BatchRequest> getBatchByProduct(String batchId ){

        List<BatchRequest> requestData=new ArrayList<>();
       Optional<Batch>batch1=batchRepo.findBatchesByProductsProductCode(batchId) ;
       if(batch1.isPresent()){
           requestData=batch1.stream().map(this::mapToCustomerRequestData).toList();
       }
       return requestData;
    }
    public  List<BatchRequest>getAllBatch(){
       List<Batch> batchList=batchRepo.findAll();
       return batchList.stream().map(this::mapToCustomerRequestData).toList();
    }
    public void checkExpiredBatches() {
        List<Batch> expiredBatches = batchRepo.findBatchesByExpirationDate(new Date() );
        for (Batch batch : expiredBatches) {
            // Take appropriate action, e.g., mark as expired, alert inventory manager
        }
    }
public BatchRequest updateBatchBYProduct(BatchRequest batchRequest){
        Batch batch=new Batch();
        batch=batchRepo.save(batch);
    return getBatchRequest(batchRequest, batch);
}
public BatchRequest updateBatchById(Long id, BatchRequest batchRequest){
        Batch batch=new Batch();
        Optional<Batch> batch1=batchRepo.findById(id);
        if(batch1.isPresent()){
            batch.setBatchId(batchRequest.getBatchId());
        }

    return getBatchRequest(batchRequest, batch);
}

    @NotNull
    private BatchRequest getBatchRequest(BatchRequest batchRequest, Batch batch) {
       // batch.setProductCode(batchRequest.getProductCode());
        batch.setBatchNo(batchRequest.getBatchNo());
        batch.setManufacturingDate(batchRequest.getManufacturingDate());
       // batch.setProductCode(batchRequest.getProductCode());
        batch.setExpirationDate(batchRequest.getExpiryDate());
        batchRepo.save(batch);
        return batchRequest;
    }

    private BatchRequest mapToCustomerRequestData(Batch batch){
        return BatchRequest.builder()
                .batchId(batch.getBatchId())
                .batchNo(batch.getBatchNo())
                .expiryDate(batch.getExpirationDate())
                .manufacturingDate(batch.getManufacturingDate())
               // .productCode(batch.getProductCode())
                .build();
    }

    @Autowired
    private BatchRepository batchRepository;
    // Batch Creation
    public Batch createBatch(String batchNumber, LocalDate productionDate, LocalDate expirationDate, int quantity) {
        Batch batch = new Batch();
        batch.setBatchNo(batchNumber);
        batch.setManufacturingDate(productionDate);
        batch.setExpirationDate(expirationDate);
        batch.setInitialQuantity(quantity);
        batch.setCurrentQuantity(quantity);
        batch.setStatus("available");
        batch.setQualityStatus("ok");
        return batchRepository.save(batch);
    }

    // Stock Monitoring
    public List<Batch> findLowStockBatches(int threshold) {
        return batchRepository.findByCurrentQuantityLessThan(threshold);
    }
    // Quality Control
    public Batch updateQualityStatus(Long batchId, String qualityStatus) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        batch.setQualityStatus(qualityStatus);
        return batchRepository.save(batch);
    }
    // Expiration Management
    public List<Batch> findExpiredBatches() {
        return batchRepository.findByExpirationDateBefore(LocalDate.now());
    }
    // Reorder Automation
    public Batch autoReplenishStock(Long batchId, int replenishQuantity) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        batch.setCurrentQuantity(batch.getCurrentQuantity() + replenishQuantity);
        batch.setStatus("available");
        return batchRepository.save(batch);
    }

    // Order Fulfillment: Reserve items from a batch
    public boolean fulfillOrder(Long batchId, int orderQuantity) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        if (batch.getCurrentQuantity() < orderQuantity) return false;

        batch.setCurrentQuantity(batch.getCurrentQuantity() - orderQuantity);
        batchRepository.save(batch);
        return true;
    }

    // Discount and Promotions based on batch attributes (e.g., nearing expiration)
    public List<Batch> findDiscountEligibleBatches() {
        LocalDate discountDateThreshold = LocalDate.now().plusDays(10);
        return batchRepository.findByExpirationDateBefore(discountDateThreshold);
    }

    // Batch Splitting
    public Batch splitBatch(Long batchId, int quantityToSplit) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        if (batch.getCurrentQuantity() < quantityToSplit) throw new IllegalArgumentException("Insufficient quantity");

        batch.setCurrentQuantity(batch.getCurrentQuantity() - quantityToSplit);
        batchRepository.save(batch);

        Batch newBatch = new Batch();
        newBatch.setBatchNo(batch.getBatchNo() + "-SPLIT");
        newBatch.setManufacturingDate(batch.getManufacturingDate());
        newBatch.setExpirationDate(batch.getExpirationDate());
        newBatch.setInitialQuantity(batch.getInitialQuantity());
        newBatch.setCurrentQuantity(quantityToSplit);
        newBatch.setStatus(batch.getStatus());
        newBatch.setQualityStatus(batch.getQualityStatus());

        return batchRepository.save(newBatch);
    }

    // Batch Transfer and Merging
    public Batch mergeBatches(Long sourceBatchId, Long targetBatchId) {
        Batch sourceBatch = batchRepository.findById(sourceBatchId)
                .orElseThrow(() -> new RuntimeException("Source batch not found"));
        Batch targetBatch = batchRepository.findById(targetBatchId)
                .orElseThrow(() -> new RuntimeException("Target batch not found"));
        targetBatch.setCurrentQuantity(targetBatch.getCurrentQuantity() + sourceBatch.getCurrentQuantity());
        batchRepository.delete(sourceBatch);
        return batchRepository.save(targetBatch);
    }

    // Batch Adjustments for inventory reconciliation
    public Batch adjustBatchQuantity(Long batchId, int adjustment) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));
        batch.setCurrentQuantity(batch.getCurrentQuantity() + adjustment);
        return batchRepository.save(batch);
    }
}
