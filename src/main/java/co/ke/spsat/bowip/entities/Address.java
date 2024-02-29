package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "ADDRESS")
@NoArgsConstructor(force = true)
public class Address {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ADDRESS_SEQ")
    @SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "\"Address_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "ADDRESS_ID")
    @NonNull
    private Long id;
    @Column(name = "STREET")
    @NonNull
    private String street;

    @Column(name = "STATE")
    @NonNull
    private String state;



    @Column(name = "ADDRESS_LINE1", nullable = false)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2")
    private String addressLine2;

    @Column(name = "CITY", nullable = false)
    private String city;


    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Column(name = "COUNTYR")

    @Pattern(regexp = "[A-Z]{2}", message = "2-letter ISO country code required")
    private String country;
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "number only")
    private String phone;

}
