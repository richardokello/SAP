package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Traffic;
import co.ke.spsat.bowip.repositories.TrafficRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrafficAnalysisService {
    @Autowired
    private TrafficRepository trafficRepository;

    public long getTotalVisits() {
        return trafficRepository.count();
    }

    public List<Traffic> getTrafficByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return trafficRepository.findByVisitDateBetween(startDate, endDate);
    }
}
