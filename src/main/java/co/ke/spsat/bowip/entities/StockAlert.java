package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.lang.NonNull;

@Data
@Entity
@Table(name = "STOCK_ALERT")
public class StockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ALERT_ID")
    @NonNull
    private Long alert_Id;
    private int threshold;
    private boolean alertActive;
    public StockAlert() {

    }
}
