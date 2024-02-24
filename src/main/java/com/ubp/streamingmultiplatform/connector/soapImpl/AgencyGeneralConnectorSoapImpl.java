package com.ubp.streamingmultiplatform.connector.soapImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.ubp.streamingmultiplatform.connector.AgencyGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.AdvertisementResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.AuthResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.BaseResponseBean;
import com.ubp.streamingmultiplatform.connector.beans.dto.AdvertisementClickDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
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
import java.util.List;

@Component
public class AgencyGeneralConnectorSoapImpl extends AgencyGeneralConnector {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    @Override
    public AuthResponseBean obtainToken(String secretToken) {
        String message = """
                <ws:obtainToken xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <secret_token>%s</secret_token>
                </ws:obtainToken>""".formatted(secretToken);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);
        AuthResponseBean responseBean = new AuthResponseBean();
        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if(responseBean.getStatus() == 1) {
            responseBean.setAuth_token(resultDocument.getElementsByTagName("auth_token").item(0).getFirstChild().getNodeValue());
        } else {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }
        return responseBean;
    }

    @Override
    public AdvertisementResponseBean getAdvertisement(String fileName, String auth_token) {
        String message = """
                <ws:getAdvertisement xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <file_name>%s</file_name>
                   <auth_token>%s</auth_token>
                </ws:getAdvertisement>""".formatted(fileName, auth_token);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);
        AdvertisementResponseBean responseBean = new AdvertisementResponseBean();

        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if(responseBean.getStatus() == 1) {
            responseBean.setRedirect_url(resultDocument.getElementsByTagName("redirect_url").item(0).getFirstChild().getNodeValue());
            responseBean.setImage_url(resultDocument.getElementsByTagName("image_url").item(0).getFirstChild().getNodeValue());
        } else {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

    @Override
    public BaseResponseBean receiveBill(String auth_token, Float billedAmount, String year, String month) {
        String message = """
                <ws:receiveBill xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <billed_amount>%s</billed_amount>
                   <year>%s</year>
                   <month>%s</month>
                   <auth_token>%s</auth_token>
                </ws:receiveBill>""".formatted(billedAmount, year, month, auth_token);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);
        BaseResponseBean responseBean = new BaseResponseBean();

        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if(responseBean.getStatus() != 1) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

    @Override
    public BaseResponseBean receiveStatistics(String auth_token, List<AdvertisementClickDTO> statistics) {
        ObjectMapper objectMapper = new ObjectMapper();
        String statisticsJson;
        try {
            statisticsJson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create().toJson(statistics);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String message = """
                <ws:receiveStatistics xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <statistics>%s</statistics>
                   <auth_token>%s</auth_token>
                </ws:receiveStatistics>""".formatted(statisticsJson, auth_token);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document resultDocument;
        Document sourceDocument;
        try {
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            resultDocument = docBuilder.newDocument();
            sourceDocument = docBuilder.parse(new ByteArrayInputStream(message.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }


        Source source = new DOMSource(sourceDocument);
        Result result = new DOMResult(resultDocument);

        webServiceTemplate.sendSourceAndReceiveToResult(this.url, source, result);
        BaseResponseBean responseBean = new BaseResponseBean();

        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if(responseBean.getStatus() != 1) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }
}
