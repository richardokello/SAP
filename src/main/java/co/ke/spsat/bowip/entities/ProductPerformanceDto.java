package co.ke.spsat.bowip.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPerformanceDto {
    private Products product;
    private Long totalSales;

    public ProductPerformanceDto(Products product, Long totalSales) {
        this.product = product;
        this.totalSales = totalSales;
    }
}