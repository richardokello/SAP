package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "LOCATION")
@Data
@Entity
public class Location {
    @Id
    private Long locationId;
    private String name;
    private String description;

}
