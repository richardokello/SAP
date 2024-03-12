package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
    @Id
    private Long id;

}
