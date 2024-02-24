package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.connector.PartnerConnectorFactory;
import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.AuthResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.TransactionIdResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.UserPartnerResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.VisualizationResponseBean;
import com.ubp.streamingmultiplatform.connector.mockImpl.PartnerGeneralConnectorMockImpl;
import com.ubp.streamingmultiplatform.model.ContentServeDTO;
import com.ubp.streamingmultiplatform.model.PartnerDTO;
import com.ubp.streamingmultiplatform.model.response.FederationOperationResponse;
import com.ubp.streamingmultiplatform.model.response.connectors.WatchContentResponse;
import com.ubp.streamingmultiplatform.repository.ContentRepository;
import com.ubp.streamingmultiplatform.repository.PartnerRepository;
import com.ubp.streamingmultiplatform.repository.PartnerUserDTO;
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

    public VisualizationResponseBean watch_content(Integer partnerId, Integer contentId, Integer userId) throws Exception {
        PartnerDTO partnerDTO = this.partnerRepository.retrieve(partnerId);
        ContentServeDTO contentServeDTO = contentRepository.retrieve_content(contentId, userId);
        PartnerUserDTO partnerUserDTO = this.partnerRepository.retrieve_user_partner(partnerId, userId);
        PartnerGeneralConnector partnerGeneralConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partnerDTO.getName(), partnerDTO.getProtocol(), partnerDTO.getUrl_service());

        AuthResponseBean responseBean = partnerGeneralConnector.obtainToken(partnerDTO.getSecret_token());
        if(responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
            throw new RuntimeException();
        }

        VisualizationResponseBean visualizationResponseBean = partnerGeneralConnector.createUrlForUser(contentServeDTO.getEidr_number(), partnerUserDTO.getIdentity_key(), responseBean.getAuth_token());
        if(visualizationResponseBean != null && visualizationResponseBean.getStatus() == 1) {
            this.contentRepository.createVisualization(userId, contentId, partnerId);
            return visualizationResponseBean;
        }
        throw new RuntimeException();
    }

    public TransactionIdResponseBean transaction_id(Integer partnerId, Integer userId, String callback_url) throws Exception {
        TransactionIdResponseBean transactionIdResponseBean = new TransactionIdResponseBean();


        try {
            PartnerUserDTO partnerUserDTO = this.partnerRepository.retrieve_user_partner(partnerId, userId);

            if ("ACTIVE".equals(partnerUserDTO.getStatus()) || callback_url == null) {
                transactionIdResponseBean.setStatus(-1);
                transactionIdResponseBean.setMessage("");
            } if ("INITIALIZE".equals(partnerUserDTO.getStatus()) && partnerUserDTO.getTransaction_id() != null) {
                FederationOperationResponse federationOperationResponse = this.transactionIdRequest(partnerUserDTO.getTransaction_id());
                if (federationOperationResponse.getStatus() == 1) {
                    transactionIdResponseBean.setStatus(2);
                    return transactionIdResponseBean;
                }
            }
        } catch (Exception e) {

        }

        PartnerDTO partnerDTO = this.partnerRepository.retrieve(partnerId);

        PartnerGeneralConnector partnerConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partnerDTO.getName(), partnerDTO.getProtocol(), partnerDTO.getUrl_service());

        AuthResponseBean responseBean = partnerConnector.obtainToken(partnerDTO.getSecret_token());
        if(responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
            throw new RuntimeException();
        }

        transactionIdResponseBean = partnerConnector.createTransactionIdForPartner(responseBean.getAuth_token(), callback_url);
        if(transactionIdResponseBean.getStatus() != 1 || transactionIdResponseBean.getTransaction_id() == null) {
            throw new RuntimeException();
        }

        this.partnerRepository.create_user_partner(partnerId, userId, transactionIdResponseBean.getTransaction_id());

        return transactionIdResponseBean;
    }

    public TransactionIdResponseBean deactivateUserPartner(Integer partnerId, Integer userId) throws Exception {
        TransactionIdResponseBean transactionIdResponseBean = new TransactionIdResponseBean();

        PartnerUserDTO partnerUserDTO = this.partnerRepository.deactivate_user_partner(partnerId, userId);

        if(!"INACTIVE".equals(partnerUserDTO.getStatus())) {
            transactionIdResponseBean.setStatus(-1);
            transactionIdResponseBean.setMessage("");
        }

        transactionIdResponseBean.setStatus(1);
        return transactionIdResponseBean;
    }

    public FederationOperationResponse transactionIdRequest(String transactionId) throws Exception {
        FederationOperationResponse transactionIdResponseBean = new FederationOperationResponse();

        PartnerUserDTO partnerUserDTO = this.partnerRepository.retrieve_user_partner_by_transaction_id(transactionId);

        if("ACTIVE".equals(partnerUserDTO.getStatus())) {
            transactionIdResponseBean.setStatus(-1);
            transactionIdResponseBean.setMessage("");
        }

        PartnerDTO partnerDTO = this.partnerRepository.retrieve(partnerUserDTO.getPartner_id());


        PartnerGeneralConnector partnerConnector = PartnerConnectorFactory.getInstance().buildPartnerConnector(partnerDTO.getName(), partnerDTO.getProtocol(), partnerDTO.getUrl_service());

        AuthResponseBean responseBean = partnerConnector.obtainToken(partnerDTO.getSecret_token());
        if(responseBean.getStatus() != 1 || responseBean.getAuth_token() == null) {
            throw new RuntimeException();
        }

        UserPartnerResponseBean userPartnerResponseBean = partnerConnector.retrieveUserPartnerByTransactionId(partnerUserDTO.getTransaction_id(), responseBean.getAuth_token());

        if(userPartnerResponseBean.getStatus() != 1) {
            throw new RuntimeException();
        }

        Float fee;
        if("REGISTER".equals(userPartnerResponseBean.getFederation_type())){
            fee = Float.valueOf(partnerDTO.getRegister_fee());
        } else if ("LOGIN".equals(userPartnerResponseBean.getFederation_type())) {
            fee = Float.valueOf(partnerDTO.getLogin_fee());
        } else if ("RETURN".equals(userPartnerResponseBean.getFederation_type())) {
            fee = (float) 0;
        } else {
            throw new RuntimeException();
        }

        PartnerUserDTO partnerUserDTO1 = partnerRepository.activate_user_partner(partnerUserDTO.getPartner_id(), partnerUserDTO.getUser_id(), userPartnerResponseBean.getIdentity_key(), userPartnerResponseBean.getFederation_type(), fee);
        transactionIdResponseBean.setStatus(1);
        transactionIdResponseBean.setPartnerAdded(partnerUserDTO1.getPartner_id());
        return transactionIdResponseBean;
    }
}
