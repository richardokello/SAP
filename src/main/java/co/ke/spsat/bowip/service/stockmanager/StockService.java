package co.ke.spsat.bowip.service.stockmanager;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.dtos.stockDTOs.StockDTO;
import co.ke.spsat.bowip.dtos.stockDTOs.StockTransferRequest;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import co.ke.spsat.bowip.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {


    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    private UsersRepository usersRepository;

    public Stock addStock(StockDTO stockRequest) {
        Products product = productRepository.findById(stockRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Warehouse warehouse = warehouseRepository.findById(stockRequest.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        Batch batch = batchRepository.findById(stockRequest.getBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found"));

        Stock stock = new Stock();


        Optional<Stock> stockOpt = stockRepository.findByProductIdAndWarehouseId(product, warehouse);

        if (stockOpt.isPresent()) {
            stock = stockOpt.get();
            stock.setQuantityOnHand(stock.getQuantityOnHand() + stockRequest.getQuantity());
        }
        stock.setProductId(product);
        stock.setBatchNumber(batch);
        stock.setMinStockLevel(stockRequest.getSafetyStock());
        stock.setLastUpdated(new Date());
        stock.setUnitPrice(stockRequest.getUnitPrice());
        stock.setReorderPoint(stockRequest.getReorderPoint());
        stock.setQuantityOnHand(stockRequest.getQuantityOnHand());
        stock.setProductId(product);
        return stockRepository.save(stock);
    }

    // Remove Stock
    public Stock removeStock(Long productId, Long warehouseId, Long quantity, Long userId, String reason) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
  Users users=usersRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        Stock stock = stockRepository.findByProductIdAndWarehouseId(product, warehouse)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        if (stock.getQuantityOnHand() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        stock.setQuantityOnHand(stock.getQuantityOnHand() - quantity);
        stock.setLastUpdated(new Date());

        // Log the removal transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(stock.getProductId());
        transaction.setWarehouse(stock.getWarehouseId());
        transaction.setQuantity(-quantity); // Negative quantity indicates removal
        transaction.setUserId(users);
        transaction.setReason(reason);
        transaction.setTransactionDate(new Date());
        stock.getTransactions().add(transaction);

        return stockRepository.save(stock);
    }



    // Get Stock Level
    public Long getStockLevel(Long productId, Long warehouseId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));

        return stockRepository.findByProductIdAndWarehouseId(product, warehouse)
                .map(Stock::getQuantityOnHand)
                .orElse(0L);
    }

    // Transfer Stock
    public Stock transferStock(StockTransferRequest stockRequest, Long quantity, Long userId) {
        removeStock(stockRequest.getToWarehouseId().getWarehouseID(), stockRequest.getFromWarehouseId().getWarehouseID(), quantity, userId, "Transfer to warehouse " + stockRequest.getToWarehouseId().getWarehouseName());
        StockDTO stockDTO=new StockDTO();
        stockDTO.setProductId(stockRequest.getProductId().getProductId());
        stockDTO.setWarehouseId(stockRequest.getToWarehouseId().getWarehouseID());
        stockDTO.setQuantityOnHand(stockRequest.getQuantity());
        stockDTO.setQuantity(quantity);
        return addStock(stockDTO);
    }

    // Get Stock by Product
    public List<Stock> getStockByProduct(Long productId) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return stockRepository.findByProductId(product);
    }

    // Get Stock by Warehouse
    public List<Stock> getStockByWarehouse(Long warehouseId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
        return stockRepository.findByWarehouseId(warehouse.getWarehouseName());
    }


    public Stock getStockById(Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
    }

    public List<Stock> getAllStocks(Integer pageNo, Integer pageSize, String sortBy) {

        List<Stock> stock = stockRepository.findAll();
       return stock;
    }

    public List<Stock> getStocksByProductId(Products productId) {
        return stockRepository.findByProductId(productId);
    }

    public List<Stock> getStocksByWarehouseId(String warehouseId) {
        return stockRepository.findByWarehouseId(warehouseId);
    }



    // Get Stock Level
    public Long getStockLevel(Products productId, Warehouse warehouseId) {
        return stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .map(Stock::getQuantityOnHand)
                .orElse(0L);
    }

    // Transfer Stock
//    public Stock transferStock(Long fromWarehouseId, Long toWarehouseId, Long productId, int quantity) {
//        Stock fromStock = removeStock(productId, fromWarehouseId, quantity);
//        return addStock(productId, toWarehouseId, quantity);
//    }

    // Receive Stock

    public Stock receiveStock(StockDTO stockRequest) {

        return addStock(stockRequest);
    }

    // Check Stock Availability
    public boolean checkStockAvailability(Products productId, Warehouse warehouseId, int quantity) {
        return getStockLevel(productId, warehouseId) >= quantity;
    }

    // Get Stock by Product
    public List<Stock> getStockByProduct(Products productId) {
        return stockRepository.findByProductId(productId);
    }



    // Get Stock by Warehouse
    public List<Stock> getStockByWarehouse(Warehouse warehouseId) {
        return stockRepository.findByWarehouseId(warehouseId);
    }


    // Update Stock
    public Stock updateStock(Long stockId, Long quantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stock.setQuantityOnHand(quantity);
        stock.setLastUpdated(new Date());
        return stockRepository.save(stock);
    }
}





















