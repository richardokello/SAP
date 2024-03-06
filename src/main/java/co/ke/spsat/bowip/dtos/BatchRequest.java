package co.ke.spsat.bowip.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequest {
    private Long batchId;
    private String batchName;
    private String batchNo;
    private Date manufacturingDate;
    private Date expiryDate;

}
