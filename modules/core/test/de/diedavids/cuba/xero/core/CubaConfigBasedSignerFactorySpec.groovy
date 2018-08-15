package de.diedavids.cuba.xero.core

import com.google.api.client.auth.oauth.OAuthRsaSigner
import com.haulmont.cuba.core.app.FileStorageAPI
import com.haulmont.cuba.core.entity.FileDescriptor
import com.haulmont.cuba.core.global.Resources
import com.xero.api.Config
import de.diedavids.cuba.xero.configuration.XeroConfiguration
import groovy.transform.InheritConstructors
import spock.lang.Specification

class CubaConfigBasedSignerFactorySpec extends Specification {
    XeroConfiguration xeroConfiguration
    MockbaleCubaConfigBasedSignerFactory sut
    Config config

    void setup() {

        config = Mock(Config)
        config.appType >> "PRIVATE"
        config.privateKeyPassword >> "test"
        config.pathToPrivateKey >> null

        sut = new MockbaleCubaConfigBasedSignerFactory(this.config)
        xeroConfiguration = Mock(XeroConfiguration)
        sut.xeroConfiguration = xeroConfiguration

        sut.resources = Mock(Resources)
        sut.fileStorageAPI = Mock(FileStorageAPI)
        sut.xeroConfiguration = Mock(XeroConfiguration)
    }

    def "createSigner creates a OAuthRsaSigner when the correct attribute for the private key are given and the path based approach is used"() {
        given:
        def config = Mock(Config)
        config.appType >> "PRIVATE"
        config.privateKeyPassword >> "test"
        config.pathToPrivateKey >> "de/diedavids/cuba/xero/core/keys/public_privatekey-test.pfx"

        when:
        def result = sut.createSigner("mySecret")
        then:
        result instanceof OAuthRsaSigner
    }

    def "createSigner creates a OAuthRsaSigner when the correct attribute for the private key are given and the file storage based approach is used"() {
        given:
        def privateKeyFileDescriptor = Mock(FileDescriptor)
        xeroConfiguration.privateKeyFileDescriptor >> privateKeyFileDescriptor

        and:
        def publicPrivateKeyTest = loadPublicPrivateKey()
        sut.fileStorageAPI.loadFile(privateKeyFileDescriptor) >> publicPrivateKeyTest

        when:
        def result = sut.createSigner("mySecret")

        then:
        result instanceof OAuthRsaSigner
    }

    protected byte[] loadPublicPrivateKey() {
        this.getClass().getResource('/de/diedavids/cuba/xero/core/keys/public_privatekey-test.pfx').bytes
    }
}

@InheritConstructors
class MockbaleCubaConfigBasedSignerFactory extends CubaConfigBasedSignerFactory {

    FileStorageAPI fileStorageAPI
    Resources resources
    XeroConfiguration xeroConfiguration
}