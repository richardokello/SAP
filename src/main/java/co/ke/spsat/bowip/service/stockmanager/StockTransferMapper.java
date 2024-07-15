package co.ke.spsat.bowip.service.stockmanager;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.dtos.stockDTOs.StockTransferRequest;
import co.ke.spsat.bowip.entities.StockTransfer;
import co.ke.spsat.bowip.repositories.UsersRepository;
import co.ke.spsat.bowip.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//
//@Component
//public class StockTransferMapper {
//
//
//    @Autowired
//    private WarehouseRepository warehouseRepository;
//
//    @Autowired
//    private UsersRepository userRepository;

//    public StockTransfer toEntity(StockTransferRequest dto) {
//        StockTransfer entity = new StockTransfer();
//
//        entity.setFromWarehouse(
//                warehouseRepository.findById(dto.getFromWarehouseId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Source warehouse not found"))
//        );
//
//        entity.setToWarehouse(
//                warehouseRepository.findById(dto.getToWarehouseId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Destination warehouse not found"))
//        );
//
//        entity.setRequestedBy(
//                userRepository.findById(dto.getRequestedBy())
//                        .orElseThrow(() -> new ResourceNotFoundException("Requester not found"))
//        );
//
//        if (dto.getApprovedBy() != null) {
//            entity.setApprovedBy(
//                    userRepository.findById(dto.getApprovedBy())
//                            .orElseThrow(() -> new ResourceNotFoundException("Approver not found"))
//            );
//        }
//
//        entity.setStatus(dto.getStatus());
//        entity.setRequestDate(dto.getRequestDate());
//        entity.setApprovalDate(dto.getApprovalDate());
//        entity.setTransferDate(new Date()); // Assuming the transfer date is set to the current date
//
//        return entity;
//    }

//    public StockTransferRequest toDto(StockTransfer entity) {
//        StockTransferRequest dto = new StockTransferRequest();
//
//        dto.setFromWarehouseId(entity.getFromWarehouse().getId());
//        dto.setToWarehouseId(entity.getToWarehouse().getId());
//        dto.setRequestedBy(entity.getRequestedBy().getUserId());
//        dto.setStatus(entity.getStatus());
//        dto.setRequestDate(entity.getRequestDate());
//        dto.setApprovalDate(entity.getApprovalDate());
//
//        if (entity.getApprovedBy() != null) {
//            dto.setApprovedBy(entity.getApprovedBy().getUserId());
//        }
//
//        return dto;
//    }
//}
