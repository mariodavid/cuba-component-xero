package de.diedavids.cuba.xero.core;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.xero.api.XeroClient;
import de.diedavids.cuba.xero.configuration.XeroConfiguration;
import de.diedavids.cuba.xero.configuration.XeroConfigurationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component("ddcx_XeroClientFactory")
public class XeroClientFactory {

    @Inject
    private XeroConfiguration xeroConfiguration;

    public XeroClient createClient() throws XeroConfigurationException {

        validateXeroConfiguration();


        try {
            return initXeroClient();

        } catch (IOException e) {
            throw new XeroConfigurationException("Private Key PEM file not found. Either filename not configured right or file was not placed in the CUBA conf directory (see: https://doc.cuba-platform.com/manual-6.9/conf_dir.html)", e);
        }
    }

    private void validateXeroConfiguration() {
        String consumerKey = xeroConfiguration.getConsumerKey();
        String consumerSecret = xeroConfiguration.getConsumerSecret();
        String apiUrl = xeroConfiguration.getApiUrl();
        String pathToPrivateKey = xeroConfiguration.getPathToPrivateKey();
        FileDescriptor privateKeyFileDescriptor = xeroConfiguration.getPrivateKeyFileDescriptor();
        String privateKeyPassword = xeroConfiguration.getPrivateKeyPassword();

        if (invalid(consumerKey) ||
                invalid(consumerSecret) ||
                invalid(apiUrl) ||
                invalid(privateKeyPassword) ||
                (privateKeyFileDescriptor == null && invalid(pathToPrivateKey))
                ) {
            throw new XeroConfigurationException("Xero Configuration not properly configured. Check Admnistration > Application Settings > Xero");
        }
    }

    private boolean invalid(String parameter) {
        return StringUtils.isEmpty(parameter);
    }

    private XeroClient initXeroClient() throws IOException {
        return new XeroClient(xeroConfiguration, new CubaConfigBasedSignerFactory(xeroConfiguration));
    }


}
