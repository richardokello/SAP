package co.ke.spsat.bowip.service.stockmanager;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.dtos.stockDTOs.StockTransferRequest;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockTransferService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;
@Autowired
private InventoryAlertRepository inventoryAlertRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private StockTransferRepository stockTransferRequestRepository;

    @Autowired
    private UsersRepository userRepository;


    public StockTransferRequest createTransferRequest(StockTransferRequest request, Integer requesterId) {
        StockTransfer transfer = new StockTransfer();
        Users requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));

        transfer.setRequestedBy(requester);
        transfer.setStatus("PENDING");
        transfer.setRequestDate(new Date());

        stockTransferRequestRepository.save(transfer);
        return request;
    }
    @Transactional
    public StockTransferRequest approveTransferRequest(Integer requestId, Integer approverId) {

        StockTransfer request = stockTransferRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new IllegalArgumentException("Transfer request is not in a pending state");
        }

        Users approver = userRepository.findById(approverId)
                .orElseThrow(() -> new ResourceNotFoundException("Approver not found"));

        request.setApprovedBy(approver);
        request.setStatus("APPROVED");
        request.setApprovalDate(new Date());
        StockTransferRequest request1=toDto(request);
        processStockTransfer(request1);

      stockTransferRequestRepository.save(request);
      return request1;
    }

    public void processStockTransfer(StockTransferRequest transferRequest) {
        Warehouse sourceWarehouse = warehouseRepository.findById(transferRequest.getFromWarehouseId().getWarehouseID())
                .orElseThrow(() -> new ResourceNotFoundException("Source warehouse not found"));

        Warehouse destinationWarehouse = warehouseRepository.findById(transferRequest.getToWarehouseId().getWarehouseID())
                .orElseThrow(() -> new ResourceNotFoundException("Destination warehouse not found"));


    // Retrieve stock at source warehouse
    Stock sourceStock = stockRepository.findByProductIdAndWarehouseId(
                    transferRequest.getProductId(), transferRequest.getFromWarehouseId())
            .orElseThrow(() -> new ResourceNotFoundException("Stock not found in source warehouse"));

    // Check if there is enough stock to transfer
        if (sourceStock.getQuantityOnHand() < transferRequest.getQuantity()) {
        throw new IllegalArgumentException("Insufficient stock in source warehouse");
     }

        // Reduce stock in source warehouse
        sourceStock.setQuantityOnHand(sourceStock.getQuantityOnHand() - transferRequest.getQuantity());
        stockRepository.save(sourceStock);

        // Check if the destination warehouse already has stock of the product
        Stock destinationStock = stockRepository.findByProductIdAndWarehouseId(
                        transferRequest.getProductId(), transferRequest.getToWarehouseId())
                .orElse(new Stock());
        // If not, create a new stock entry

        if (destinationStock.getStockId().isEmpty()) {
            destinationStock.setProductId(sourceStock.getProductId());
            destinationStock.setWarehouseId(destinationWarehouse);
            destinationStock.setQuantityOnHand(0L);
        }
        // Increase stock in destination warehouse
        destinationStock.setQuantityOnHand(destinationStock.getQuantityOnHand() + transferRequest.getQuantity());
        stockRepository.save(destinationStock);

    }
    public List<StockTransferRequest> getAllTransferRequests() {
        List<StockTransfer> transferRequests = stockTransferRequestRepository.findAll();
        return transferRequests.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StockTransferRequest toDto(StockTransfer entity) {
        StockTransferRequest dto = new StockTransferRequest();

        dto.setFromWarehouseId(entity.getFromWarehouseId());
        dto.setToWarehouseId(entity.getToWarehouseId());
        dto.setRequestedBy(entity.getRequestedBy().getUserId());
        dto.setStatus(entity.getStatus());
        dto.setRequestDate(entity.getRequestDate());
        dto.setApprovalDate(entity.getApprovalDate());

        if (entity.getApprovedBy() != null) {
            dto.setApprovedBy(entity.getApprovedBy().getUserId());
        }

        return dto;
    }
    // Warehouse Management
    public Warehouse addWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // Inventory Alerts
    private void createInventoryAlert(Stock stock) {
        InventoryAlert alert = new InventoryAlert();
        alert.setProduct(stock.getProductId());
        alert.setWarehouse(stock.getWarehouseId());
        alert.setMessage("Stock level below reorder level");
        alert.setAlertDate(new Date());

        inventoryAlertRepository.save(alert);
    }
    public Stock updateStockLevel(Long stockId, Long quantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setQuantityOnHand(quantity);
        stock.setLastUpdated(new Date());

        // Check if quantity is below reorder level and create an alert
        if (quantity < stock.getReorderPoint()) {
            createInventoryAlert(stock);
        }

        return stockRepository.save(stock);
    }


    public List<InventoryAlert> getAllInventoryAlerts() {
        return inventoryAlertRepository.findAll();
    }
    // Reorder Levels
    public Stock updateReorderLevel(Long stockId, Long reorderLevel) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setReorderPoint(reorderLevel);
        return stockRepository.save(stock);
    }

}
