package com.ubp.streamingmultiplatform.connector.mockImpl;

import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.*;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;
import com.ubp.streamingmultiplatform.model.response.connectors.WatchContentResponse;

import java.util.List;

public class PartnerGeneralConnectorMockImpl extends PartnerGeneralConnector {
    public WatchContentResponse retrieveContent(String userAuthToken, String eidNumber) {
        WatchContentResponse watchContentResponse = new WatchContentResponse();
        watchContentResponse.setStatus(1);
        watchContentResponse.setMessage(null);
        watchContentResponse.setVisualisationLink("https://www.youtube.com/embed/PGGjXdlbW58?si=CwWixzpO83RfDYAC");
        return watchContentResponse;
    }

    @Override
    public AuthResponseBean obtainToken(String secret_token) {
        return null;
    }

    @Override
    public UserPartnerResponseBean retrieveUserPartnerByTransactionId(String transaction_id, String auth_token) {
        return null;
    }

    @Override
    public TransactionIdResponseBean createTransactionIdForPartner(String auth_token, String callback_url) {
        return null;
    }

    @Override
    public VisualizationResponseBean createUrlForUser(String eidr_number, String identity_key, String auth_token) {
        return null;
    }

    @Override
    public ContentResponseBean listAllContents(String auth_token) {
        return null;
    }

    @Override
    public BaseResponseBean receiveBill(String auth_token, Float registerAmount, Float loginAmount, Float owedAmount, String year, String month) {
        return null;
    }

    @Override
    public BaseResponseBean receiveStatistics(String auth_token, List<PartnerClicksDTO> statistics) {
        return null;
    }
}
