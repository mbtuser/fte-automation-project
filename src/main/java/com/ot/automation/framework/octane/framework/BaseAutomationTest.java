package com.ot.automation.framework.octane.framework;

import com.ot.automation.framework.backoffice.BackOffice;
import com.ot.automation.framework.jenkins.Jenkins;
import com.ot.automation.framework.octane.framework.Entities.Entity;
import com.ot.automation.framework.octane.ui.UI;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ot.automation.framework.octane.framework.GeneralUtils.timeoutSeconds;

public class BaseAutomationTest {
    public static RemoteWebDriver driver;
    public static WebDriverWait waiter;
    protected static BackOffice BO;
    protected static UI UI;
    protected static Jenkins Jenkins;
    public static Map<String, List<String>> sharedLoginCookies;
    public static String OCTANE_USERNAME;
    public static String OCTANE_PASSWORD;
    public static String OCTANE_URL;
    public static String OCTANE_TENANT_NAME;
    public static String OCTANE_TENANT_ID;
    public static String OCTANE_WORKSPACE_NAME;
    public static String OCTANE_WORKSPACE_ID;
    public static Boolean OCTANE_VE_ENVIRONMENT;
    public static Boolean LOCAL_RUN;
    public static String SERVER;
    public static String SCHEME;
    public static String PORT;
    public static String CONTEXT_PATH;

    public static void setWorkspace(String spaceName, String workspaceName) {
        OCTANE_TENANT_NAME = spaceName;
        OCTANE_WORKSPACE_NAME = workspaceName;
    }

    public void initializeEnv(String octaneUrl, String tenantId, String tenantName, String workspaceName, String userName, String password) {
        setupConfiguration(octaneUrl, tenantId, tenantName, workspaceName, userName, password);
        BO = new BackOffice();
        UI = new UI();
        Jenkins = new Jenkins();
    }

    public void setupDriver(RemoteWebDriver testDriver, WebDriverWait testWaiter) {
        driver = testDriver;
        waiter = testWaiter;

        if (LOCAL_RUN) {
            // Download the latest Chromedriver
            String chromeVersion = getChromeVersionFromCmd();
            String chromeDriverPath = downloadChromeDriver(chromeVersion);

            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            System.setProperty("java.awt.headless", "false");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-search-engine-choice-screen");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void loginOctane() {
        restLogin(OCTANE_USERNAME, OCTANE_PASSWORD);

        GeneralUtils.waitAtMostFor(500000, "UI Login was not successful", () -> {
            Boolean loginSuccessful;
            if (OCTANE_VE_ENVIRONMENT) {
                loginSuccessful = loginOctaneVE(OCTANE_URL, OCTANE_USERNAME, OCTANE_PASSWORD);
            } else {
                loginSuccessful = loginOctane(OCTANE_URL, OCTANE_USERNAME, OCTANE_PASSWORD);
            }
            return loginSuccessful;
        });

        OCTANE_TENANT_ID = OCTANE_TENANT_ID.isEmpty() ? GeneralUtils.getSpaceIdByName(OCTANE_TENANT_NAME) : OCTANE_TENANT_ID;
        OCTANE_WORKSPACE_ID = GeneralUtils.getWorkspaceIdByName(OCTANE_TENANT_ID, OCTANE_WORKSPACE_NAME);
    }

    public void navigateByUrl(UrlNavigation.Module module) {
        driver.get(module.getUrl());
        try {
            UI.expectUiToBeLoaded();
        } catch (AssertionError e) {
            navigateByUrl(OCTANE_URL);
        }
    }

    public void navigateByUrl(String url) {
        driver.get(url);
    }

    protected void navigateByUrlToEntity(Entity.Data entity) {
        driver.get(getNavigationUrlToEntity(entity));
        UI.expectUiToBeLoaded();
        UI.docViews.docView.expectVisible();
    }

    protected String getNavigationUrlToEntity(Entity.Data entity) {
//        if (entity.getMetadata().getEntityLevel().equals(Entity.EntityLevel.ADMIN)) {
        if (1 == 0) {
            return UrlUtils.getBaseUrl() + "/ui/?site#/entity-navigation?entityType=" + UrlUtils.getEntityTypeForUrl(entity) + "&id=" + entity.getId();
        } else {
            return UrlUtils.getUiProjectUrl() + "#/entity-navigation?entityType=" + UrlUtils.getEntityTypeForUrl(entity) + "&id=" + entity.getId();
        }
    }

    //region Chromedriver methods
    private String getChromeVersionFromCmd() {
        String majorVersion = null;
        try {
            Process process = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("version")) {
                    String[] parts = line.split("\\s+");
                    String version = parts[parts.length - 1];
                    majorVersion = version.split("\\.")[0];
                    reader.close();
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get local chrome version. ", e);
        }
        return majorVersion;
    }

    private String downloadChromeDriver(String chromeVersion) {
        String downloadedFilePath = downloadUsingPuppeteer("chromedriver", chromeVersion);
        System.out.println("chrome driver file path: " + downloadedFilePath);
        return downloadedFilePath;
    }

    private String downloadUsingPuppeteer(String type, String version) {
        final String[] arguments = {"npx.cmd", "@puppeteer/browsers", "install", type + "@" + version, "--path", "C:\\dev\\FteSanity\\src\\test\\resources"};
        System.out.println("using @puppeteer/browsers to download " + type);
        System.out.println(String.join(" ", arguments));
        List<String> output = GeneralUtils.executeCommand(arguments);
        assert output != null;
        return getDownloadedFilePath(output);
    }

    private String getDownloadedFilePath(List<String> results) {
        if (results.isEmpty()) {
            throw new RuntimeException("Expected output response is empty");
        }

        String filePath = null;
        for (String result : results) {
            if (result.contains("chrome")) {
                filePath = result.split(" ")[1];
                break;
            }
        }
        return filePath;
    }
    //endregion

    //region Login methods
    public void loginBackOffice(String user, String password) {
        navigateByUrl("https://bo.ct-shared.saas.microfocus.com/home/bl/desktop.html");
        new BaseElement(By.id("federateLoginName")).expectVisible();
        new BaseElement(By.id("federateLoginName")).sendKeys(user);
        new BaseElement(By.id("fed-submit")).click();
        new BaseElement(By.id("password")).expectVisible();
        new BaseElement(By.id("password")).sendKeys(password);
        new BaseElement(By.id("submit_button")).click();
        restLogin(OCTANE_USERNAME, OCTANE_PASSWORD);
    }

    public void logoutBackOffice() {
        navigateByUrl("https://bo.ct-shared.saas.microfocus.com/home/bl/desktop.html");
        new BaseElement(By.cssSelector("[ng-click*='toggleProfileMenu()']")).click();
        new BaseElement(By.cssSelector("[ng-click*='_logout()']")).click();
        new BaseElement(By.id("federateLoginName")).expectVisible();
        restLogout(OCTANE_USERNAME, OCTANE_PASSWORD);
    }

    private boolean loginOctane(String octaneUrl, String user, String password) {
        navigateByUrl(octaneUrl);
        BaseElement usernameBox = new BaseElement(By.id("federateLoginName"));
        usernameBox.expectVisible();
        usernameBox.sendKeys(user);
        new BaseElement(By.id("fed-submit")).click();
        BaseElement passwordBox = new BaseElement(By.id("password"));
        passwordBox.expectVisible();
        passwordBox.sendKeys(password);
        new BaseElement(By.id("submit_button")).click();
        return !(usernameBox.isVisible());
    }

    private boolean loginOctaneVE(String octaneUrl, String user, String password) {
        navigateByUrl(octaneUrl);
        UI.loginPage.expectVisible();
        UI.loginPage.login(user, password);
        return !(UI.loginPage.isVisible());
    }

    protected void restLogin(String user, String password) {
        Map<String, List<String>> loginCookies = null;
        String loginResource = "/authentication/sign_in";
        String loginBody = "{ \"user\":\"" + user + "\", \"password\":\"" + password + "\" }";

        RestCallResult loginResult = GeneralUtils.makeRestCallOctane("POST", loginResource, loginBody);

        assert loginResult != null;
        if (loginResult.getStatusCode() == 200) {
            // If login is successful, get cookies from the response headers
            loginCookies = extractCookiesFromHeaders(loginResult.getResponseHeaders());
        } else {
            System.out.println("Rest login failed. Status Code: " + loginResult.getStatusCode());
            System.out.println("Login Response Body: " + loginResult.getResponseBody());
        }
        sharedLoginCookies = loginCookies;
    }

    protected void restLogout(String user, String password) {
        String loginResource = "/authentication/sign_out";
        String loginBody = "{ \"user\":\"" + user + "\", \"password\":\"" + password + "\" }";

        RestCallResult loginResult = GeneralUtils.makeRestCallOctane("POST", loginResource, loginBody);
        assert loginResult.getStatusCode() == 200;
    }

    private Map<String, List<String>> extractCookiesFromHeaders(Map<String, List<String>> headers) {
        // Extract cookies from the headers
        List<String> cookies = headers.get("Set-Cookie");
        Map<String, List<String>> storedCookies = new HashMap<>();
        if (cookies != null) {
            storedCookies.put("Cookie", cookies);
        }

        return storedCookies;
    }


    private static void setupConfiguration(String octaneUrl, String tenantId, String tenantName, String workspaceName, String userName, String password) {
        if ((tenantId == null || tenantId.isEmpty()) && (tenantName == null || tenantName.isEmpty())) {
            throw new IllegalArgumentException("Either 'tenantId' or 'tenantName' must be provided in the configuration.");
        }

        OCTANE_URL = octaneUrl;
        OCTANE_USERNAME = userName;
        OCTANE_PASSWORD = password;
        OCTANE_TENANT_ID = (tenantId == null || tenantId.isEmpty()) ? "" : tenantId;
        OCTANE_TENANT_NAME = (tenantName == null || tenantName.isEmpty()) ? "" : tenantName;
        OCTANE_WORKSPACE_NAME = workspaceName;
        OCTANE_VE_ENVIRONMENT = false;
        LOCAL_RUN = (System.getenv("BROWSER_LAB_URL") == null);
    }

    //    private static void readConfiguration() {
//        // Get values from configuration.properties
////        Properties properties = loadProperties("configuration.properties");
//        Properties properties = loadProperties("src/main/java/com/ot/automation/framework/configuration.properties");
//
//        if (properties == null) {
//            return;
//        }
//
//        OCTANE_USERNAME = properties.getProperty("ui.userName");
//        OCTANE_PASSWORD = properties.getProperty("ui.password");
//
//        SERVER = properties.getProperty("basicServer");
//        SCHEME = properties.getProperty("basicScheme");
//        PORT = properties.getProperty("basicPort");
//        CONTEXT_PATH = properties.getProperty("basicContextPath");
//
//        OCTANE_URL = SCHEME + "://" + SERVER;
//        OCTANE_TENANT_NAME = properties.getProperty("ui.tenantName");
//        OCTANE_WORKSPACE_NAME = properties.getProperty("ui.workspaceName");
//        OCTANE_VE_ENVIRONMENT = Boolean.valueOf(properties.getProperty("isVEEnvironment"));
//        LOCAL_RUN = Boolean.valueOf(properties.getProperty("localRun"));
//    }
//
//    private static Properties loadProperties(String filename) {
//        Properties properties = new Properties();
//        try (InputStream input = new FileInputStream(filename)) {
////        try (InputStream input = GeneralUtils.class.getClassLoader().getResourceAsStream(filename)) {
//            if (input == null) {
//                System.out.println("Unable to find " + filename);
//                return null;
//            }
//            properties.load(input);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return properties;
//    }
//    //endregion
//
    @AfterSuite(alwaysRun = true)
    public void logoutOctane() {
        System.out.println("Last visited url: " + driver.getCurrentUrl());
        restLogout(OCTANE_USERNAME, OCTANE_PASSWORD);
        logoutUI();
        if (LOCAL_RUN) {
            try {
                if (driver != null) {
                    driver.quit();
                    driver = null;
                }
            } catch (Exception e) {
                System.err.println("Exception occurred during driver quit:");
                e.printStackTrace();
            } finally {
                if (driver != null) {
                    driver.quit();
                    driver = null;
                }
            }
        }
    }

    private void logoutUI() {
        try {
            GeneralUtils.waitAtMostFor(500000, "Logout was not successful", () -> {
                navigateByUrl(UrlNavigation.Module.HOME);

                try {
                    // Wait for the alert to be present and then accept it
                    Alert alert = new WebDriverWait(driver, timeoutSeconds).until(ExpectedConditions.alertIsPresent());
                    alert.accept();
                } catch (Exception e) {
                    System.out.println("No alert present");
                }

                BaseElement logoutButton = new BaseElement(Locator.cssSelector("[data-aid*='value-edge-logout'"));
                if (logoutButton.isVisible()) {
                    logoutButton.click();
                }

                BaseElement userProfileButton = new BaseElement(Locator.cssSelector("[data-aid*='open-user-profile'"));
                if (userProfileButton.isVisible()) {
                    userProfileButton.click();
                    new BaseElement(Locator.cssSelector("[data-aid*='mqm-user-details-on-logout'")).click();
                }

                GeneralUtils.delay(2000);
                return driver.getCurrentUrl().contains("browser_sign_out") ? null : true;
            });

            new BaseElement(Locator.name("federateLoginName")).expectVisible();

        } catch (Exception e) {
            System.err.println("Exception occurred during logout: ");
            e.printStackTrace();
        }
    }
}