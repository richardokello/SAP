//package co.ke.spsat.bowip.service;
//
//import co.ke.spsat.bowip.entities.Customers;
//import co.ke.spsat.bowip.entities.Order;
//import co.ke.spsat.bowip.entities.Products;
//import co.ke.spsat.bowip.entities.Regions;
//import co.ke.spsat.bowip.repositories.CustomerRepository;
//import co.ke.spsat.bowip.repositories.OrderRepository;
//import co.ke.spsat.bowip.repositories.ProductsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class AnalyticsService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private ProductsRepository productRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    // Sales Reports
//    public Map<String, Object> getSalesReport(Date startDate, Date endDate) {
//        List<Order> orders = orderRepository.findOrdersByDateRange(startDate, endDate);
//        double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
//        int totalOrders = orders.size();
//        return Map.of("totalSales", totalSales, "totalOrders", totalOrders);
//    }
//
//    // Customer Behavior Analysis
//    public Map<String, Object> getCustomerBehaviorAnalysis() {
//        List<Customers> customers = customerRepository.findAllCustomers();
//        long totalCustomers = customers.size();
//        Map<Regions, Long> customerByRegion = customers.stream()
//                .collect(Collectors.groupingBy(Customers::getRegions, Collectors.counting()));
//        return Map.of("totalCustomers", totalCustomers, "customerByRegion", customerByRegion);
//    }
//
//    // Product Performance
//    public Map<String, Object> getProductPerformance(Long productId) {
//        Products product = productRepository.findProductsByProductId(productId);
//        long totalSales = product.getOrders().size();
//        double totalRevenue = product.getOrders().stream().mapToDouble(Order::getTotalAmount).sum();
//        return Map.of("totalSales", totalSales, "totalRevenue", totalRevenue);
//    }
//
//    // Marketing Campaign Analysis
//    public Map<String, Object> getMarketingCampaignAnalysis(Long campaignId) {
//        // This method would typically call an external marketing tool's API to get data
//        // For the sake of example, let's assume we have the data
//        double totalSpent = 5000; // Example data
//        double totalRevenue = 15000; // Example data
//        int totalConversions = 300; // Example data
//        return Map.of("totalSpent", totalSpent, "totalRevenue", totalRevenue, "totalConversions", totalConversions);
//    }
//
//    // Traffic Analysis
//    public Map<String, Object> getTrafficAnalysis(Date startDate, Date endDate) {
//        // This method would typically call a web analytics tool's API to get data
//        // For the sake of example, let's assume we have the data
//        long totalVisits = 50000; // Example data
//        double averageSessionDuration = 300; // Example data
//        return Map.of("totalVisits", totalVisits, "averageSessionDuration", averageSessionDuration);
//    }
//
//    // Conversion Rates
//    public Map<String, Object> getConversionRates(Date startDate, Date endDate) {
//        List<Order> orders = orderRepository.findOrdersByDateRange(startDate, endDate);
//        long totalVisits = 50000; // Example data, would typically be fetched from a web analytics tool
//        double conversionRate = (double) orders.size() / totalVisits * 100;
//        return Map.of("totalVisits", totalVisits, "conversionRate", conversionRate);
//    }
//}
