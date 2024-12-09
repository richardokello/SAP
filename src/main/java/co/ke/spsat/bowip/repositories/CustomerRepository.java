package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Regions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

       @Query("SELECT c FROM Customers c WHERE c.routes.routeId = :routeId")
       List<Customers> findCustomersByRouteId(@Param("routeId") Long routeId, Pageable pageable);
       List<Customers> findCustomersByCustomerId(Long customerId);
       List<Customers>findCustomersByRegionsId(Long regionId);
       Optional<Customers> getCustomersByCustomerCode(String customerCode);


//List<Customers> findAllCustomers();
       //List<Customers>findAllBy
}
