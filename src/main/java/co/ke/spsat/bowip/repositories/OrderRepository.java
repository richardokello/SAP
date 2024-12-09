package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.OrderStatus;
import co.ke.spsat.bowip.entities.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public interface
      OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customers customer);
   List<Order>  findByCustomerCustomerId(Long customerId);
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate ")
    List<Order> findOrdersByDateRange(Date startDate, Date endDate);
   List<Order> findAllByProductsProductId(Long  products);

    Long countByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);

    Long countByCustomer(Customers customer);
    Long countByProducts(Products products);

    Collection<Order> findByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);
    List<Order> findByOrderDateBetweenAndOrderStatus(LocalDateTime startDate, LocalDateTime endDate, OrderStatus orderStatus);

  //  Long countByProducts(Products p2);


}
