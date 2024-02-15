package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Promotion {

    @Id
    @GenericGenerator(
            name = "PROMOTION_SEQ",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "PROMOTION_SEQ"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increase_size", value = "1")
            }
    )
    @GeneratedValue(generator = "PROMOTION_SEQ")
    @Column(name = "ID")
    private Long promotionId;
    @Column(name = "PROMOTION_NAME")
    @Nonnull
    private String name;
    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "APPLICABLE_PRODUCT_ID", nullable = false)
    @JoinColumn(name = "APPLICABLE_PRODUCT_ID", referencedColumnName = "PRODUCT_ID", insertable = false,updatable = false)
    private List<Products> applicableProducts;
    @Column(name = "DISCOUNT_PERCENTAGE")
    @Nonnull
    private double discountPercentage;
    @Column(name = "STARTDATE")
    @Nonnull
    private LocalDate startDate;
    @Column(name = "ENDDATE")
    @Nonnull
    private LocalDate endDate;

}
