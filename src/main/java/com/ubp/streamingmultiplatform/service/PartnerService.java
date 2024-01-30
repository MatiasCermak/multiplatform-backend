package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.model.ContentServeDTO;
import com.ubp.streamingmultiplatform.model.PartnerDTO;
import com.ubp.streamingmultiplatform.model.response.connectors.WatchContentResponse;
import com.ubp.streamingmultiplatform.repository.ContentRepository;
import com.ubp.streamingmultiplatform.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final ContentRepository contentRepository;

    public List<PartnerDTO> list() {
        return partnerRepository.list();
    }

    public PartnerDTO create(PartnerDTO partner) {
        return partnerRepository.create(partner);
    }

    public PartnerDTO retrieve(Integer partnerId) {
        return partnerRepository.retrieve(partnerId);
    }

    public PartnerDTO update(Integer partner_id, PartnerDTO partner) {
        return partnerRepository.update(partner_id, partner);
    }

    public PartnerDTO delete(Integer partnerId) {
        return partnerRepository.delete(partnerId);
    }

    public WatchContentResponse watch_content(Integer partnerId, Integer contentId, Integer userId) throws Exception {
        PartnerDTO partnerDTO = this.partnerRepository.retrieve(partnerId);
        ContentServeDTO contentServeDTO = contentRepository.retrieve_content(contentId);
        PartnerGeneralConnector partnerGeneralConnector = PartnerGeneralConnector.createPartner(partnerId, partnerDTO.getProtocol());
        WatchContentResponse watchContentResponse = partnerGeneralConnector.retrieveContent("asdasd", contentServeDTO.getEidr_number());
        if(watchContentResponse != null && watchContentResponse.getStatus() == 1) {
            return watchContentResponse;
        }
        throw new RuntimeException();
    }
}
