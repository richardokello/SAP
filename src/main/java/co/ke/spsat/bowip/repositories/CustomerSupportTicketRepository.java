package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.CustomerSupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSupportTicketRepository extends JpaRepository<CustomerSupportTicket, Long> {
}
