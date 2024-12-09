package co.ke.spsat.bowip.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesReports {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String customerName;

}
