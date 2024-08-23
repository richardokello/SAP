package co.ke.spsat.bowip.repositories;

import co.ke.spsat.bowip.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
