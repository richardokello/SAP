package co.ke.spsat.bowip.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "")
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "DISCOUNT_ID", updatable = false, nullable = false)
    private Long discountId;
    @Column(name = "NAME", nullable = false)
    @Nonnull
    private String code;

    @Column(name = "DISCOUNT_PERCENTAGE", nullable = false)
    @Nonnull
    private BigDecimal discountPercentage;
    @Nonnull
    private LocalDate validFrom;

    @Nonnull
    private LocalDate validTo;
    private Boolean isActive;

}
