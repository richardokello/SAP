package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import java.util.List;

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
    @JoinColumn( name = "LOCATION", referencedColumnName = "LOCATION_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    @JoinColumn( name = "CUSTOMERS", referencedColumnName = "REGION_ID")
    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Customers> customers;

    public Regions() {

    }
}
