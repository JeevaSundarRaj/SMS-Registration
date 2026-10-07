package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:test.properties")
public interface TestConfig extends Config {

    @Key("loginEmail")
    @DefaultValue("automationuser1.jeeva@gmail.com")
    String loginEmail();

    @Key("loginPassword")
    @DefaultValue("Dialpad@12345")
    String loginPassword();

    @Key("loginType")
    @DefaultValue("open")
    String loginType();

    @Key("baseUrl")
    @DefaultValue("https://dialpadbeta.com")
    String baseUrl();

    @Key("companyName")
    String companyName();

    @Key("brandName")
    String brandName();

    @Key("taxID")
    String taxID();

    @Key("emailID")
    String emailID();

    @Key("phoneNo")
    String phoneNo();

    @Key("webSite")
    String webSite();

    @Key("street")
    String street();

    @Key("city")
    String city();

    @Key("zipCode")
    String zipCode();

    @Key("commPurpose")
    String commPurpose();

    @Key("customerSupport")
    String customerSupport();

    @Key("cSupportReason")
    String cSupportReason();

    @Key("supportWeb")
    String supportWeb();

    @Key("commPurpose2")
    String commPurpose2();

    @Key("commPurpose3")
    String commPurpose3();

    @Key("commPurpose4")
    String commPurpose4();

    @Key("commPurpose5")
    String commPurpose5();

    @Key("commPurpose6")
    String commPurpose6();

    @Key("webSite2")
    String webSite2();

    @Key("faxUser.title")
    String faxUserTitle();

    @Key("faxCC.title")
    String faxCCTitle();

    @Key("faxDept.title")
    String faxDeptTitle();

    @Key("faxCC2.title")
    String faxCC2Title();

    @Key("faxDept2.title")
    String faxDept2Title();

    @Key("faxHelloFax.title")
    String faxHelloFaxTitle();

    @Key("faxReceiver.email")
    @DefaultValue("automationuser2.jeeva@gmail.com")
    String faxReceiverEmail();

    @Key("faxReceiver.password")
    @DefaultValue("Dialpad@12345")
    String faxReceiverPassword();

    @Key("faxUser.search")
    String faxUserSearch();

    @Key("faxCC.search")
    String faxCCSearch();

    @Key("faxDept.search")
    String faxDeptSearch();

    @Key("faxCC2.search")
    String faxCC2Search();

    @Key("faxDept2.search")
    String faxDept2Search();

    @Key("faxHelloFax.search")
    String faxHelloFaxSearch();

    @Key("largeFaxFilePath")
    @DefaultValue("src/test/resources/test_data/flower.pdf")
    String largeFaxFilePath();
}
