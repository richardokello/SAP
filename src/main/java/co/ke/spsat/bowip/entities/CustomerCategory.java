package co.ke.spsat.bowip.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CUSTOMER_CATEGORY")
public class CustomerCategory {
    @Id
    private Long id;
    private String categoryName;

}
