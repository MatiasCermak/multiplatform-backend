package com.ubp.streamingmultiplatform.connector.soapImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.ubp.streamingmultiplatform.connector.PartnerGeneralConnector;
import com.ubp.streamingmultiplatform.connector.beans.*;
import com.ubp.streamingmultiplatform.connector.beans.dto.ContentsDTO;
import com.ubp.streamingmultiplatform.connector.beans.dto.PartnerClicksDTO;
import jakarta.xml.soap.MessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class PartnerGeneralConnectorSoapImpl extends PartnerGeneralConnector {
    private WebServiceTemplate webServiceTemplate;

    public PartnerGeneralConnectorSoapImpl() {
        this.webServiceTemplate = new WebServiceTemplate();
    }

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
        if (responseBean.getStatus() == 1) {
            responseBean.setAuth_token(resultDocument.getElementsByTagName("auth_token").item(0).getFirstChild().getNodeValue());
        } else {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }
        return responseBean;
    }

    @Override
    public UserPartnerResponseBean retrieveUserPartnerByTransactionId(String transaction_id, String auth_token) {
        String message = """
                <ws:retrieveUserPartnerByTransactionId xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <transaction_id>%s</transaction_id>
                   <auth_token>%s</auth_token>
                </ws:retrieveUserPartnerByTransactionId>""".formatted(transaction_id, auth_token);

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
        UserPartnerResponseBean responseBean = new UserPartnerResponseBean();
        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));

        if (responseBean.getStatus() == 1) {
            responseBean.setIdentity_key(resultDocument.getElementsByTagName("identity_key").item(0).getFirstChild().getNodeValue());
            responseBean.setFederation_type(resultDocument.getElementsByTagName("federation_type").item(0).getFirstChild().getNodeValue());

        } else {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

    @Override
    public TransactionIdResponseBean createTransactionIdForPartner(String auth_token, String callback_url) {
        String message = """
                <ws:createTransactionIdForPartner xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <auth_token>%s</auth_token>
                   <callback_url>%s</callback_url>
                </ws:createTransactionIdForPartner>""".formatted(auth_token, callback_url);

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
        TransactionIdResponseBean responseBean = new TransactionIdResponseBean();
        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if (responseBean.getStatus() == 1) {
            responseBean.setTransaction_id(resultDocument.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue());
        } else {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }
        return responseBean;
    }

    @Override
    public VisualizationResponseBean createUrlForUser(String eidr_number, String identity_key, String auth_token) {
        String message = """
                <ws:createUrlForUser xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <eidr_number>%s</eidr_number>
                   <identity_key>%s</identity_key>
                   <auth_token>%s</auth_token>
                </ws:createUrlForUser>""".formatted(eidr_number, identity_key, auth_token);

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
        VisualizationResponseBean responseBean = new VisualizationResponseBean();
        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));
        if (responseBean.getStatus() < 0) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        } else {
            responseBean.setVisualization_link(resultDocument.getElementsByTagName("visualization_link").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

    @Override
    public ContentResponseBean listAllContents(String auth_token) {
        String message = """
                <ws:listAllContents xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <auth_token>%s</auth_token>
                </ws:listAllContents>""".formatted(auth_token);

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
        ContentResponseBean responseBean = new ContentResponseBean();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<LinkedList<ContentsDTO>> typeRef = new TypeReference<LinkedList<ContentsDTO>>() {
        };

        responseBean.setStatus(Integer.parseInt(resultDocument.getElementsByTagName("status").item(0).getFirstChild().getNodeValue()));

        if (responseBean.getStatus() < 1) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        } else {
            try {
                responseBean.setProgramming(mapper.readValue(resultDocument.getElementsByTagName("programming").item(0).getFirstChild().getNodeValue(), typeRef));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return responseBean;
    }

    @Override
    public BaseResponseBean receiveBill(String auth_token, Float registerAmount, Float loginAmount, Float owedAmount, String year, String month) {
        String message = """
                <ws:receiveBill xmlns:ws="http://ws.das.ubp.edu.ar/">
                   <register_amount>%s</register_amount>
                   <login_amount>%s</login_amount>
                   <owed_amount>%s</owed_amount>
                   <year>%s</year>
                   <month>%s</month>
                   <auth_token>%s</auth_token>
                </ws:receiveBill>""".formatted(registerAmount, loginAmount, owedAmount, year, month, auth_token);

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
        if (responseBean.getStatus() != 1) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

    @Override
    public BaseResponseBean receiveStatistics(String auth_token, List<PartnerClicksDTO> statistics) {
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
        if (responseBean.getStatus() != 1) {
            responseBean.setMessage(resultDocument.getElementsByTagName("message").item(0).getFirstChild().getNodeValue());
        }

        return responseBean;
    }

}
