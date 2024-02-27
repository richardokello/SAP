package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
    @Id
    @Column(name = "WAREHOUSE_ID")
    private Long warehouseID;

    @Nonnull
    @Column(name = "WAREHOUSE_NAME")
    private String warehouseName;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION_ID")
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL, optional = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "REGIONS", referencedColumnName = "REGION_ID")
    private Regions regions;
}
