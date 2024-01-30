package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.AgencyDTO;
import com.ubp.streamingmultiplatform.repository.AdvertisementPlanRepository;
import com.ubp.streamingmultiplatform.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private final AgencyRepository agencyRepository;

    public List<AgencyDTO> list() {
        return agencyRepository.list();
    }

    public AgencyDTO create(AgencyDTO agency) {
        return agencyRepository.create(agency);
    }

    public AgencyDTO retrieve(Integer agency_id) {
        return agencyRepository.retrieve(agency_id);
    }

    public AgencyDTO update(Integer agency_id, AgencyDTO agency) {
        return agencyRepository.update(agency_id, agency);
    }

    public AgencyDTO delete(Integer agency_id) {
        return agencyRepository.delete(agency_id);
    }

}
