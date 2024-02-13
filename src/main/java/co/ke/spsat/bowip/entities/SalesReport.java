package co.ke.spsat.bowip.entities;

import lombok.Data;

import java.time.LocalDate;
@Data
public class SalesReport {
    private Long reportId;
    private LocalDate reportDate;
    private double totalSales;

}
