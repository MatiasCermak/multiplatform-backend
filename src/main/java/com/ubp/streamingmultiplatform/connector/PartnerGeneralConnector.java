package com.ubp.streamingmultiplatform.connector;

import com.ubp.streamingmultiplatform.connector.beans.*;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;

import java.util.List;

public abstract class PartnerGeneralConnector {
    protected String url = "";

    public void setUrl(String url) {
        this.url = url;
    }

    public abstract AuthResponseBean obtainToken(String secret_token);

    public abstract UserPartnerResponseBean retrieveUserPartnerByTransactionId(String transaction_id, String auth_token);

    public abstract TransactionIdResponseBean createTransactionIdForPartner(String auth_token, String callback_url);

    public abstract VisualizationResponseBean createUrlForUser(String eidr_number, String identity_key, String auth_token);

    public abstract ContentResponseBean listAllContents(String auth_token);

    public abstract BaseResponseBean receiveBill(String auth_token, Float registerAmount, Float loginAmount, Float owedAmount, String year, String month);

    public abstract BaseResponseBean receiveStatistics(String auth_token, List<PartnerClicksDTO> statistics);

}
