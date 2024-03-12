package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {

       @Query("SELECT c FROM Customers c WHERE c.routes.routeId = :routeId")
       List<Customers> findCustomersByRouteId(@Param("routeId") Long routeId);

}
