package de.diedavids.cuba.xero.configuration;

import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.Secret;

import java.util.UUID;

@Source(type = SourceType.DATABASE)
public interface XeroConfiguration extends com.haulmont.cuba.core.config.Config, com.xero.api.Config {


    @Override
    @Property("xero.appType")
    @Default("Public")
    String getAppType();

    @Override
    @Secret
    @Property("xero.privateKeyPassword")
    String getPrivateKeyPassword();

    @Override
    @Property("xero.pathToPrivateKey")
    String getPathToPrivateKey();

    @Property("xero.privateKeyFileDescriptor")
    FileDescriptor getPrivateKeyFileDescriptor();

    @Override
    @Secret
    @Property("xero.consumerKey")
    String getConsumerKey();

    @Override
    @Secret
    @Property("xero.consumerSecret")
    String getConsumerSecret();

    @Override
    @Property("xero.apiUrl")
    @Default("https://api.xero.com/api.xro/2.0/")
    String getApiUrl();

    @Override
    @Property("xero.fileUrl")
    @Default("https://api.xero.com/files.xro/1.0/")
    String getFilesUrl();

    @Override
    @Property("xero.requestTokenUrl")
    @Default("https://api.xero.com/oauth/RequestToken")
    String getRequestTokenUrl();

    @Override
    @Property("xero.authorizeUrl")
    @Default("https://api.xero.com/oauth/Authorize")
    String getAuthorizeUrl();

    @Override
    @Property("xero.accessTokenUrl")
    @Default("https://api.xero.com/oauth/AccessToken")
    String getAccessTokenUrl();


    @Override
    @Property("xero.userAgent")
    @Default("Xero-Java-SDK")
    String getUserAgent();

    @Override
    @Property("xero.accept")
    @Default("application/xml")
    String getAccept();

    @Override
    @Property("xero.redirectUrl")
    String getRedirectUri();

    @Override
    @Property("xero.proxyHost")
    String getProxyHost();

    @Override
    @Property("xero.proxyPort")
    @Default("80")
    long getProxyPort();

    @Override
    @Property("xero.proxyHttpsEnabled")
    @Default("false")
    boolean getProxyHttpsEnabled();

    @Override
    @Property("xero.connectionTimeout")
    @Default("60")
    int getConnectTimeout();

    @Override
    @Property("xero.readTimeout")
    @Default("60")
    int getReadTimeout();

    @Override
    @Property("xero.decimalPlaces")
    String getDecimalPlaces();

    @Override
    @Property("xero.appFirewallHostname")
    String getAppFirewallHostname();

    @Override
    @Property("xero.appFirewallUrlPrefix")
    String getAppFirewallUrlPrefix();

    @Override
    @Property("xero.keyStorePath")
    String getKeyStorePath();

    @Override
    @Secret
    @Property("xero.keyStorePassword")
    String getKeyStorePassword();

    @Override
    @Property("xero.usingAppFirewall")
    @Default("false")
    boolean isUsingAppFirewall();


    void setConsumerKey(String consumerKey);

    void setConsumerSecret(String consumerSecret);

    void setAppType(String appType);

    void setAuthCallBackUrl(String authCallbackUrl);

    void setConnectTimeout(int connectTimeout);

    void setReadTimeout(int readTimeout);

    void setDecimalPlaces(String decimalPlaces);

    void setUsingAppFirewall(boolean usingAppFirewall);

    void setAppFirewallHostname(String appFirewallHostname);

    void setAppFirewallUrlPrefix(String appFirewallUrlPrefix);

    void setKeyStorePath(String keyStorePath);

    void setKeyStorePassword(String keyStorePassword);
}
