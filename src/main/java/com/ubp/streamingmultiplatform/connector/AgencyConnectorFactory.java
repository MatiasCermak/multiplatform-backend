package com.ubp.streamingmultiplatform.connector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class AgencyConnectorFactory {

    private final LinkedHashMap<String, AgencyGeneralConnector> agencyConnectors = new LinkedHashMap<>();
    private static AgencyConnectorFactory instance;
    private AgencyConnectorFactory() {
    }
    public static synchronized AgencyConnectorFactory getInstance() {
        if (instance == null) {
            instance = new AgencyConnectorFactory();
        }
        return instance;
    }

    public AgencyGeneralConnector buildAgencyConnector(String name, String type, String url) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(this.agencyConnectors.containsKey(name+type)) {
            return this.agencyConnectors.get(name+type);
        }

        Class<?> pClass;
        if("SOAP".equals(type)) {
            pClass = Class.forName("com.ubp.streamingmultiplatform.connector.soapImpl.AgencyGeneralConnectorSoapImpl");
        } else if ("REST".equals(type)) {
            pClass = Class.forName("com.ubp.streamingmultiplatform.connector.restImpl.AgencyGeneralConnectorRestImpl");
        } else {
            throw new ClassNotFoundException();
        }

        Constructor<?> pConstructor = pClass.getDeclaredConstructor();
        AgencyGeneralConnector pConnector = (AgencyGeneralConnector) pConstructor.newInstance();
        pConnector.setUrl(url);
        this.agencyConnectors.put(name+type, pConnector);
        return pConnector;
    }

}
