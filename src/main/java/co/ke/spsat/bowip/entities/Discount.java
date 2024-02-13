package co.ke.spsat.bowip.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "")
public class Discount {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "DISCOUNT_ID", updatable = false, nullable = false)
    private Long discountId;
    @Column(name = "NAME", nullable = false)
    @Nonnull
    private String name;
    @Column(name = "DISCOUNT_PERCENTAGE", nullable = false)
    @Nonnull
    private double discountPercentage;
    @Column(name = "STARTDATE", nullable = false)
    @Nonnull
    private LocalDate startDate;
    @Column(name = "ENDDATE", nullable = false)
    @Nonnull
    private LocalDate endDate;

}
