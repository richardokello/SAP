package co.ke.spsat.bowip.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Table(name = "SUPPLIERS")
@Entity
@Data
@NoArgsConstructor
public class Supplier {
    @Id
    @Column(name = "SUPPLIER_ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUPPLIERS_SEQ")
//    @SequenceGenerator(sequenceName = "supplier_seq", allocationSize = 1, name = "SUPPLIERS_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long supplierId;
    @Column(name = "NAME")
    private String supplierName;
    @Column(name = "PHONE")
    private String contactPhone;
    @Column(name = "EMAIL")
    private String contactEmail;
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ADDRESS", referencedColumnName = "ADDRESS_ID")
    private Address address;
//    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
//    @JsonIgnore
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "PRODUCT_ID")
//    private List<Products> suppliedProductID;
//    @OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
//    @JsonIgnore
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "CONTACTS" )
//    private List<Contract> contracts;

    @Column(name = "STATUS")
    private String status="Active";
    @Column(name = "ISDELETED")
    private Boolean isDeleted=Boolean.FALSE;
}
