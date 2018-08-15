package de.diedavids.cuba.xero.core

import de.diedavids.cuba.xero.configuration.XeroConfiguration
import de.diedavids.cuba.xero.configuration.XeroConfigurationException
import spock.lang.Specification

class XeroClientFactorySpec extends Specification {
    def "createClient returns an instance of a XeroClient when all attributes are valid"() {

        given:
        def xeroConfiguration = Mock(XeroConfiguration)
        def sut = new XeroClientFactory(xeroConfiguration: xeroConfiguration)

        and:
        xeroConfiguration.getConsumerKey() >> "123"
        xeroConfiguration.getConsumerSecret() >> "456"
        xeroConfiguration.getApiUrl() >> "myUrl"
        xeroConfiguration.getPrivateKeyPassword() >> "myPassword"
        xeroConfiguration.getPathToPrivateKey() >> "path/to/private.key"

        when:
        def result = sut.createClient()

        then:
        result
    }

    def "createClient throws a XeroConfigurationException when consumerKey is missing in the configuration"() {

        given:
        def xeroConfiguration = Mock(XeroConfiguration)
        def sut = new XeroClientFactory(xeroConfiguration: xeroConfiguration)

        and:
        xeroConfiguration.getConsumerKey() >> null

        when:
        sut.createClient()

        then:
        thrown(XeroConfigurationException)
    }

    def "createClient throws a XeroConfigurationException when neither a private Key fileDescriptor nor a path to a private key is given in the configuration"() {

        given:
        def xeroConfiguration = Mock(XeroConfiguration)
        def sut = new XeroClientFactory(xeroConfiguration: xeroConfiguration)

        and:
        xeroConfiguration.getConsumerKey() >> "123"
        xeroConfiguration.getConsumerSecret() >> "456"
        xeroConfiguration.getApiUrl() >> "myUrl"
        xeroConfiguration.getPrivateKeyPassword() >> "myPassword"

        and:
        xeroConfiguration.getPathToPrivateKey() >> null
        xeroConfiguration.getPrivateKeyFileDescriptor() >> null

        when:
        sut.createClient()

        then:
        thrown(XeroConfigurationException)
    }
}
