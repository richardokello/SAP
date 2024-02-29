package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Regions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Regions, Long> {
}
