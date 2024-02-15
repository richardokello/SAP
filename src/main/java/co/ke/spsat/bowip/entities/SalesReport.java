package co.ke.spsat.bowip.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "SALES_REPORT")
public class SalesReport {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SALES_REPORT_SEQ")
    @SequenceGenerator(name = "SALES_REPORT_SEQ", sequenceName = "\"Sales_report_Seq\"",allocationSize = 1)
    @Id
    @Column(name = "SALES_REPORT_ID")
    @NonNull
    private Long reportId;
    @Column(name = "SALES_REPORT_DATE")
    @NonNull
    private LocalDate reportDate;
    @Column(name = "TOTALS")
    @NonNull
    private double totalSales;

    public SalesReport() {

    }
}
