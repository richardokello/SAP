package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TrafficRepository extends JpaRepository<Traffic, Long> {
    Long countByVisitDateBetween(LocalDateTime visitDate, LocalDateTime visitDate2);
    List<Traffic> findByVisitDateBetween(LocalDateTime LocalDateTime, LocalDateTime endDate);
}
