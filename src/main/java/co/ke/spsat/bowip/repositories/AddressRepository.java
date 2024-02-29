package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
