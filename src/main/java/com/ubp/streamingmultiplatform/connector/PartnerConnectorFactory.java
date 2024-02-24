package com.ubp.streamingmultiplatform.connector;

import jakarta.xml.soap.SOAPException;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class PartnerConnectorFactory {

    private final LinkedHashMap<String, PartnerGeneralConnector> partnerConnectors = new LinkedHashMap<>();
    private static PartnerConnectorFactory instance;
    private PartnerConnectorFactory() {
    }
    public static synchronized PartnerConnectorFactory getInstance() {
        if (instance == null) {
            instance = new PartnerConnectorFactory();
        }
        return instance;
    }

    public PartnerGeneralConnector buildPartnerConnector(String name, String type, String url) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SOAPException {
        if(this.partnerConnectors.containsKey(name+type)) {
            return this.partnerConnectors.get(name+type);
        }

        Class<?> pClass;
        if("SOAP".equals(type)) {
            pClass = Class.forName("com.ubp.streamingmultiplatform.connector.soapImpl.PartnerGeneralConnectorSoapImpl");
        } else if ("REST".equals(type)) {
            pClass = Class.forName("com.ubp.streamingmultiplatform.connector.restImpl.PartnerGeneralConnectorRestImpl");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> pConstructor = pClass.getDeclaredConstructor();
        PartnerGeneralConnector pConnector = (PartnerGeneralConnector) pConstructor.newInstance();
        pConnector.setUrl(url);
        this.partnerConnectors.put(name+type, pConnector);
        return pConnector;
    }

}
