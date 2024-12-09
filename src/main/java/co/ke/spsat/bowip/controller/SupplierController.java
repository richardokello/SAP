package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.BatchRequest;
import co.ke.spsat.bowip.dtos.SupplierDTO;
import co.ke.spsat.bowip.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SupplierController {
    @Autowired
    private final SupplierService supplierService;
    @GetMapping("/getAllSupplier")
    public ResponseEntity<SupplierDTO> getAllSupplier(Long id) {
        return new ResponseEntity<>(supplierService.getSupplierById(id) ,new HttpHeaders(), HttpStatus.OK);
    }
}

