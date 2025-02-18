package com.ot.automation.tests;

import com.ot.automation.framework.octane.framework.BaseAutomationTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QATools extends BaseAutomationTest {
    static RemoteWebDriver driver;
    static WebDriverWait waiter;

    @BeforeSuite
    @Test
    @Parameters({"octaneUrl", "tenantId", "tenantName", "workspaceName", "userName", "password"})
    void beforeTestQATool(@Optional("https://integration-master-dev.almoctane.com") String octaneUrl, @Optional String tenantId, @Optional("FTE_*") String tenantName, @Optional("sanity_workspace") String workspaceName, @Optional("mqm_rnd@hpe.com") String userName, @Optional("JustK33pGoing!") String password) {
        initializeEnv(octaneUrl, tenantId, tenantName, workspaceName, userName, password);
        setupDriver(driver, waiter);
    }

    @Test
    @Parameters({"jenkinsURL", "octaneURL", "tenant", "userName", "password"})
    public void addOctaneToJenkins(@Optional("http://10.168.73.202:8080") String jenkinsURL, @Optional("https://integration-master-dev.almoctane.com") String octaneURL, @Optional("1001") String tenant, @Optional("mqm_rnd@hpe.com") String userName, @Optional("JustK33pGoing!") String password) {
        String location = octaneURL + "/ui/?p=" + tenant;
        navigateByUrl(jenkinsURL + "/configure");
        Jenkins.jenkinsUtils.clickAddServerButton();
        Jenkins.jenkinsUtils.setLocationField(location);
        Jenkins.jenkinsUtils.setClientIDField(userName);
        Jenkins.jenkinsUtils.setClientSecretField(password);
        Jenkins.jenkinsUtils.clickApplyButton();
        Jenkins.jenkinsUtils.clickSaveButton();
    }

    @Test
    @Parameters({"browserLabURL", "oauthClientId", "oauthClientSecret", "tenantId"})
    void verifyBrowserLabRespond(@Optional("https://integ-master.ftdigitallab.valueedge.saas.microfocus.com/selenium/wd/hub") String browserLabURL, @Optional() String oauthClientId, @Optional() String oauthClientSecret, @Optional() String tenantId) {
        try {

            ChromeOptions options = new ChromeOptions();

            Map<String, Object> dlOptions = new HashMap<>();
            dlOptions.put("oauthClientId", oauthClientId);
            dlOptions.put("oauthClientSecret", oauthClientSecret);
            dlOptions.put("tenantId", tenantId);
            dlOptions.put("region", "Europe (Frankfurt)");
            dlOptions.put("osType", "Windows Server 2022");
            dlOptions.put("browserType", "chrome");
            dlOptions.put("browserVersion", "latest");

            options.setCapability("dl:options", dlOptions);

            URL url = new URL(browserLabURL);

            WebDriver driver = new RemoteWebDriver(url, options);

            final String urlTest = "https://advantageonlinebanking.com/";
            driver.get(urlTest);

            String currentUrl = driver.getCurrentUrl();
            Assert.assertEquals(currentUrl, urlTest, "URL mismatch!");

            String pageTitle = driver.getTitle();
            Assert.assertTrue(pageTitle.contains("Home - Advantage Bank"), "Page title is incorrect!");

            driver.quit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
