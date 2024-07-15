package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Payment;
import co.ke.spsat.bowip.entities.ShippingOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long > {
}
