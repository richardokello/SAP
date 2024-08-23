package co.ke.spsat.bowip.dtos;

import co.ke.spsat.bowip.entities.Products;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchRequest {
    private Long batchId;
  //  private String batchName;
    private String batchNo;
    private Date manufacturingDate;
    private Date expiryDate;
   // private Products productId;
   // private String productCode;


}
