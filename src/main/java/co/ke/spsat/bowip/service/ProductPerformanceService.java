package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.repositories.OrderRepository;
import co.ke.spsat.bowip.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPerformanceService {
    @Autowired
    private ProductsRepository productRepository;

    @Autowired
    private OrderRepository orderItemRepository;
//
//    public List<Products> getBestSellingProducts() {
//        return productRepository.findAll().stream()
//                .filter(product -> orderItemRepository.countByProduct(product) > 50)
//                .collect(Collectors.toList());
//    }
//
//    public List<Products> getLowSellingProducts() {
//        return productRepository.findAll().stream()
//                .filter(product -> orderItemRepository.countByProduct(product) < 10)
//                .collect(Collectors.toList());
//    }

    public List<Products> getBestSellingProducts() {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> Long.compare(orderItemRepository.countByProducts(p2), orderItemRepository.countByProducts(p1)))
                .collect(Collectors.toList());
    }

    public List<Products> getLowSellingProducts() {
        return productRepository.findAll().stream()
                .sorted((p1, p2) -> Long.compare(orderItemRepository.countByProducts(p1), orderItemRepository.countByProducts(p2)))
                .collect(Collectors.toList());
    }
}
