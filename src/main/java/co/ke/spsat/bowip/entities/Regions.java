package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
@Table(name = "Regions")
public class Regions {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "REGION_SEQ")
    @SequenceGenerator(name = "REGION_SEQ", sequenceName = "\"Region_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "REGION_ID")
    @NonNull
    private Long Id;
    @Column(name = "REGION_NAME")
    @NonNull
    private String regionName;
    @Column(name = "DESCRIPTION")
    @NonNull
    private String description;
     @ManyToOne
    private Location location;

    public Regions() {

    }
}
