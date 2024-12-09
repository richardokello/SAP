package co.ke.spsat.bowip.controller;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.service.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marketing")
public class MarketingController {
    @Autowired
    private MarketingService marketingService;

    // Discount Codes and Coupons
    @PostMapping("/create-discount-codes")
    public ResponseEntity<Discount> createDiscountCode(@RequestBody Discount discountCode) {
        Discount createdDiscountCode = marketingService.createDiscountCode(discountCode);
        return ResponseEntity.ok(createdDiscountCode);
    }

    @GetMapping("/discount-codes")
    public ResponseEntity<List<Discount>> getAllDiscountCodes() {
        List<Discount> discountCodes = marketingService.getAllDiscountCodes();
        return ResponseEntity.ok(discountCodes);
    }

    @PutMapping("/discount-codes/{id}")
    public ResponseEntity<Discount> updateDiscountCode(@PathVariable Long id, @RequestBody Discount discountCodeDetails) {
        Discount updatedDiscountCode = marketingService.updateDiscountCode(id, discountCodeDetails);
        return ResponseEntity.ok(updatedDiscountCode);
    }

    // Special Offers
    @PostMapping("/special-offers")
    public ResponseEntity<SpecialOffer> createSpecialOffer(@RequestBody SpecialOffer specialOffer) {
        SpecialOffer createdSpecialOffer = marketingService.createSpecialOffer(specialOffer);
        return ResponseEntity.ok(createdSpecialOffer);
    }

    @GetMapping("/special-offers")
    public ResponseEntity<List<SpecialOffer>> getAllSpecialOffers() {
        List<SpecialOffer> specialOffers = marketingService.getAllSpecialOffers();
        return ResponseEntity.ok(specialOffers);
    }

    @PutMapping("/special-offers/{id}")
    public ResponseEntity<SpecialOffer> updateSpecialOffer(@PathVariable Long id, @RequestBody SpecialOffer specialOfferDetails) {
        SpecialOffer updatedSpecialOffer = marketingService.updateSpecialOffer(id, specialOfferDetails);
        return ResponseEntity.ok(updatedSpecialOffer);
    }

    // Loyalty Programs
    @PostMapping("/loyalty-programs")
    public ResponseEntity<LoyaltyProgram> createLoyaltyProgram(@RequestBody LoyaltyProgram loyaltyProgram) {
        LoyaltyProgram createdLoyaltyProgram = marketingService.createLoyaltyProgram(loyaltyProgram);
        return ResponseEntity.ok(createdLoyaltyProgram);
    }

    @GetMapping("/loyalty-programs")
    public ResponseEntity<List<LoyaltyProgram>> getAllLoyaltyPrograms() {
        List<LoyaltyProgram> loyaltyPrograms = marketingService.getAllLoyaltyPrograms();
        return ResponseEntity.ok(loyaltyPrograms);
    }

    @PutMapping("/loyalty-programs/{id}")
    public ResponseEntity<LoyaltyProgram> updateLoyaltyProgram(@PathVariable Long id, @RequestBody LoyaltyProgram loyaltyProgramDetails) {
        LoyaltyProgram updatedLoyaltyProgram = marketingService.updateLoyaltyProgram(id, loyaltyProgramDetails);
        return ResponseEntity.ok(updatedLoyaltyProgram);
    }

    // Affiliate Marketing
    @PostMapping("/affiliate-programs")
    public ResponseEntity<AffiliateProgram> createAffiliateProgram(@RequestBody AffiliateProgram affiliateProgram) {
        AffiliateProgram createdAffiliateProgram = marketingService.createAffiliateProgram(affiliateProgram);
        return ResponseEntity.ok(createdAffiliateProgram);
    }

    @GetMapping("/affiliate-programs")
    public ResponseEntity<List<AffiliateProgram>> getAllAffiliatePrograms() {
        List<AffiliateProgram> affiliatePrograms = marketingService.getAllAffiliatePrograms();
        return ResponseEntity.ok(affiliatePrograms);
    }

    @PutMapping("/affiliate-programs/{id}")
    public ResponseEntity<AffiliateProgram> updateAffiliateProgram(@PathVariable Long id, @RequestBody AffiliateProgram affiliateProgramDetails) {
        AffiliateProgram updatedAffiliateProgram = marketingService.updateAffiliateProgram(id, affiliateProgramDetails);
        return ResponseEntity.ok(updatedAffiliateProgram);
    }

    // Product Recommendations - Placeholder (requires additional logic and entities)
    @GetMapping("/product-recommendations/{userId}")
    public ResponseEntity<List<Products>> getProductRecommendations(@PathVariable Long userId) {
        List<Products> recommendations = marketingService.getProductRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }
}
