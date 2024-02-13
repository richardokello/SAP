package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Regions")
public class Regions {
    @jakarta.persistence.Id
    private Long Id;
    private String regionName;
    private String description;
     @ManyToOne
    private Location location;
}
