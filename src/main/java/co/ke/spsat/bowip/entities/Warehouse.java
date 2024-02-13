package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
    @Id
    private Long warehouseID;
    private String warehouseName;
    @ManyToOne
    private Location location;
    private Regions regions;
}
