package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.entities.Stock;
import co.ke.spsat.bowip.entities.StockBatch;
import co.ke.spsat.bowip.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class COGSService {
    @Autowired
    private StockRepository stockRepository;

    /**
     * Calculate COGS using a FIFO approach for the given product and quantity sold.
     *
     * @param productId the product ID
     * @param quantitySold the quantity sold
     * @return the COGS for the quantity sold
     */
    public double calculateCOGS(Long productId, Long quantitySold) {
        // Fetch stock information (batches) for the product
        Stock stock = stockRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        LinkedList<StockBatch> stockBatches = (LinkedList<StockBatch>) stock.getStockBatches();//stock.getStockBatch(); // Assuming you store stock as a list of batches
        long remainingQuantity = quantitySold;
        double totalCOGS = 0.0;

        // FIFO - process each batch in order until we satisfy the required quantity
        while (remainingQuantity > 0 && !stockBatches.isEmpty()) {
            StockBatch currentBatch = stockBatches.getFirst();

            if (currentBatch.getQuantityAvailable() <= remainingQuantity) {
                // Consume entire batch
                totalCOGS += currentBatch.getQuantityAvailable() * currentBatch.getUnitCostPrice();
                remainingQuantity -= currentBatch.getQuantityAvailable();
                stockBatches.removeFirst(); // Remove batch as it's fully consumed
            } else {
                // Partially consume batch
                totalCOGS += remainingQuantity * currentBatch.getUnitCostPrice();
                currentBatch.setQuantityAvailable(currentBatch.getQuantityAvailable() - remainingQuantity);
                remainingQuantity = 0;
            }
        }

        if (remainingQuantity > 0) {
            throw new IllegalArgumentException("Not enough stock to fulfill the order.");
        }

        // Update stock batches in the database
        stock.setStockBatches(stockBatches);
        stockRepository.save(stock);

        return totalCOGS;
    }
}
