package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.SupplierDTO;
import co.ke.spsat.bowip.dtos.SupplierResponse;
import co.ke.spsat.bowip.entities.Supplier;
import co.ke.spsat.bowip.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    public SupplierDTO getSupplierById(Long id)
    {
        SupplierDTO supplierResponse=new SupplierDTO();
        Optional<Supplier> supplier=supplierRepository.findById(id);

        if(supplier.isPresent()){
            supplierResponse=supplier.map(this::mapSupplierToDTO).get();
        }
        return supplierResponse;
    }
    public List<SupplierDTO> getAllSupplier(Integer pageNo, Integer pageSize, String sortBy)
    {
        SupplierDTO supplierResponse=new SupplierDTO();
        List<Supplier> supplier=supplierRepository.findAll();

        return supplier.stream().map(this::mapSupplierToDTO).toList();
    }
    public  SupplierDTO editSupplierById(Long id, SupplierDTO supplierResponse){
        Supplier  supplierResponse1=new Supplier();
        Optional<Supplier> supplier=supplierRepository.findById(id);
        if(supplier.isPresent()){
//            sup
            supplierResponse1=supplier.get();
            supplierResponse=mapSupplierToDTO(supplierResponse1);
            supplierRepository.save(supplierResponse1);
        }
        supplierRepository.save(supplierResponse1);
        return supplierResponse;
}


 private void deleteSupplierById(Long id){
        SupplierResponse supplierResponse=new SupplierResponse();
        Optional<Supplier> supplier=supplierRepository.findById(id);
        if(supplier.isPresent()){
            supplierRepository.delete(supplier.get());
        }

 }

    SupplierDTO mapSupplierToDTO(Supplier supplier){
        SupplierDTO supplierDTO=new SupplierDTO();
        supplierDTO.setSupplierName(supplier.getSupplierName());
        supplierDTO.setStatus(supplier.getStatus());
        supplierDTO.setAddress(supplier.getAddress());
        supplierDTO.setContactPhone(supplier.getContactPhone());
        supplierDTO.setContactEmail(supplier.getContactEmail());
        supplierDTO.setIsDeleted(supplier.getIsDeleted());
        return supplierDTO;

    }
}
