package com.ubp.streamingmultiplatform.connector.restImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.*;
import com.ubp.streamingmultiplatform.connector.beans.dto.ContentsDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;
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
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PartnerGeneralConnectorRestImpl extends PartnerGeneralConnector {
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
    public UserPartnerResponseBean retrieveUserPartnerByTransactionId(String transaction_id, String auth_token) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("transaction_id", transaction_id);
        ObjectMapper objectMapper = new ObjectMapper();
        UserPartnerResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/partner-transaction-id"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue((String) response.body(), UserPartnerResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new UserPartnerResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public TransactionIdResponseBean createTransactionIdForPartner(String auth_token, String callback_url) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("callback_url", callback_url);
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionIdResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/transaction-id-partner"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue((String) response.body(), TransactionIdResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new TransactionIdResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public VisualizationResponseBean createUrlForUser(String eidr_number, String identity_key, String auth_token) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("eidr_number", eidr_number);
        bodyMap.put("identity_key", identity_key);
        ObjectMapper objectMapper = new ObjectMapper();
        VisualizationResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/create-url-user"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue((String) response.body(), VisualizationResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new VisualizationResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public ContentResponseBean listAllContents(String auth_token) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        ObjectMapper objectMapper = new ObjectMapper();
        ContentResponseBean responseBean;
        try {
            String bodyString = objectMapper.writeValueAsString(bodyMap);
            request = HttpRequest.newBuilder()
                    .uri(new URI(url + "/list-contents"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            responseBean = objectMapper.readValue(response.body(), ContentResponseBean.class);
            return responseBean;
        } catch (Exception e) {
            responseBean = new ContentResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public BaseResponseBean receiveBill(String auth_token, Float registerAmount, Float loginAmount, Float owedAmount, String year, String month) {
        HttpRequest request;
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response;
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("auth_token", auth_token);
        bodyMap.put("register_amount", registerAmount);
        bodyMap.put("login_amount", loginAmount);
        bodyMap.put("year", year);
        bodyMap.put("month", month);
        bodyMap.put("owed_amount", owedAmount);
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
            responseBean = new BaseResponseBean();
            responseBean.setStatus(-1);
            responseBean.setMessage("Unexpected Error");
            return responseBean;
        }
    }

    @Override
    public BaseResponseBean receiveStatistics(String auth_token, List<PartnerClicksDTO> statistics) {
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
