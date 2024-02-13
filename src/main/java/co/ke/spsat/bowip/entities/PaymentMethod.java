package co.ke.spsat.bowip.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@Table(name = "PAYMENTMETHODS")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO )
    private Long methodsId;
    @Nonnull
    @Column(name = "METHODNAME")
    private String methodName;
    @Nonnull
    @Column(name = "DESCRIPTION")
    private String description;

}
