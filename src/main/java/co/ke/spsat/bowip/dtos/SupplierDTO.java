package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Address;
import lombok.Data;

@Data
public class SupplierDTO {

        private Long supplierId;

        private String supplierName;

        private String contactPhone;

        private String contactEmail;

        private Address address;

//    private List<Contract> contracts;

        private String status="Active";

        private Boolean isDeleted=Boolean.FALSE;
    }


