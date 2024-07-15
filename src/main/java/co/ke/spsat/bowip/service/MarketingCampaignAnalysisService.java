package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.MarketingCampaign;
import co.ke.spsat.bowip.repositories.MarketingCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketingCampaignAnalysisService {
    @Autowired
    private MarketingCampaignRepository campaignRepository;

    public List<MarketingCampaign> getSuccessfulCampaigns() {
        return campaignRepository.findAll().stream()
                .filter(campaign -> campaign.getConversionRate() > 10)
                .collect(Collectors.toList());
    }

    public List<MarketingCampaign> getFailedCampaigns() {
        return campaignRepository.findAll().stream()
                .filter(campaign -> campaign.getConversionRate() < 2)
                .collect(Collectors.toList());
    }
}
