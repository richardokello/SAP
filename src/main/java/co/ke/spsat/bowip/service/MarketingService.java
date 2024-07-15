package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.AffiliateProgramRepository;
import co.ke.spsat.bowip.repositories.DiscountCodeRepository;
import co.ke.spsat.bowip.repositories.LoyaltyProgramRepository;
import co.ke.spsat.bowip.repositories.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketingService {

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private AffiliateProgramRepository affiliateProgramRepository;

    // Discount Codes and Coupons
    public DiscountCode createDiscountCode(DiscountCode discountCode) {
        return discountCodeRepository.save(discountCode);
    }

    public List<DiscountCode> getAllDiscountCodes() {
        return discountCodeRepository.findAll();
    }

    public DiscountCode updateDiscountCode(Long id, DiscountCode discountCodeDetails) {
        DiscountCode discountCode = discountCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount code not found"));
        discountCode.setCode(discountCodeDetails.getCode());
        discountCode.setDiscountPercentage(discountCodeDetails.getDiscountPercentage());
        discountCode.setValidFrom(discountCodeDetails.getValidFrom());
        discountCode.setValidTo(discountCodeDetails.getValidTo());
        discountCode.setIsActive(discountCodeDetails.getIsActive());
        return discountCodeRepository.save(discountCode);
    }

    // Special Offers
    public SpecialOffer createSpecialOffer(SpecialOffer specialOffer) {
        return specialOfferRepository.save(specialOffer);
    }

    public List<SpecialOffer> getAllSpecialOffers() {
        return specialOfferRepository.findAll();
    }

    public SpecialOffer updateSpecialOffer(Long id, SpecialOffer specialOfferDetails) {
        SpecialOffer specialOffer = specialOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Special offer not found"));
        specialOffer.setTitle(specialOfferDetails.getTitle());
        specialOffer.setDescription(specialOfferDetails.getDescription());
        specialOffer.setValidFrom(specialOfferDetails.getValidFrom());
        specialOffer.setValidTo(specialOfferDetails.getValidTo());
        specialOffer.setIsActive(specialOfferDetails.getIsActive());
        return specialOfferRepository.save(specialOffer);
    }

    // Loyalty Programs
    public LoyaltyProgram createLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        return loyaltyProgramRepository.save(loyaltyProgram);
    }

    public List<LoyaltyProgram> getAllLoyaltyPrograms() {
        return loyaltyProgramRepository.findAll();
    }

    public LoyaltyProgram updateLoyaltyProgram(Long id, LoyaltyProgram loyaltyProgramDetails) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loyalty program not found"));
        loyaltyProgram.setName(loyaltyProgramDetails.getName());
        loyaltyProgram.setPointsPerDollar(loyaltyProgramDetails.getPointsPerDollar());
        loyaltyProgram.setDiscountPercentagePerPoint(loyaltyProgramDetails.getDiscountPercentagePerPoint());
        loyaltyProgram.setIsActive(loyaltyProgramDetails.getIsActive());
        return loyaltyProgramRepository.save(loyaltyProgram);
    }

    // Affiliate Marketing
    public AffiliateProgram createAffiliateProgram(AffiliateProgram affiliateProgram) {
        return affiliateProgramRepository.save(affiliateProgram);
    }

    public List<AffiliateProgram> getAllAffiliatePrograms() {
        return affiliateProgramRepository.findAll();
    }

    public AffiliateProgram updateAffiliateProgram(Long id, AffiliateProgram affiliateProgramDetails) {
        AffiliateProgram affiliateProgram = affiliateProgramRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affiliate program not found"));
        affiliateProgram.setName(affiliateProgramDetails.getName());
        affiliateProgram.setTrackingUrl(affiliateProgramDetails.getTrackingUrl());
        affiliateProgram.setCommissionPercentage(affiliateProgramDetails.getCommissionPercentage());
        affiliateProgram.setIsActive(affiliateProgramDetails.getIsActive());
        return affiliateProgramRepository.save(affiliateProgram);
    }

    // Product Recommendations - Placeholder (requires additional logic and entities)
    public List<Products> getProductRecommendations(Long userId) {
        // Implementation depends on business logic (e.g., collaborative filtering, content-based filtering, etc.)
        return null;
    }
}
