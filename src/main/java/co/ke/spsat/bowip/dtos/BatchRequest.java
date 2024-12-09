package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchRequest {
    private Long batchId;
  //  private String batchName;
    private String batchNo;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
   // private Products productId;
   // private String productCode;


}
