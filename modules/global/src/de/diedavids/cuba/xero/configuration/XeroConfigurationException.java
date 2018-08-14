package de.diedavids.cuba.xero.configuration;

import com.haulmont.cuba.core.global.SupportedByClient;

@SupportedByClient
public class XeroConfigurationException extends RuntimeException {

    public XeroConfigurationException(String message) {
        super(message);
    }
    public XeroConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
