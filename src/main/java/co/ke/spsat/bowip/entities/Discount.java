package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Discount {
    @Id
    private Long discountId;
    private String name;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

}
