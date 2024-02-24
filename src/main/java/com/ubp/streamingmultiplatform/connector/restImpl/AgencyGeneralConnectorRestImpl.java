package com.ubp.streamingmultiplatform.connector.restImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.ubp.streamingmultiplatform.connector.AgencyGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.AdvertisementResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.AuthResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.BaseResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.ContentResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AgencyGeneralConnectorRestImpl extends AgencyGeneralConnector {
    @Override
    public AuthResponseBean obtainToken(String secret_token) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("secret_token", secret_token);
        ObjectMapper objectMapper = new ObjectMapper();
        AuthResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/obtain-token"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue((String) response.body(), AuthResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new AuthResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public AdvertisementResponseBean getAdvertisement(String fileName, String auth_token) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("file_name", fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        AdvertisementResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/get-advertisement"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue((String) response.body(), AdvertisementResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new AdvertisementResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public BaseResponseBean receiveBill(String auth_token, Float billedAmount, String year, String month) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("year", year);
        bodyMap.put("month", month);
        bodyMap.put("billed_amount", billedAmount);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/receive-bill"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue(response.body(), BaseResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new ContentResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public BaseResponseBean receiveStatistics(String auth_token, List<AdvertisementClickDTO> statistics) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("statistics", statistics);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponseBean responseBean;
        try {
            String bodyString = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/receive-statistics"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue(response.body(), BaseResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new BaseResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }
}
