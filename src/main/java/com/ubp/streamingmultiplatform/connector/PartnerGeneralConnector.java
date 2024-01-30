package com.ubp.streamingmultiplatform.connector;

import com.ubp.streamingmultiplatform.model.response.connectors.WatchContentResponse;

public class PartnerGeneralConnector {
    public static PartnerGeneralConnector createPartner(Integer partnerId, String protocol){
        return new PartnerGeneralConnector();
    }

    public WatchContentResponse retrieveContent(String userAuthToken, String eidNumber) {
        WatchContentResponse watchContentResponse = new WatchContentResponse();
        watchContentResponse.setStatus(1);
        watchContentResponse.setMessage(null);
        watchContentResponse.setVisualisationLink("https://www.youtube.com/embed/PGGjXdlbW58?si=CwWixzpO83RfDYAC");
        return watchContentResponse;
    }


}
