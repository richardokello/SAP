package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Promotion {
    @Id
    private Long promotionId;
    private String name;
    private List<Products> applicableProducts;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;

}
