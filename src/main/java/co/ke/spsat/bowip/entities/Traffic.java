package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "TRAFFIC")
public class Traffic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PAGE_VISITED")
    private String pageVisited;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "VISIT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime visitDate;

    @Column(name = "PAGE_URL", nullable = false)
    private String pageUrl;

    @Column(name = "VISITOR_IP", nullable = false)
    private String visitorIp;
}
