[![Build Status](https://travis-ci.org/mariodavid/cuba-component-xero.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-xero)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-xero/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-xero/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# CUBA Platform Component - Xero

This application component let's you easily integrate with the Xero API.

It mainly leverages the official [Xero Java SDK](https://github.com/XeroAPI/Xero-Java). The configuration of the SDK is done via CUBA's application properties.


## Installation

1. `xero` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 6.9.x            | 0.1.x          |


The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-xero/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-xero/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.xero`
* Artifact name: `xero-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.xero:xero-global:*addon-version*")
}
```

### Add Xero API maven repository

In order to download all dependencies it is required to additionally add the following maven repositories to your `build.gradle`:

```

buildscript {
    
    //...
    
    repositories {

        maven { url "https://raw.github.com/XeroAPI/Xero-Java/mvn-repo" }
        maven { url "https://raw.github.com/XeroAPI/XeroAPI-Schemas/mvn-repo" }

        //...
        
    }
}
```

### Usage

In order to interact with the Xero API it is reqiured to setup an account and create the corresponding credentials
accordingly.

After that the configuation settings can be setup in the CUBA application.

#### Create Xero API credentials

In order to interact with the Xero API, the following steps are required:

1. create an account at [Xero](https://www.xero.com/signup/api/)
2. enable the [Xero demo company](https://developer.xero.com/documentation/getting-started/development-accounts/)
3. create a [application](https://api.xero.com/Application)

Once all those steps are done, you will get credentials from Xero (Consumer Key, Consumer Secret etc.)

Additionally a public / private key pair has to be created. See details here:
https://developer.xero.com/documentation/api-guides/create-publicprivate-key

Using OpenSSL it looks like this:
```
openssl genrsa -out privatekey.pem 1024
openssl req -new -x509 -key privatekey.pem -out publickey.cer -days 1825
openssl pkcs12 -export -out public_privatekey.pfx -inkey privatekey.pem -in publickey.cer
```

Now you should have a file called `public_privatekey.pfx` and a password defined for it.


#### Configure the Xero API

In the CUBA application it is possible to configure all required and optional attributes through `Administration > Application Properties > Xero`.
 
![Xero configuration](https://github.com/mariodavid/cuba-component-xero/blob/master/img/1-xero-configuration.png)

##### Using the private / public key file

The required file `public_privatekey.pfx` can be used in two ways:

The first option is to put the `public_privatekey.pfx` file into the [conf directory](https://doc.cuba-platform.com/manual-6.9/conf_dir.html) of the CUBA application.
In the configuration it has to be referenced via the application property `xero.pathToPrivateKey` where the filename
should be inserted as a value.

The second option uses the external files feature of the CUBA application. You can just upload the file via `Administration > External Files`.
Then in the configuration `xero.privateKeyFileDescriptor` has to point the FileDescriptor. This can be done
via the following String `sys$FileDescriptor-f0edbbb8-24c8-a179-a052-0e73f5eb88d1` where `f0edbbb8-24c8-a179-a052-0e73f5eb88d1` is the UUID of the uploaded file.
The ID of the file can be retrieved via the external files screen, when right click on the file and select `System information`.



##### Using the API in the application

In order to use the Xero API, you can use the default `XeroClient` from the official SDK.
To get a reference to a `XeroClient` instance, you can use the following snippet:

```
@Inject
XeroClientFactory xeroClientFactory;

public List<Invoice> getInvoicesFromXero() {

    XeroClient client = xeroClientFactory.createClient();
    List<Invoice> xeroInvoiceList = client.getInvoices();

    return xeroInvoiceList;
}


```


### Example usage

An example usage of this application component can be found here: [cuba-example-using-xero](https://github.com/mariodavid/cuba-example-using-xero)


### More information

More information on how to use Xero as well as using additional configurations, the following resources should be looked at:

* https://developer.xero.com/
* https://developer.xero.com/documentation/libraries/java
* https://github.com/XeroAPI/Xero-Java


