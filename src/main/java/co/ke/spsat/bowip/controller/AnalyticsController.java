package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.service.AnalyticsService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
@RestController
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/sales-report")
    public ResponseEntity<Map<String, Object>> getSalesReport(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        Map<String, Object> report = analyticsService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/customer-behavior")
    public ResponseEntity<Map<String, Object>> getCustomerBehaviorAnalysis() {
        Map<String, Object> analysis = analyticsService.getCustomerBehaviorAnalysis();
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/product-performance/{productId}")
    public ResponseEntity<Map<String, Object>> getProductPerformance(@PathVariable Long productId) {
        Map<String, Object> performance = analyticsService.getProductPerformance(productId);
        return ResponseEntity.ok(performance);
    }

    @GetMapping("/marketing-campaign/{campaignId}")
    public ResponseEntity<Map<String, Object>> getMarketingCampaignAnalysis(@PathVariable Long campaignId) {
        Map<String, Object> analysis = analyticsService.getMarketingCampaignAnalysis(campaignId);
        return ResponseEntity.ok(analysis);
    }

}
