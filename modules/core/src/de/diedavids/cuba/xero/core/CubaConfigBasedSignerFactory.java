package de.diedavids.cuba.xero.core;

import com.google.api.client.auth.oauth.OAuthSigner;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.xero.api.Config;
import com.xero.api.HmacSignerFactory;
import com.xero.api.RsaSignerFactory;
import com.xero.api.SignerFactory;
import de.diedavids.cuba.xero.configuration.XeroConfiguration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class CubaConfigBasedSignerFactory implements SignerFactory {
    private static final String PUBLIC_APP = "PUBLIC";
    private Config config;

    public CubaConfigBasedSignerFactory(Config config) {
        this.config = config;
    }

    @Override
    public OAuthSigner createSigner(String tokenSharedSecret) {


        if (config.getAppType().equals(PUBLIC_APP)) {
            return new HmacSignerFactory(config.getConsumerSecret()).createSigner(tokenSharedSecret);
        } else {
            return new RsaSignerFactory(getPrivateKey(), config.getPrivateKeyPassword()).createSigner(
                    tokenSharedSecret);
        }
    }

    private InputStream getPrivateKey() {
        Resources resources = AppBeans.get(Resources.NAME);
        FileStorageAPI fileStorageAPI = AppBeans.get(FileStorageAPI.NAME);

        XeroConfiguration xeroConfiguration = AppBeans.get(Configuration.class)
                .getConfig(XeroConfiguration.class);

        InputStream result = null;

        if (xeroConfiguration.getPrivateKeyFileDescriptor() != null) {

            try {
                byte[] privateKeyBytes = fileStorageAPI.loadFile(xeroConfiguration.getPrivateKeyFileDescriptor());
                result = new ByteArrayInputStream(privateKeyBytes);
            } catch (FileStorageException e) {
                e.printStackTrace();
            }
        }
        else {
            String resourcePathToPrivateKey = config.getPathToPrivateKey();

            if (resourcePathToPrivateKey != null) {
                result = resources.getResourceAsStream(resourcePathToPrivateKey);
            }
        }

        return result;

    }

}
