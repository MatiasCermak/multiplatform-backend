package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.PartnerDTO;
import com.ubp.streamingmultiplatform.repository.AdvertisementPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementPlanService {
    private final AdvertisementPlanRepository advertisementPlanRepository;

    public List<AdvertisementPlanDTO> list() {
        return advertisementPlanRepository.list();
    }

    public AdvertisementPlanDTO create(AdvertisementPlanDTO advertisementPlan) {
        return advertisementPlanRepository.create(advertisementPlan);
    }

    public AdvertisementPlanDTO retrieve(Integer advertisement_plan_id) {
        return advertisementPlanRepository.retrieve(advertisement_plan_id);
    }

    public AdvertisementPlanDTO update(Integer advertisement_plan_id, AdvertisementPlanDTO advertisementPlan) {
        return advertisementPlanRepository.update(advertisement_plan_id, advertisementPlan);
    }

    public AdvertisementPlanDTO delete(Integer advertisement_plan_id) {
        return advertisementPlanRepository.delete(advertisement_plan_id);
    }
}
