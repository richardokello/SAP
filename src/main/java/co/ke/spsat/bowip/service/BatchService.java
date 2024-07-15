package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.BatchRequest;
import co.ke.spsat.bowip.entities.Batch;

import co.ke.spsat.bowip.repositories.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class BatchService {
    @Autowired
    private final BatchRepository batchRepo;
    public List<BatchRequest> getBatchByProduct(String batchId ){
       // Batch batch = new Batch();
        List<BatchRequest> requestData=new ArrayList<>();
       Optional<Batch>batch1=batchRepo.findBatchesByProductProductCode(batchId) ;
       if(batch1.isPresent()){
           requestData=batch1.stream().map(this::mapToCustomerRequestData).toList();
       }
       return requestData;
    }
    public  List<BatchRequest>getAllBatch(){
       List<Batch> batchList=batchRepo.findAll();
       return batchList.stream().map(this::mapToCustomerRequestData).toList();
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
        batch.setProductCode(batchRequest.getProductCode());
        batch.setBatchNo(batchRequest.getBatchNo());
        batch.setManufacturingDate(batchRequest.getManufacturingDate());
        batch.setProductCode(batchRequest.getProductCode());
        batch.setExpirationDate(batchRequest.getExpiryDate());
        batchRepo.save(batch);
        return batchRequest;
    }

    private BatchRequest mapToCustomerRequestData(Batch batch){
        return BatchRequest.builder()
                .batchNo(batch.getBatchNo())
                .expiryDate(batch.getExpirationDate())
                .manufacturingDate(batch.getManufacturingDate())
                .productCode(batch.getProductCode())
                .build();

    }
}
