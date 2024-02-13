package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Table(name = "LOCATION")
@Data
@Entity

public class Location {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "LOCATION_SEQ")
    @SequenceGenerator(name = "LOCATION_SEQ", sequenceName = "\"Location_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "LOCATION_ID")
    @NonNull
    private Long locationId;
    @Column(name = "NAME")
    @NonNull
    private String name;
    @Column(name = "DESCRIPTION")
    @NonNull
    private String description;


    public Location() {

    }
}
