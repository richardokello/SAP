package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Routes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutesRepository extends JpaRepository<Routes, Long> {
    List<Customers> findByRouteId(Long routeId);
}
