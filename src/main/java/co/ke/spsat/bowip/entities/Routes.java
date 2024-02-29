package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "ROUTES")
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTE_ID")
    private Long routeId;

    @Column(name = "ROUTE_NAME")
    private String routeName;

    @Column(name = "STARTING_LOCATION")
    private String startingLocation;

    @Column(name = "ENDING_LOCATION")
    private String endingLocation;

    @Column(name = "DISTANCE")
    private BigDecimal distance;
    @JoinColumn( name = "REGIONS")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Regions regions;

    @Column(name = "ESTIMATED_TIME")
    private Time estimatedTime;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerId")
//    @JsonIgnore
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @Column(name = "ASSIGNED_CUSTOMERS")
//    private List<Customers> assignedCustomers;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_DATE", updatable = false)
    private Timestamp createdDate;

    @Column(name = "LAST_UPDATED_DATE")
    private Timestamp lastUpdatedDate;

}
