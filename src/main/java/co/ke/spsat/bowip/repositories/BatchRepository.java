package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> {
}
