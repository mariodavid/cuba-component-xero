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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

public class CubaConfigBasedSignerFactory implements SignerFactory {


    private static final Logger log = LoggerFactory.getLogger(CubaConfigBasedSignerFactory.class);

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
            return new RsaSignerFactory(getPrivateKey(), config.getPrivateKeyPassword()).createSigner(tokenSharedSecret);
        }
    }

    private InputStream getPrivateKey() {

        FileDescriptor privateKeyFileDescriptor = tryToGetPrivateKeyFileDescriptor();

        if (privateKeyFileDescriptor != null) {
            return loadPrivateKeyFromFileStorage(privateKeyFileDescriptor);
        }
        else {
            return loadPrivateKeyFromFilepath();
        }

    }

    private InputStream loadPrivateKeyFromFileStorage(FileDescriptor privateKeyFileDescriptor) {
        try {
            FileStorageAPI fileStorageAPI = getFileStorageAPI();
            return new ByteArrayInputStream(fileStorageAPI.loadFile(privateKeyFileDescriptor));
        } catch (FileStorageException e) {
            log.error("Private Key file for referenece (xero.privateKeyFileDescriptor) could not be found via File Storage API.", e);
            return null;
        }
    }

    private InputStream loadPrivateKeyFromFilepath() {
        String resourcePathToPrivateKey = config.getPathToPrivateKey();

        if (resourcePathToPrivateKey != null) {
            Resources resources = getResources();
            return resources.getResourceAsStream(resourcePathToPrivateKey);
        }
        else {
            return null;
        }
    }

    protected Resources getResources() {
        return AppBeans.get(Resources.NAME);
    }


    protected FileStorageAPI getFileStorageAPI() {
        return AppBeans.get(FileStorageAPI.NAME);
    }

    private FileDescriptor tryToGetPrivateKeyFileDescriptor() {
        XeroConfiguration xeroConfiguration = getXeroConfiguration();
        return xeroConfiguration.getPrivateKeyFileDescriptor();
    }

    protected XeroConfiguration getXeroConfiguration() {
        return AppBeans.get(Configuration.class)
                    .getConfig(XeroConfiguration.class);
    }

}
