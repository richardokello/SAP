package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

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
    @Column(name = "CITY")
    @NonNull
    private String city;
    @Column(name = "STATE")
    @NonNull
    private String state;
    @Column(name = "POSTAL_CODE")
    @NonNull
    private String postalCode;
    @Column(name = "COUNTYR")
    @NonNull
    private String country;

}
