package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Products product;

    private int quantitySold;
    private LocalDateTime salesDate;
    private Double salesAmount;

}
