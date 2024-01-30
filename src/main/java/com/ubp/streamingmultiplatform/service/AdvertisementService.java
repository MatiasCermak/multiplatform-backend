package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.model.AdvertisementDTO;
import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.InterestDTO;
import com.ubp.streamingmultiplatform.repository.AdvertisementPlanRepository;
import com.ubp.streamingmultiplatform.repository.AdvertisementRepository;
import com.ubp.streamingmultiplatform.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final InterestRepository interestRepository;

    public List<AdvertisementDTO> list() {
        List<AdvertisementDTO> advertisements =  advertisementRepository.list();
        List<AdvertisementDTO> advertisementsWithInterests = advertisements.stream().map( advertisementDTO -> {
            List<InterestDTO> interests = interestRepository.retrieveAdvertisementInterests(advertisementDTO.getAdvertisement_id());
            advertisementDTO.setInterests(interests);
            return advertisementDTO;
        }).toList();
        return advertisementsWithInterests;
    }

    public AdvertisementDTO create(AdvertisementDTO advertisement) {
        AdvertisementDTO advertisementDTO = advertisementRepository.create(advertisement);
        advertisement.getInterests().forEach(interestDTO -> interestRepository.createAdvertisementInterest(interestDTO.getInterest_id(), advertisementDTO.getAdvertisement_id()));
        return retrieve(advertisementDTO.getAdvertisement_id());
    }

    public AdvertisementDTO retrieve(Integer advertisement_id) {
        AdvertisementDTO advertisementDTO = advertisementRepository.retrieve(advertisement_id);
        List<InterestDTO> interestDTOs = interestRepository.retrieveAdvertisementInterests(advertisement_id);
        advertisementDTO.setInterests(interestDTOs);
        return advertisementDTO;
    }

    public AdvertisementDTO update(Integer advertisement_id, AdvertisementDTO advertisement) {
        List<InterestDTO> interestDTOs = interestRepository.retrieveAdvertisementInterests(advertisement_id);
        interestDTOs.forEach( interestDTO -> interestRepository.deleteAdvertisementInterests(advertisement_id, interestDTO.getInterest_id()));
        advertisement.getInterests().forEach( interestDTO -> interestRepository.createAdvertisementInterest(interestDTO.getInterest_id(), advertisement_id));
        return advertisementRepository.update(advertisement_id, advertisement);
    }

    public AdvertisementDTO delete(Integer advertisement_id) {
        List<InterestDTO> interestDTOs = interestRepository.retrieveAdvertisementInterests(advertisement_id);
        interestDTOs.forEach( interestDTO -> interestRepository.deleteAdvertisementInterests(advertisement_id, interestDTO.getInterest_id()));
        return advertisementRepository.delete(advertisement_id);
    }

    public List<AdvertisementDTO> serve(Integer userId, String pageName) {
        return advertisementRepository.serve(userId, pageName);
    }
}
