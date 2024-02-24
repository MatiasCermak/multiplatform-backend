package com.ubp.streamingmultiplatform.connector;

import com.ubp.streamingmultiplatform.connector.beans.AdvertisementResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.AuthResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.BaseResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;

import java.util.List;

public abstract class AgencyGeneralConnector {
    protected String url = "";

    public void setUrl(String url) {
        this.url = url;
    }
    public abstract AuthResponseBean obtainToken(String secretToken);
    public abstract AdvertisementResponseBean getAdvertisement(String fileName, String auth_token);
    public abstract BaseResponseBean receiveBill(String auth_token, Float billedAmount, String year, String month);
    public abstract BaseResponseBean receiveStatistics(String auth_token, List<AdvertisementClickDTO> statistics);
}
