package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Entity
@Table(name = "Regions")
@AllArgsConstructor
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
    @NonNull
    @Column(name = "LOCATION_NAME")
    private String locationName;
    @NonNull
    @Column(name = "LOCATION_ID")
    @Pattern(regexp = "[A-Z]{2}", message = "2-letter ISO region code required")
    private String locationCOde;

    @JoinColumn( name = "CUSTOMERS", referencedColumnName = "REGION_ID")
    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Customers> customers;

    public Regions() {

    }
}
