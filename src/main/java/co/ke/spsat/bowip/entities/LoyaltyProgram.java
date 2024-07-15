package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class LoyaltyProgram {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int pointsPerDollar;

    @Column(nullable = false)
    private double discountPercentagePerPoint;

    @Column(nullable = false)
    private Boolean isActive;
}
