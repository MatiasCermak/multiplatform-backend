package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.connector.AgencyConnectorFactory;
import com.ubp.streamingmultiplatform.connector.AgencyGeneralConnector;
import com.ubp.streamingmultiplatform.connector.PartnerConnectorFactory;
import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.AdvertisementResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.AuthResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.BaseResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.ContentResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.ContentsDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerBalanceDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;
import com.ubp.streamingmultiplatform.model.*;
import com.ubp.streamingmultiplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class ScheduledTasksService {
    @Autowired
    private final PartnerService partnerService;

    @Autowired
    private final PartnerRepository partnerRepository;

    @Autowired
    private final ContentRepository contentRepository;

    @Autowired
    private final AgencyRepository agencyRepository;

    @Autowired
    private final AdvertisementRepository advertisementRepository;

    @Autowired
    private final AdvertisementPlanRepository advertisementPlanRepository;

    //@Scheduled(cron = "* * * * * *")
    public void obtainProgramming() {
        List<PartnerDTO> partners = partnerService.list();

        contentRepository.dumpContent();

        partners.forEach(partner -> {
            if (!partner.getActive()) {
                return;
            }
            try {
                PartnerGeneralConnector partnerConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partner.getName(), partner.getProtocol(), partner.getUrl_service());
                AuthResponseBean responseBean = partnerConnector.obtainToken(partner.getSecret_token());
                if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                    return;
                }
                ContentResponseBean receivedContents = partnerConnector.listAllContents(responseBean.getAuth_token());

                List<ContentsDTO> programming = receivedContents.getProgramming();
                programming.forEach((content) -> {
                    ContentServeDTO dbContent = null;
                    try {
                        dbContent = contentRepository.retrieveContentByEidr(content.getEidr_number());
                    } catch (Exception e) {
                        dbContent = contentRepository.createContent(content);
                    }

                    ContentPartnerDTO contentPartnerDTO = contentRepository.linkContentToPartner(dbContent.getContent_id(), partner.getPartner_id(), content.getRecently_added(), content.getPromoted());
                    if (!Objects.equals(contentPartnerDTO.getPartner_id(), partner.getPartner_id()) || !Objects.equals(contentPartnerDTO.getContent_id(), dbContent.getContent_id())) {
                        Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //@Scheduled(fixedRate = 3000)
    public void obtainAdvertisements() {
        List<AgencyDTO> agencies = agencyRepository.list();

        agencies.forEach(agency -> {
            if (!agency.getActive()) {
                return;
            }
            try {
                List<AdvertisementDTO> advertisements = advertisementRepository.obtainInitializedAdvertisementsById(agency.getAgency_id());
                AgencyGeneralConnector agencyConnector = AgencyConnectorFactory.getInstance().buildAgencyConnector(agency.getName(), agency.getProtocol(), agency.getUrl_service());
                advertisements.forEach( (advertisement) -> {
                    AuthResponseBean responseBean = agencyConnector.obtainToken(agency.getSecret_token());
                    if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                        return;
                    }

                    AdvertisementResponseBean advertisementResponseBean = agencyConnector.getAdvertisement(advertisement.getFile_name(), responseBean.getAuth_token());
                    AdvertisementDTO activatedAdvertisement = advertisementRepository.activateAdvertisement(advertisement.getAdvertisement_id(), advertisementResponseBean.getRedirect_url(), advertisementResponseBean.getImage_url());

                    if(!Objects.equals(activatedAdvertisement.getAdvertisement_id(), advertisement.getAdvertisement_id())) {
                        Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //@Scheduled(fixedRate = 3000)
    public void sendClicksAgencies() {
        List<AgencyDTO> agencies = agencyRepository.list();
        Date currentDate = new Date();
        agencies.forEach(agency -> {
            if(!agency.getActive()) {
                return;
            }

            try {
                List<AdvertisementClickDTO> clickList = advertisementRepository.obtainWeeklyClicksByAgency(agency.getAgency_id(), getStartWeek(Date.from(Instant.parse("2024-02-26T10:15:30.00Z"))), getEndWeek(Date.from(Instant.parse("2024-02-26T10:15:30.00Z"))));
                if(clickList.isEmpty()) {
                    Logger.getAnonymousLogger().log(Level.INFO, "No new clicks found for agency, skipping...");
                    return;
                }
                AgencyGeneralConnector agencyConnector = AgencyConnectorFactory.getInstance().buildAgencyConnector(agency.getName(), agency.getProtocol(), agency.getUrl_service());
                AuthResponseBean responseBean = agencyConnector.obtainToken(agency.getSecret_token());
                if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                    return;
                }

                BaseResponseBean respBean = agencyConnector.receiveStatistics(responseBean.getAuth_token(), clickList);
                if(respBean.getStatus() != 1) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    //@Scheduled(fixedRate = 3000)
    public void sendClicksPartners() {
        List<PartnerDTO> partners = partnerService.list();
        Date currentDate = new Date();
        partners.forEach(partner -> {
            if(!partner.getActive()) {
                return;
            }

            try {
                List<PartnerClicksDTO> clickList = contentRepository.obtainWeeklyClicksByPartner(partner.getPartner_id(), getStartWeek(Date.from(Instant.parse("2024-02-26T10:15:30.00Z"))), getEndWeek(Date.from(Instant.parse("2024-02-26T10:15:30.00Z"))));
                if(clickList.isEmpty()) {
                    Logger.getAnonymousLogger().log(Level.INFO, "No new clicks found for agency, skipping...");
                    return;
                }
                PartnerGeneralConnector partnerConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partner.getName(), partner.getProtocol(), partner.getUrl_service());
                AuthResponseBean responseBean = partnerConnector.obtainToken(partner.getSecret_token());
                if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                    return;
                }

                BaseResponseBean respBean = partnerConnector.receiveStatistics(responseBean.getAuth_token(), clickList);
                if(respBean.getStatus() != 1) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    //@Scheduled(fixedRate = 3000)
    public void billAgencies() {
        List<AgencyDTO> agencies = agencyRepository.list();
        Date currentDate = new Date();
        agencies.forEach(agency -> {
            if(!agency.getActive()) {
                return;
            }

            try {
                AgencyGeneralConnector agencyConnector = AgencyConnectorFactory.getInstance().buildAgencyConnector(agency.getName(), agency.getProtocol(), agency.getUrl_service());
                AuthResponseBean responseBean = agencyConnector.obtainToken(agency.getSecret_token());
                if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                    return;
                }
                AdvertisementPlanDTO advertisementPlanDTO = advertisementPlanRepository.retrieve(agency.getAdvertisement_plan_id());

                BaseResponseBean baseResponseBean = agencyConnector.receiveBill(responseBean.getAuth_token(), Float.valueOf(advertisementPlanDTO.getPrice()), getYearOneMonthAgo(currentDate), getMonthOneMonthAgo(currentDate));
                if(baseResponseBean.getStatus() != 1) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    //@Scheduled(fixedRate = 3000)
    public void billPartners() {
        List<PartnerDTO> partners = partnerService.list();
        Date currentDate = new Date();
        partners.forEach(partner -> {
            if(!partner.getActive()) {
                return;
            }

            try {
                PartnerGeneralConnector partnerConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partner.getName(), partner.getProtocol(), partner.getUrl_service());
                AuthResponseBean responseBean = partnerConnector.obtainToken(partner.getSecret_token());
                if (responseBean == null || responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
                    return;
                }

                PartnerBalanceDTO partnerBalance = partnerRepository.obtainPartnerBalance(partner.getPartner_id(), getFirstDayOfLastMonth(Date.from(Instant.parse("2024-03-26T10:15:30.00Z"))), getLastDayOfLastMonth(Date.from(Instant.parse("2024-03-26T10:15:30.00Z"))));

                BaseResponseBean baseResponseBean = partnerConnector.receiveBill(responseBean.getAuth_token(), partnerBalance.getRegister_amount(), partnerBalance.getLogin_amount(), partnerBalance.getOwed_amount(), getYearOneMonthAgo(currentDate), getMonthOneMonthAgo(currentDate));
                if(baseResponseBean.getStatus() != 1) {
                    Logger.getAnonymousLogger().log(Level.WARNING, "Error something happened");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }


    private Date getStartWeek(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);

        return calendar.getTime();
    }

    private Date getEndWeek(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.add(Calendar.DAY_OF_WEEK, 6);

        return calendar.getTime();
    }

    public String getYearOneMonthAgo(Date currentDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);

        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public String getMonthOneMonthAgo(Date currentDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);

        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }


    public static Date getFirstDayOfLastMonth(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }

    public static Date getLastDayOfLastMonth(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }
}
