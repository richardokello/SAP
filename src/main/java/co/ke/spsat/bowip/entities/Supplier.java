package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "SUPPLIERS")
@Entity
@Data
@NoArgsConstructor
public class Supplier {
    @Id
    @Column(name = "SUPPLIER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUPPLIERS_SEQ")
    @SequenceGenerator(sequenceName = "supplier_seq", allocationSize = 1, name = "SUPPLIERS_SEQ")
    private Long supplierId;
    @Column(name = "NAME")
    private String supplierName;
    @Column(name = "PHONE")
    private String contactPhone;
    @Column(name = "EMAIL")
    private String contactEmail;
    private Address address;
    private List<Products> suppliedProducts;
    private List<Contract> contracts;

    @Column(name = "STATUS")
    private String status="Active";
    @Column(name = "ISDELETED")
    private Boolean isDeleted=Boolean.FALSE;
}
