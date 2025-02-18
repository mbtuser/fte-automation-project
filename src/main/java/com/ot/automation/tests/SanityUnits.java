package com.ot.automation.tests;

import com.ot.automation.framework.octane.framework.*;
import com.ot.automation.framework.octane.framework.Entities.*;
import com.ot.automation.framework.octane.ui.spaces.DevOpsView;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ot.automation.framework.octane.framework.GeneralUtils.constructQueryString;

public class SanityUnits extends BaseAutomationTest {
    static RemoteWebDriver driver;
    static WebDriverWait waiter;
    private final static String mbtUftRepoUrl = "https://github.com/mbtuser/mbtuser.git";
    private final static String mbtUftGitUrl = "https://github.com/mbtuser/FTEUFTOne.git";
    private final static String seleniumRepoUrl = "https://github.com/mbtuser/FTESelenium.git";
    //    private final static String seleniumRepoUrl = "https://github.com/mbtuser/Selenium-demo.git";
    private final static String mbtUftDpRepoUrl = "https://github.com/mbtuser/UFTOne-Data-Population.git";
    private final static String seleniumDpRepoUrl = "https://github.com/mbtuser/Selenium-Data-Population.git";
    private final static String packagePathSelenium = "org.example";
    private final static String packagePathExecutionStorage = "opentext.sanity";
    private final static String packagePathDataPopulation = "opentext.mbt.tests";
    private final static String uftRunnerName = "UFT";
    private final static String mbtRunnerName = "MBT (Open Text Functional Testing)";
    private final static String mbtRunnerNameOld = "MBT (UFT)";
    private final static String mbtDPRunnerName = "MBT (UFT) Data-Population";
    private final static String seleniumRunnerName = "Selenium (TestNG)";
    private final static String seleniumMbtRunnerName = "MBT (Selenium TestNG)";
    private final static String seleniumMbtDPRunnerName = "MBT (Selenium TestNG) Data-Population";
    private final static String seleniumExecutionStorageRunnerName = "Selenium Execution Storage";
    private final static String scheduleNameMbt = "SCH_MBT_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameUft = "SCH_UFT_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameUftRec = "SCH_UFT_Recurring_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameMobile = "SCH_Mobile_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameBrowsers = "SCH_Browsers_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameSel = "SCH_Selenium_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameSelMbt = "SCH_MBT_Selenium_" + RandomStringUtils.randomAlphabetic(5);
    private final static String scheduleNameExecSt = "SCH_Execution_Storage_" + RandomStringUtils.randomAlphabetic(5);
    private final static String seleniumTestToRun = "browserGoogleSearchTest";
    private final static String seleniumReportTestToRun = "browserParameterXmlUrlTest";
    private final static String ciJenkinsName = "CIJenkins";
    private final static String jenkinsURL = "http://auto-fte-uft:8080";
    private final static String mbtTestRunnerName = "MBT_Jenkins";
    private final static String uftTestRunnerName = "UFT_Jenkins";
    private final static String mbtFrameworkName = "MBT (Open Text Functional Testing)";
    private final static String uftFrameworkName = "UFT";
    private final static boolean waitForResults = true;

    @BeforeSuite
    @Test
    @Parameters({"octaneUrl", "tenantId", "tenantName", "workspaceName", "userName", "password"})
    void beforeTest(@Optional("https://integration-master-dev.almoctane.com") String octaneUrl, @Optional String tenantId, @Optional("FTE_*") String tenantName, @Optional("sanity_workspace") String workspaceName, @Optional("mqm_rnd@hpe.com") String userName, @Optional("JustK33pGoing!") String password) {
        initializeEnv(octaneUrl, tenantId, tenantName, workspaceName, userName, password);
        setupDriver(driver, waiter);
    }

    @Test
    public void loginOctaneUnit() {
        loginOctane();
    }

    @Test
    public void createTenantUnit() {
        loginBackOffice("mqm_rnd@hpe.com", "JustK33pGoing!");
        if (GeneralUtils.getSiteParameter("FTE_AI_ACCESS_KEY").equals("")) {
            GeneralUtils.setSiteParameter("FTE_AI_ACCESS_KEY", "clientID=fte_ai_key_frankfurt_temp_n0edpr98klyz0u9ng3vwp78xo;secret=-15924730165214451105P;tenant=460005;url=https://ftai-eur.saas.microfocus.com/;");
        }

        if (GeneralUtils.getSiteParameter("AUTO_INSTALL_EXTENSION_BUNDLES_ON_SPACE_CREATION").equals("false")) {
            GeneralUtils.setSiteParameter("AUTO_INSTALL_EXTENSION_BUNDLES_ON_SPACE_CREATION", "true");
        }

        if (GeneralUtils.getSiteParameter("SET_VE_INSTANCE_ID_INTERNALLY").equals("true")) {
            GeneralUtils.setSiteParameter("SET_VE_INSTANCE_ID_INTERNALLY", "false");
        }

        try {
            GeneralUtils.waitAtMostFor(500000, "Navigate failed", () -> {
                String url = "https://bo.ct-shared.saas.microfocus.com/home/bl/desktop.html?TENANTID=1#/ofs/orders/subscriptions/new/0";
                navigateByUrl(url);
                GeneralUtils.delay(3000);

                return BaseAutomationTest.driver.getCurrentUrl().contains(url) ? true : null;
            });

        } catch (Exception e) {
            System.err.println("Exception occurred during navigate: ");
            e.printStackTrace();
        }

        BO.ipPage.expectVisible();
        BO.ipPage.generalTab.expectVisible();

        BO.ipPage.generalTab.clickAssignAccountButton();
        BO.dialogsBO.assignAccountDialog.selectAccount("MQM AWS Integration Branch Prod");

        BO.ipPage.generalTab.selectReasonType("SaaS Flex Order");
        BO.ipPage.generalTab.setReasonDetails("any");
        BO.ipPage.generalTab.selectProduct("ALM Octane");
        BO.ipPage.generalTab.setApprovedBy(OCTANE_USERNAME);
        BO.ipPage.generalTab.setAttachment("C:\\Windows\\System32\\ActiveHours.png");

        String sub1name = "VSM_Quality_FT_CC_User_VE"; // SA-AC135
        String sub2name = "FT_Digital_Lab_Concurrent_Devices_VE"; // SA-AC096
        String sub3name = "FT_Execution_Concurrent_Runs_VE"; // SA-AC095
        String sub4name = "Aviator_Named_User_VE"; // SA-AC242

        Integer sub1quantity = 35;
        Integer sub2quantity = 45;
        Integer sub3quantity = 3;
        Integer sub4quantity = 1;

        BO.ipPage.clickSubscriptionTermsTab();
        BO.ipPage.subscriptionsTab.checkApplyDateToAll();
        BO.ipPage.subscriptionsTab.addNewSubscription(sub1quantity, sub1name, null, null);
        BO.ipPage.subscriptionsTab.addNewSubscription(sub2quantity, sub2name, null, null);
        BO.ipPage.subscriptionsTab.addNewSubscription(sub3quantity, sub3name, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BO.ipPage.subscriptionsTab.addNewSubscription(sub4quantity, sub4name, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        BO.ipPage.clickTenantsTab();

        String farm = "MQM AWS Integration Master Dev: America - North";
        String timezone = "(GMT +2) Europe/Bucharest)";
        String NEW_OCTANE_TENANT_NAME = OCTANE_TENANT_NAME.replace("*", "") + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy")) + "_" + RandomStringUtils.randomAlphabetic(3);

        BO.ipPage.tenantsTab.addTenant(NEW_OCTANE_TENANT_NAME, farm, timezone, "Basic", "Dev", List.of(sub1quantity, sub2quantity, sub3quantity), List.of(sub1name, sub2name, sub3name));
//        BO.ipPage.tenantsTab.addTenant(NEW_OCTANE_TENANT_NAME, farm, timezone, "Basic", "Dev", List.of(sub1quantity, sub2quantity, sub3quantity, sub4quantity), List.of(sub1name, sub2name, sub3name, sub4name));
        BO.ipPage.clickApproveSubmit();

        // Validate tenant is created with ValueEdge Instance ID
        GeneralUtils.waitAtMostFor(120000, "Timeout while waiting for tenant creation", () -> {
            SharedSpace.Data tenant = SharedSpace.getEntityByName(OCTANE_TENANT_NAME, List.of(SharedSpace.Fields.VALUE_EDGE_INSTANCE_ID));
            return tenant.getField(SharedSpace.Fields.VALUE_EDGE_INSTANCE_ID);
        });

        OCTANE_TENANT_ID = GeneralUtils.getSpaceIdByName(NEW_OCTANE_TENANT_NAME);
        logoutBackOffice();
    }

    @Test
    public void validateCIServerConnectedUnit() {
        navigateToDevOpsTab();
        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.CI_SERVER);
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.toolbar.clickRefresh();

        // Validate CI Server is connected (Rest)
        CIServer.Data ciServer = CIServer.getEntityByName("VEFT Execution default OT Cloud CI", List.of(CIServer.Fields.IS_CONNECTED));

        // If CI Server is not connected, reset FTE_REGISTRATION_ENABLED parameter
        if (!isCiServerConnected()) {
            resetFteRegistrationEnabledParameter();
        }

        // If CI Server is still not connected, delete the api key
        if (!isCiServerConnected()) {
            deleteApiKey();
        }

        Assert.assertTrue(isCiServerConnected(), "CI Server is_connected field value is false");
        String ciServerId = ciServer.getId();

        // Validate CI Server is connected (UI)
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.expectVisible();
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).expectVisible();
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.NAME).expectVisible();
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.NAME).expectTextToContain("VEFT Execution default OT Cloud CI");
        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.IS_CONNECTED).expectVisible();
        BaseElement successIcon = new BaseElement(By.cssSelector("[href*='#f-status-passed']"), UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.IS_CONNECTED));
        successIcon.expectVisible();

        //Add release
        Release.create("Release_" + RandomStringUtils.randomAlphabetic(5), LocalDateTime.now(), LocalDateTime.now().plusDays(30), 7);
    }

    @Test
    public void validateCodelessRunnerCreated() {
        // Validate codeless runner was created automatically
        String codelessRunnerName = "Codeless Runner";
        CloudRunner.Data codelessRunner = CloudRunner.getEntityByName(codelessRunnerName, List.of(CloudRunner.Fields.NAME, CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK));
        Assert.assertTrue(codelessRunner.getId() != null, "Cannot find " + codelessRunnerName);
        Assert.assertTrue(codelessRunner.getField(CloudRunner.Fields.NAME).contentEquals(codelessRunnerName));
        Assert.assertTrue(codelessRunner.getField(CloudRunner.Fields.CI_SERVER).contains("VEFT Execution default OT Cloud CI"));
        Assert.assertTrue(codelessRunner.getField(CloudRunner.Fields.FRAMEWORK).contains("list_node.je.framework.codeless"));
    }

    @Test
    public void validateTestRunnersTabUnit() {
        navigateToDevOpsTab();
        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.TEST_RUNNERS);
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.expectVisible();

        String mbtRunnerName = getMbtRunnerName();

        // Create test runners
        CloudRunner.Data cr1 = createCloudRunner(trimParenthesis(uftRunnerName), uftRunnerName, mbtUftRepoUrl, null);
        CloudRunner.Data cr2 = createCloudRunner(trimParenthesis(mbtRunnerName), mbtRunnerName, mbtUftRepoUrl, null);
        CloudRunner.Data cr3 = createCloudRunner(trimParenthesis(seleniumRunnerName), seleniumRunnerName, seleniumRepoUrl, packagePathSelenium);
        CloudRunner.Data cr4 = createCloudRunner(trimParenthesis(seleniumMbtRunnerName), seleniumMbtRunnerName, seleniumRepoUrl, packagePathSelenium);
        CloudRunner.Data cr5 = createCloudRunner(trimParenthesis(seleniumExecutionStorageRunnerName), seleniumRunnerName, null, packagePathExecutionStorage);
        CloudRunner.Data cr6 = createCloudRunner(trimParenthesis(mbtDPRunnerName), mbtRunnerName, mbtUftDpRepoUrl, null);
        CloudRunner.Data cr7 = createCloudRunner(trimParenthesis(seleniumMbtDPRunnerName), seleniumMbtRunnerName, seleniumDpRepoUrl, packagePathDataPopulation);

        // Sync runners
        syncRunner(cr1);
        syncRunner(cr2);
        syncRunner(cr3);
        syncRunner(cr4);
        syncRunner(cr6);
        syncRunner(cr7);

        // Validate sync status for each
        GeneralUtils.waitAtMostFor(150000, "Sync status for " + uftRunnerName + " is not successful.", () -> isSyncSuccessful(cr1.getId()) ? true : null);
        GeneralUtils.waitAtMostFor(150000, "Sync status for " + mbtRunnerName + " is not successful.", () -> isSyncSuccessful(cr2.getId()) ? true : null);
        GeneralUtils.waitAtMostFor(150000, "Sync status for " + seleniumRunnerName + " is not successful.", () -> isSyncSuccessful(cr3.getId()) ? true : null);
        GeneralUtils.waitAtMostFor(150000, "Sync status for " + seleniumMbtRunnerName + " is not successful.", () -> isSyncSuccessful(cr4.getId()) ? true : null);

        // Validate sync status in UI
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickRefresh();
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr1.getId()).getCell("sync_status").expectTextToContain("Success");
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr2.getId()).getCell("sync_status").expectTextToContain("Success");
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr3.getId()).getCell("sync_status").expectTextToContain("Success");
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr4.getId()).getCell("sync_status").expectTextToContain("Success");
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr6.getId()).getCell("sync_status").expectTextToContain("Success");
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr7.getId()).getCell("sync_status").expectTextToContain("Success");
    }

    @Test
    public void sanityTestCreateJenkinsCIAndRunner() {
        addCIJenkins(ciJenkinsName, jenkinsURL);
        addTestRunner(mbtTestRunnerName, ciJenkinsName, mbtFrameworkName, mbtUftGitUrl);
        addTestRunner(uftTestRunnerName, ciJenkinsName, uftFrameworkName, mbtUftGitUrl);
    }

    @Test
    public void sanityTestExecutionUftRecurringUnit() {
        // Create TS for UFT
        TestSuite.Data tsUft = TestSuite.create("UFT_TS_" + RandomStringUtils.randomAlphabetic(5));
        tsUft = TestSuite.getEntityById(tsUft.getId(), List.of(TestSuite.Fields.NAME));
        navigateByUrlToEntity(tsUft);
        addTestToTestSuite("FunctionLibraryAutomationTest");

        // Create schedule for UFT recurring
        createSchedule(scheduleNameUftRec, "Recurring");
        addSuiteToSchedule(tsUft);
        String recurringScheduleRunId = startSchedule();

        // Wait for results
        if (waitForResults) {
            expectScheduleCompleted(recurringScheduleRunId);
            expectGeneralRunStatus(recurringScheduleRunId, 1, 0, 0);

            // Download report
            navigateByUrlToEntity(tsUft);

            UI.docViews.docView.selectTab("suite_runs");
            UI.docViews.docView.runsContainer.grid.getRow(1).clickId();

            UI.docViews.docView.selectTab("run");
            UI.docViews.docView.suiteRunsContainer.grid.getRow(1).clickId();

            UI.docViews.docView.detailsTab.expectVisible();
            UI.docViews.docView.detailsTab.toolbar.clickDownloadTestRunReport();
            UI.notifications.success.expectTextToContain("Test run report successfully downloaded");
        }
    }

    @Test
    public void sanityTestExecutionMbtUnit() {
        // Create TS for MBT
        Unit.Data unit1 = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\FlightLinkParamAutomationTests\\\\Action1:1+Login'");
        Unit.Data unit2 = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\FlightLinkParamAutomationTests\\\\Action2:2+Flight+Confirmation'");
        Unit.Data unit3 = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\FlightLinkParamAutomationTests\\\\Action3:3+Search+Order'");
        Model.Data model = createModel("Link_Param_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(getMbtRunnerName()), List.of(unit1, unit2, unit3));

        ModelBasedTest.Data mbtTest = createMbtTest(model);
        navigateByUrlToEntity(mbtTest);
        UI.docViews.docView.selectTab("model-based-test-parameters-tab");

        // Link parameters
        if (!UI.docViews.docView.parametersContainer.grid.getRow(4).getCell("output_parameter").getText().contains("Order_Number")) {
            UI.docViews.docView.parametersContainer.grid.getRow(4).select();
            UI.docViews.docView.parametersContainer.grid.getRow(6).select();
            UI.docViews.docView.parametersContainer.toolbar.clickLinkParameters();
            UI.docViews.docView.parametersContainer.toolbar.clickRefresh();
        }
        UI.docViews.docView.parametersContainer.grid.expectVisible();
        UI.docViews.docView.parametersContainer.grid.getRow(4).getCell("output_parameter").expectTextToContain("Order_Number");

        // Add iterations
        UI.docViews.docView.selectTab("mbt-data-table");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).sendKeys("John");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(3).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(3).sendKeys("hp");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.toolbar.clickSave();

        TestSuite.Data tsMbt = TestSuite.create("MBT_UFT_Link_Param_TS_" + RandomStringUtils.randomAlphabetic(5));
        tsMbt = TestSuite.getEntityById(tsMbt.getId(), List.of(TestSuite.Fields.NAME));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt);

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 0, 1, 0);
        }
    }

    @Test
    public void sanityTestExecutionMobileUnit() {
        // Create TS for Mobile
        TestSuite.Data tsMobile = TestSuite.create("UFT_Mobile_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsMobile);
        addTestToTestSuite("iOS_native_InApp", "Android_Native_InApp");

        // Create schedule for mobile
        createSchedule(scheduleNameMobile, "Once");
        addSuiteToSchedule(tsMobile.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 2, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionBrowsersUnit() {
        // Create TS for Browsers
        TestSuite.Data tsBrowsers = TestSuite.create("UFT_Browsers_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsBrowsers);
        addTestToTestSuite("BrowsersTesFireFox", "BrowsersTestEdge");
        setMachineTemplateAndRunMode("UFTOne", "Parallel");
        UI.docViews.docView.detailsTab.toolbar.clickSave();

        // Create schedule for browsers
        createSchedule(scheduleNameBrowsers, "Once");
        addSuiteToSchedule(tsBrowsers.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 2, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionSeleniumUnit() {
        // Create TS for Selenium
        TestSuite.Data tsSelenium = TestSuite.create("Selenium_TS_" + RandomStringUtils.randomAlphabetic(5));
        tsSelenium = TestSuite.getEntityById(tsSelenium.getId(), List.of(TestSuite.Fields.NAME));
        navigateByUrlToEntity(tsSelenium);
        addTestToTestSuite(seleniumTestToRun);
        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
        UI.docViews.docView.detailsTab.toolbar.clickSave();

        // Create schedule for Selenium
        createSchedule(scheduleNameSel, "Once");
        addSuiteToSchedule(tsSelenium);

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionSeleniumMbtUnit() {
        // Create TS for Selenium MBT
        Unit.Data unit = Unit.getEntityByName(seleniumTestToRun);
        Model.Data modelSelenium = createModel("Selenium_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(seleniumMbtRunnerName), List.of(unit));
        ModelBasedTest.Data mbtSeleniumTest = createMbtTest(modelSelenium);
        TestSuite.Data tsSeleniumMbt = TestSuite.create("MBT_Selenium_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsSeleniumMbt);
        addTestToTestSuite(mbtSeleniumTest.getName());
        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
        UI.docViews.docView.detailsTab.toolbar.clickSave();

        // Create schedule for Selenium Mbt
        createSchedule(scheduleNameSelMbt, "Once");
        addSuiteToSchedule(tsSeleniumMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestReportMbtUFTUnit() {
        // Create TS for MBT
        Unit.Data unit = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\FlightLoginRecoveryWarningAutomationTest\\\\Action1:Login'");
        Model.Data model = createModel("Recovery_Warning_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), List.of(unit));
        ModelBasedTest.Data mbtTest = createMbtTest(model);
        TestSuite.Data tsMbt = TestSuite.create("MBT_UFT_Recovery_Warning_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);

            // Verify report text
            verifyReport(tsMbt, Arrays.asList("Login (User_Name = John, Password = hp, Wrong_User_Name = WrongUser, Wrong_Password = WrongPassword)", "Warning login Tests recovery Description (Warning)."));
        }
    }

    @Test
    public void sanityTestReportMbtSeleniumUnit() {
        // Create TS for Selenium MBT
        Unit.Data unit = Unit.getEntityByName(seleniumReportTestToRun);
        Model.Data modelSelenium = createModel("Selenium_Report_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(seleniumMbtRunnerName), List.of(unit));
        ModelBasedTest.Data mbtSeleniumTest = createMbtTest(modelSelenium);

        navigateByUrlToEntity(mbtSeleniumTest);
        UI.docViews.docView.selectTab("mbt-data-table");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).getCell(2).sendKeys("https://www.google.com/");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).click();
        UI.docViews.docView.dataSetsContainer.toolbar.clickSave();

        TestSuite.Data tsSeleniumMbt = TestSuite.create("Selenium_Report_MBT_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsSeleniumMbt);
        addTestToTestSuite(mbtSeleniumTest.getName());
        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
        UI.docViews.docView.detailsTab.toolbar.clickSave();

        // Create schedule for Selenium Mbt
        createSchedule(scheduleNameSelMbt, "Once");
        addSuiteToSchedule(tsSeleniumMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);

            // Verify report text
            verifyReport(tsSeleniumMbt, Arrays.asList("browserParameterXmlUrlTest (site = https://www.google.com/)"));
        }
    }

    @Test
    public void sanityTestFunctionLibrariesMbtUFTUnit() {
        // Create TS for MBT
        Unit.Data unit = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\FunctionLibraryAutomationTest\\\\Action1:Action1'");
        Model.Data model = createModel("Function_Libraries_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), List.of(unit));
        ModelBasedTest.Data mbtTest = createMbtTest(model);
        TestSuite.Data tsMbt = TestSuite.create("MBT_UFT_Function_Libraries_TS_" + RandomStringUtils.randomAlphabetic(5));
        tsMbt = TestSuite.getEntityById(tsMbt.getId(), List.of(TestSuite.Fields.NAME));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt);

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestCancelledByUserUnit() {
        // Create TS for UFT
        TestSuite.Data tsUft = TestSuite.create("UFT_Cancelled_By_User_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsUft);
        addTestToTestSuite("FlightLoginAutomationTest");

        // Create schedule for UFT
        createSchedule(scheduleNameUft, "Once");
        addSuiteToSchedule(tsUft.getId());

        // Start the schedule and wait for it to be Running (total_suite_count > 0)
        startSchedule();
        GeneralUtils.waitAtMostFor(60000, "Schedule is not running", () -> {
            SuiteRunSchedulerRun.Data scr = SuiteRunSchedulerRun.getEntityByName(scheduleNameUft, List.of(SuiteRunSchedulerRun.Fields.STATUS, SuiteRunSchedulerRun.Fields.TOTAL_SUITE_COUNT));
            Boolean inProgress = scr.getField(SuiteRunSchedulerRun.Fields.STATUS).contains("schedule_run_status.inprogress");
            Boolean noSuiteCount = scr.getField(SuiteRunSchedulerRun.Fields.TOTAL_SUITE_COUNT).contains("null");
            return (inProgress && !noSuiteCount) ? true : null;
        });

        // Go to the board and stop the run
        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_PLAN);
        SuiteRunSchedulerRun.Data scr = SuiteRunSchedulerRun.getEntityByName(scheduleNameUft);
        UI.functionalTestingView.scheduledRunsContainer.board.expectVisible();
        UI.functionalTestingView.scheduledRunsContainer.board.getBoardCard(scr).expectVisible();
        UI.functionalTestingView.scheduledRunsContainer.board.getBoardCard(scr).clickStopRun();
        UI.dialogs.clickYes();
        UI.functionalTestingView.scheduledRunsContainer.board.getBoardCard(scr).expectSkippedIcon();
    }

    @Test
    public void sanityTestSkippedSchedule() {
        // Create empty schedule
        createSchedule("Schedule_Empty", "Once");

        // Run schedule and wait for result
        String scheduleRunEmptyId = startSchedule();
        expectScheduleSkipped(scheduleRunEmptyId);
        expectGeneralRunStatusError(scheduleRunEmptyId, "No test suites connected to the schedule were found.");

        // Create schedule with empty test suite
        TestSuite.Data tsEmpty = TestSuite.create("Empty_TS_" + RandomStringUtils.randomAlphabetic(5));
        createSchedule("Schedule_Empty_TS", "Once");
        addSuiteToSchedule(tsEmpty.getId());

        // Run schedule and wait for result
        String scheduleRunEmptyTsId = startSchedule();
        expectScheduleSkipped(scheduleRunEmptyTsId);
        expectGeneralRunStatusError(scheduleRunEmptyTsId, "The suite cannot run because it does not contain any test runs.");
    }

    @Test
    public void sanityTestExecutionMbtDPUnit() {
        // Create TS for MBT
        Unit.Data unit1 = Unit.getEntityByRepositoryPath("'MBT_UFTOne_Samples\\\\Action2:RegisterNewUser'");
        Unit.Data unit2 = Unit.getEntityByRepositoryPath("'MBT_UFTOne_Samples\\\\Action1:LoginSiteUser'");
        Unit.Data unit3 = Unit.getEntityByRepositoryPath("'MBT_UFTOne_Samples\\\\Action3:OpenNewAccount'");
        Unit.Data unit4 = Unit.getEntityByRepositoryPath("'MBT_UFTOne_Samples\\\\Action4:Logout'");
        Model.Data model = createModel("UFT_Data_Population_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), List.of(unit1, unit2, unit3, unit4));

        ModelBasedTest.Data mbtTest = createMbtTest(model);
        navigateByUrlToEntity(mbtTest);
        UI.docViews.docView.selectTab("model-based-test-parameters-tab");

        TestSuite.Data tsMbt = TestSuite.create("MBT_UFT_Data_Population_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionSeleniumMbtDPUnit() {
        // Create TS for Selenium MBT
        Unit.Data unit1 = Unit.getEntityByRepositoryPath("'opentext.mbt.tests.MBT_Selenium_TestNG_Samples:RegisterNewUser'");
        Unit.Data unit2 = Unit.getEntityByRepositoryPath("'opentext.mbt.tests.MBT_Selenium_TestNG_Samples:LoginSiteUser'");
        Unit.Data unit3 = Unit.getEntityByRepositoryPath("'opentext.mbt.tests.MBT_Selenium_TestNG_Samples:OpenNewAccount'");
        Unit.Data unit4 = Unit.getEntityByRepositoryPath("'opentext.mbt.tests.MBT_Selenium_TestNG_Samples:Logout'");
        Model.Data modelSelenium = createModel("Selenium_Data_Population_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), List.of(unit1, unit2, unit3, unit4));

        ModelBasedTest.Data mbtSeleniumTest = createMbtTest(modelSelenium);
        TestSuite.Data tsSeleniumMbt = TestSuite.create("MBT_Selenium_Data_Population_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsSeleniumMbt);
        addTestToTestSuite(mbtSeleniumTest.getName());
        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
        UI.docViews.docView.detailsTab.toolbar.clickSave();

        // Create schedule for Selenium Mbt
        createSchedule(scheduleNameSelMbt, "Once");
        addSuiteToSchedule(tsSeleniumMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionStorage() {
        String apiName = "myApi_" + RandomStringUtils.randomAlphabetic(5);
        String apiSecret = "!qA" + RandomStringUtils.randomAlphabetic(6) + RandomStringUtils.randomNumeric(1);
        String validJar = "valid.jar";
        String invalidJar = "invalid.jar";

        Role.Data role = Role.getEntityByName("CI/CD Integration");
        WorkspaceRole.Data wr = WorkspaceRole.getRole(role.getId(), OCTANE_WORKSPACE_ID);

        ApiAccess.create(apiName, apiSecret, wr.getId());
        ApiAccess.Data apiAccess = ApiAccess.getEntityByName(apiName, List.of(ApiAccess.Fields.NAME, ApiAccess.Fields.ID, ApiAccess.Fields.CLIENT_ID));
        String apiClientId = apiAccess.getField(ApiAccess.Fields.CLIENT_ID);

        restLogout(OCTANE_USERNAME, OCTANE_PASSWORD);
        restLogin(apiClientId, apiSecret);

        CloudRunner.Data crStorage = CloudRunner.getEntityByName(seleniumExecutionStorageRunnerName);
        uploadJar(invalidJar, "0001", crStorage.getId(), apiClientId);
        uploadJar(validJar, "0002", crStorage.getId(), apiClientId);

        GeneralUtils.waitAtMostFor(100000, "Cannot retrieve populated FTE Artifact", () -> {
            try {
                FteArtifact.Data fteArtifactValid = FteArtifact.getEntityByUploaderAndJarFilename(apiClientId, validJar, crStorage.getId());
                return fteArtifactValid.getField(FteArtifact.Fields.FILE_NAME).contains(validJar) ? true : null;
            } catch (Exception e) {
                return null;
            }
        });

        GeneralUtils.waitAtMostFor(100000, "Cannot retrieve populated FTE Artifact", () -> {
            try {
                FteArtifact.Data fteArtifactInvalid = FteArtifact.getEntityByUploaderAndJarFilename(apiClientId, invalidJar, crStorage.getId());
                return fteArtifactInvalid.getField(FteArtifact.Fields.FILE_NAME).contains(invalidJar) ? true : null;
            } catch (Exception e) {
                return null;
            }
        });

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatterUI = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter formatterRest = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateUI = currentDate.format(formatterUI);
        String formattedDateRest = currentDate.format(formatterRest);

        // Validate artifact entities values by Rest
        FteArtifact.Data fteArtifactValid = FteArtifact.getEntityByUploaderAndJarFilename(apiClientId, validJar, crStorage.getId());
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.UPLOAD_DATE).contains(formattedDateRest));
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.FILE_NAME).contains(validJar));
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.UPLOAD_BY).contains(apiClientId));
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.SIZE).contains("28"));
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.VERSION).contains("0002"));
        Assert.assertTrue(fteArtifactValid.getField(FteArtifact.Fields.ERROR_MESSAGE).equals("null"));

        FteArtifact.Data fteArtifactInvalid = FteArtifact.getEntityByUploaderAndJarFilename(apiName, invalidJar, crStorage.getId());
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.UPLOAD_DATE).contains(formattedDateRest));
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.FILE_NAME).contains(invalidJar));
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.UPLOAD_BY).contains(apiClientId));
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.SIZE).contains("16"));
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.VERSION).contains("0001"));
        Assert.assertTrue(fteArtifactInvalid.getField(FteArtifact.Fields.ERROR_MESSAGE).contains("File is not a jar and neither of any accepted file type, rejecting"));

        // Validate artifact entities values by UI
        navigateToDevOpsTab();
        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.TEST_RUNNERS);
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.expectVisible();
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(crStorage.getId()).select();
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickUploadTracker();
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(1).expectTextToContain(formattedDateUI);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(2).expectTextToContain(validJar);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(3).expectTextToContain(apiClientId);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(4).expectTextToContain("27.2 MB");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(5).expectTextToContain("0002");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(6).expectTextToContain("Validation\n");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(1).getCell(7).expectTextToContain("");

        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(1).expectTextToContain(formattedDateUI);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(2).expectTextToContain(invalidJar);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(3).expectTextToContain(apiClientId);
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(4).expectTextToContain("16", "kB");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(5).expectTextToContain("0001");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(6).expectTextToContain("Validation failed\n2\nScan\n3\nDone");
        UI.dialogs.devOpsDialogs.uploadsTrackerDialog.artifactsContainer.grid.getRow(2).getCell(7).expectTextToContain("File is not a jar and neither of any accepted file type, rejecting");

        // Create TS for Execution Storage
        TestSuite.Data tsExecSt = TestSuite.create("Execution_Storage_TS_" + RandomStringUtils.randomAlphabetic(5));
        tsExecSt = TestSuite.getEntityById(tsExecSt.getId(), List.of(TestSuite.Fields.NAME));
        navigateByUrlToEntity(tsExecSt);
        addTestToTestSuite("findGoogleSearchButton");

        // Create schedule for Execution Storage
        createSchedule(scheduleNameExecSt, "Once");
        addSuiteToSchedule(tsExecSt);
        String scheduleRunId = startSchedule();

        if (waitForResults) {
            // Wait for results
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionMBTJenkinsUnit() {
        // Create TS for MBT Jenkins
        Unit.Data unit = Unit.getEntityByRepositoryPath("'UFTOne_FlightLoginAutomationTest\\\\Action1:LoginUnit'");
        Model.Data model = createModel("Jenkins_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtTestRunnerName), List.of(unit));

        ModelBasedTest.Data mbtTest = createMbtTest(model);
        String tsJenkinsRun = "MBT_Jenkins_TS_" + RandomStringUtils.randomAlphabetic(5);
        TestSuite.Data tsMBTJenkins = TestSuite.create(tsJenkinsRun);
        navigateByUrlToEntity(tsMBTJenkins);
        addTestToTestSuite(mbtTest.getName());

        // TODO: Remove the "Select Run Mode and Runner" option once issue 2622030 is resolved.
        UI.docViews.docView.planningTab.selectRunModeAutomatically();
        UI.docViews.docView.planningTab.selectRunner(mbtTestRunnerName);
        UI.docViews.docView.testSuiteContainer.toolbar.clickRunSuite();
        UI.dialogs.runTestDialog.clickRun();
        GeneralUtils.delay(100000);
        Run.Data run = Run.getEntityByName(tsJenkinsRun);
        expectSuiteRunCompleted(run.getId());
    }

    @Test
    public void sanityTestExecutionUFTJenkinsUnit() {
        // Create TS for UFT Jenkins
        String tsJenkinsRun = "UFT_Jenkins_TS_" + RandomStringUtils.randomAlphabetic(5);
        TestSuite.Data tsUFTJenkins = TestSuite.create(tsJenkinsRun);
        navigateByUrlToEntity(tsUFTJenkins);
        addTestToTestSuite("UFTOne_FlightLoginAutomationTest");
        UI.docViews.docView.testSuiteContainer.toolbar.clickRunSuite();
        UI.dialogs.runTestDialog.clickRun();
        GeneralUtils.delay(100000);
        Run.Data run = Run.getEntityByName(tsJenkinsRun);
        expectSuiteRunCompleted(run.getId());
    }

    @Test
    public void sanityTestExecutionIterationMbtUftUnit() {
        // Create TS for MBT
        Unit.Data unit = Unit.getEntityByRepositoryPath("'UFTOneAutomationTests\\\\BrowsersTesFireFox\\\\Action1:FireFox'");
        Model.Data model = createModel("Iteration_UFT_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(getMbtRunnerName()), List.of(unit));

        ModelBasedTest.Data mbtTest = createMbtTest(model);
        navigateByUrlToEntity(mbtTest);

        // Add iterations
        UI.docViews.docView.selectTab("mbt-data-table");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).sendKeys("https://sp.booking.com/index.html?");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(3).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(3).getCell(2).sendKeys("https://sp.booking.com/index.html?");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.toolbar.clickSave();

        TestSuite.Data tsMbt = TestSuite.create("MBT_UFT_Iteration_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void sanityTestExecutionIterationMbtSeleniumUnit() {
        // Create TS for Selenium MBT
        Unit.Data unit = Unit.getEntityByName("reporterSideRespondTest");
        Model.Data model = createModel("Iteration_Selenium_Model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(getMbtRunnerName()), List.of(unit));

        ModelBasedTest.Data mbtTest = createMbtTest(model);
        navigateByUrlToEntity(mbtTest);

        UI.docViews.docView.selectTab("mbt-data-table");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).getCell(2).sendKeys("https://www.google.com/");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(2).getCell(2).sendKeys("https://www.google.com/");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(3).getCell(2).click();
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(3).getCell(2).sendKeys("https://www.google.com/");
        UI.docViews.docView.dataSetsContainer.grid.getSlickRow(1).click();
        UI.docViews.docView.dataSetsContainer.toolbar.clickSave();

        TestSuite.Data tsMbt = TestSuite.create("MBT_Selenium_Iteration_TS_" + RandomStringUtils.randomAlphabetic(5));
        navigateByUrlToEntity(tsMbt);
        addTestToTestSuite(mbtTest.getName());

        // Create schedule for MBT
        createSchedule(scheduleNameMbt, "Once");
        addSuiteToSchedule(tsMbt.getId());

        // Run schedule and wait for result
        String scheduleRunId = startSchedule();
        if (waitForResults) {
            expectScheduleCompleted(scheduleRunId);
            expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
        }
    }

    @Test
    public void deleteWorkingWorkspace() {
        // Delete the working workspace
        Workspace.Data workingWorkspace = Workspace.getEntityByName(OCTANE_WORKSPACE_NAME);
        if (workingWorkspace.getEntityType() != null) {
            Workspace.delete(workingWorkspace, "cleanup");
        }
    }

    private void uploadJar(String filename, String fileversion, String cloudRunnerId, String apiClientId) {
        Map<String, String> params = new HashMap<>();
        params.put("runner_id", cloudRunnerId);
        params.put("filename", filename);
        params.put("fileversion", fileversion);
        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/presigned_url" + constructQueryString(params);
        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);

        String generatedUrl = getEntityResult.getResponseBody();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/java-archive");
        headers.put("Accept", "application/json, text/plain");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Connection", "keep-alive");
        headers.put("x-amz-meta-uploadby", apiClientId);
        headers.put("x-amz-meta-fileversion", fileversion);

        try (InputStream inputStream = SanityUnits.class.getClassLoader().getResourceAsStream(filename)) {
            if (inputStream == null) {
                System.out.println("File not found!");
                return;
            }

            File resourceFile = new File(SanityUnits.class.getClassLoader().getResource(filename).toURI());
            RestCallResult putEntityResult = GeneralUtils.makeRestCall("PUT", generatedUrl, resourceFile, headers);
            String putEntityResponse = putEntityResult.getResponseBody();
            assert putEntityResult.getStatusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean isCiServerConnected() {
        CIServer.Data ciServer = CIServer.getEntityByName("VEFT Execution default OT Cloud CI", List.of(CIServer.Fields.IS_CONNECTED));
        return Boolean.parseBoolean(ciServer.getField("is_connected"));
    }

    private void resetFteRegistrationEnabledParameter() {
        GeneralUtils.setSiteParameter("FTE_REGISTRATION_ENABLED", "false");
        Assert.assertFalse(Boolean.valueOf(GeneralUtils.getSiteParameter("FTE_REGISTRATION_ENABLED")));
        GeneralUtils.setSiteParameter("FTE_REGISTRATION_ENABLED", "true");
        Assert.assertTrue(Boolean.valueOf(GeneralUtils.getSiteParameter("FTE_REGISTRATION_ENABLED")));
        GeneralUtils.delay(10000);
    }

    private void deleteApiKey() {
        navigateByUrl(UrlNavigation.Module.SITE_SPACES);
        UI.siteAdminMainView.spacesForm.grid.expectVisible();
        UI.siteAdminMainView.spacesForm.grid.getRowById(OCTANE_TENANT_ID).expectVisible();
        UI.siteAdminMainView.spacesForm.grid.getRowById(OCTANE_TENANT_ID).select();
        UI.siteAdminMainView.spacesForm.toolbar.clickDeactivateSpace();
        UI.dialogs.clickOk();

        ApiAccess.Data apiAccess = ApiAccess.getEntityByNameAndSpace("FT_DESIGN_service*", OCTANE_TENANT_ID, List.of(ApiAccess.Fields.ID));
        ApiAccess.delete(apiAccess);

        UI.siteAdminMainView.spacesForm.toolbar.clickActivateSpace();
        UI.dialogs.clickOk();
        GeneralUtils.delay(10000);
    }

    private void navigateToDevOpsTab() {
        navigateByUrl(UrlNavigation.Module.WORKSPACES_DEV_OPS);
        UI.spacesMainView.expectVisible();
        UI.dialogs.clickOk();
        UI.spacesMainView.tree.selectWorkspace(OCTANE_WORKSPACE_NAME);
        UI.spacesMainView.tree.expectVisible();
        UI.spacesMainView.workspaceForm.navigateToDevOps();
        UI.spacesMainView.workspaceForm.devopsView.expectVisible();
    }

    private CloudRunner.Data createCloudRunner(String runnerName, String framework, String repoUrl, String packagePath) {
        CloudRunner.Data runner = CloudRunner.getEntityByName(runnerName, List.of(CloudRunner.Fields.NAME, CloudRunner.Fields.SYNC_STATUS, CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK));
        if (runner.getEntityType() == null) {
            // If runner with the same name doesn't exist, create it
            UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickAddCloudRunner();
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.expectVisible();
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.name.sendKeys(runnerName);
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.name.expectValue(runnerName);
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.framework.setValue(framework);
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.framework.expectValue(framework);

            // Check Test Connection button
            if (repoUrl != null) {
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.url.sendKeys(repoUrl);
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.url.expectValue(repoUrl);
                GeneralUtils.waitAtMostFor(100000, "Test connection was not successful.\nExpected message: Connection succeeded with warning\nActual message: " + UI.dialogs.devOpsDialogs.cloudRunnerDialog.getTestConnectionMessage(), () -> {
                    UI.dialogs.devOpsDialogs.cloudRunnerDialog.testConnectionButton.click();
                    String connectionMessage = UI.dialogs.devOpsDialogs.cloudRunnerDialog.getTestConnectionMessage();
                    return connectionMessage.contains("Connection succeeded with warning") ? true : null;
                });
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.testConnectionMessage.expectTextToContain("Connection succeeded");
            } else {
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.selectExecutionStorage();
            }

            // Set package path
            if (packagePath != null) {
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.setPackagePath(packagePath, 0);
            }

            // Close the dialog
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.clickSave();
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.expectNotVisible();

            // Get the id of the newly created runner
            String id = UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).getId();
            runner = CloudRunner.getEntityById(id, List.of(CloudRunner.Fields.NAME, CloudRunner.Fields.SYNC_STATUS, CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK));

        }
        return runner;
    }

    private void syncRunner(CloudRunner.Data runner) {
        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(runner.getId()).clickId();
        UI.dialogs.devOpsDialogs.cloudRunnerDialog.expectVisible();

        GeneralUtils.waitAtMostFor(100000, "Sync was not successful.", () -> {
            UI.dialogs.devOpsDialogs.cloudRunnerDialog.syncNowButton.expectVisible();
            if (!UI.dialogs.devOpsDialogs.cloudRunnerDialog.syncNowButton.isDisabled()) {
                UI.dialogs.devOpsDialogs.cloudRunnerDialog.syncNowButton.click();
            } else {
                Assert.fail("'Sync now' button is disabled");
            }
            String syncMessage = UI.dialogs.devOpsDialogs.cloudRunnerDialog.getSyncNowMessage();
            return syncMessage.contains("Sync process started") ? true : null;
        });

        UI.dialogs.devOpsDialogs.cloudRunnerDialog.clickSave();
        UI.dialogs.devOpsDialogs.cloudRunnerDialog.expectNotVisible();
    }

    private boolean isSyncSuccessful(String crId) {
        CloudRunner.Data cr = CloudRunner.getEntityById(crId, List.of(CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK, CloudRunner.Fields.SYNC_STATUS));
        return cr.getField(CloudRunner.Fields.SYNC_STATUS).contains("sync_status.success");
    }

    private Model.Data createModel(String modelName, String modelFolder, List<Unit.Data> units) {
        ModelFolder.Data mbtFolder = ModelFolder.getEntityByName(modelFolder);
        Model.Data model = Model.create(modelName, mbtFolder);

        List<Object> transitions = new ArrayList<>();
        // Check if the units list is not empty
        if (!units.isEmpty()) {
            // Add the start transition
            transitions.add(Arrays.asList(MBTUtils.DiagramNodes.START_NODE, units.get(0)));

            // Add intermediate transitions
            for (int i = 0; i < units.size() - 1; i++) {
                transitions.add(Arrays.asList(units.get(i), units.get(i + 1)));
            }

            // Add the end transition
            transitions.add(Arrays.asList(units.get(units.size() - 1), MBTUtils.DiagramNodes.END_NODE));
        }

        MBTUtils.createDiagram(model, null, units, null, 0, 0, 1, transitions);

        return model;
    }

    private ModelBasedTest.Data createMbtTest(Model.Data model) {
        navigateByUrlToEntity(model);
        UI.docViews.docView.selectTab("mbt-path-tab");
        UI.docViews.docView.pathsContainer.grid.getRow(1).select();

        if (!UI.docViews.docView.pathsContainer.grid.getRow(1).getCell("covered_by_test").getText().equals("")) {
            String testId = UI.docViews.docView.pathsContainer.grid.getRow(1).getCell("covered_by_test").getChildElement(Locator.cssSelector("a")).getText();
            return ModelBasedTest.getEntityById(testId, List.of(ModelBasedTest.Fields.NAME, ModelBasedTest.Fields.ID));
        } else {
            UI.docViews.docView.pathsContainer.toolbar.clickGenerateTest();
            UI.dialogs.modelTestGeneratorDialog.clickGenerate();
            UI.notifications.success.expectVisible();
            return ModelBasedTest.getEntityByName(model.getName() + "_00001", List.of(ModelBasedTest.Fields.NAME, ModelBasedTest.Fields.ID));
        }
    }

    private void expectScheduleCompleted(String scheduleRunId) {
        String status = waitScheduleInProgress(1200000, scheduleRunId);
        Assert.assertTrue(status.contains("Completed"), "Schedule run expected status: Completed. Actual status: " + status);
    }

    private void expectSuiteRunCompleted(String suiteRunId) {
        String status = GeneralUtils.waitSuiteRunInProgress(1200000, suiteRunId);
        Assert.assertTrue(status.contains("Passed"), "Suite run expected status: Passed. Actual status: " + status);
    }

    private void expectScheduleSkipped(String scheduleRunId) {
        String status = waitScheduleInProgress(1200000, scheduleRunId);
        Assert.assertTrue(status.contains("Skipped"), "Schedule run expected status: Skipped. Actual status: " + status);
    }

    private void expectGeneralRunStatus(String scheduleRunId, int passed, int failed, int skipped) {
        SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityById(scheduleRunId, List.of(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS));
        JSONObject generalRunStatusJson = new JSONObject(suiteRun.getField(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS));
        int actualPassed = (int) generalRunStatusJson.get("list_node.run_native_status.passed");
        int actualFailed = (int) generalRunStatusJson.get("list_node.run_native_status.failed");
        int actualSkipped = (int) generalRunStatusJson.get("list_node.run_native_status.skipped");

        boolean correctStatus = false;
        if ((passed == actualPassed) && (failed == actualFailed) && skipped == actualSkipped) {
            correctStatus = true;
        }

        Assert.assertTrue(correctStatus, "General run status not as expected. Expected: " + passed + " passed, " + failed + " failed, " + skipped + " skipped. Actual: " + actualPassed + " passed, " + actualFailed + " failed, " + actualSkipped + " skipped.");
    }

    private void expectGeneralRunStatusError(String scheduleRunId, String expectedErrorMessage) {
        SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityById(scheduleRunId, List.of(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS, SuiteRunSchedulerRun.Fields.ERROR_MESSAGE));

        String generalRunStatus = suiteRun.getField(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS);
        String errorMessage = suiteRun.getField(SuiteRunSchedulerRun.Fields.ERROR_MESSAGE);

        Assert.assertTrue(generalRunStatus.equals("null"), "General run status not as expected. Expected: null. Actual: " + generalRunStatus);
        Assert.assertTrue(errorMessage.contains(expectedErrorMessage), "Error message is not as expected. Expected: " + expectedErrorMessage + ". Actual: " + errorMessage);
    }

    private String waitScheduleInProgress(int waitTime, String scheduleName) {
        return GeneralUtils.waitAtMostFor(waitTime, "Schedule is still in progress.", () -> {
            SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityById(scheduleName, List.of(SuiteRunSchedulerRun.Fields.STATUS));
            JSONObject statusJson = new JSONObject(suiteRun.getField(SuiteRunSchedulerRun.Fields.STATUS));
            String status = (String) statusJson.get("name");

            return status.contains("In progress") ? null : status.trim();
        });
    }

    private String trimParenthesis(String text) {
        return text.replaceAll("[()]", "");
    }

    private void addTestToTestSuite(String... testsName) {
        UI.docViews.docView.testsContainer.toolbar.clickAddExistingTests();
        UI.dialogs.addExistingTestsDialog.grid.expectVisible();

        for (String testName : testsName) {
            GeneralUtils.waitAtMostFor(150000, "Cannot retrieve test " + testName, () -> ManualTest.getEntityByName(testName, List.of(ManualTest.Fields.NAME)).getName());
            ManualTest.Data test = ManualTest.getEntityByName(testName);
            UI.dialogs.addExistingTestsDialog.toolbar.clickRefresh();
            UI.dialogs.addExistingTestsDialog.toolbar.search(testName);
            UI.dialogs.addExistingTestsDialog.grid.getRowById(test.getId()).select();
            UI.dialogs.addExistingTestsDialog.clickAdd();
        }

        UI.dialogs.addExistingTestsDialog.clickClose();
        UI.dialogs.addExistingTestsDialog.expectNotVisible();
    }

    private void setMachineTemplateAndRunMode(String machineTemplate, String runMode) {
        UI.docViews.docView.selectTab("details");
        UI.docViews.docView.detailsTab.expectVisible();
        UI.docViews.docView.detailsTab.setMachineTemplate(machineTemplate);
        UI.docViews.docView.detailsTab.setExecutionMode(runMode);
    }

    private void createSchedule(String scheduleName, String runMode) {
        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
        UI.functionalTestingView.schedulesContainer.expectVisible();
        UI.functionalTestingView.schedulesContainer.toolbar.clickAdd();

        UI.dialogs.addScheduleDialog.name.sendKeys(scheduleName);
        UI.dialogs.addScheduleDialog.runMode.setValue(runMode);
        UI.dialogs.addScheduleDialog.startTime.setValue(LocalDateTime.now().plusMinutes(1));

        if (runMode == "Recurring") {
            UI.dialogs.addScheduleDialog.repeatEvery.sendKeys("4");
        }

        UI.dialogs.addScheduleDialog.clickAddAndEdit();
    }

    private void addSuiteToSchedule(String testSuiteId) {
        UI.docViews.docView.fteTestSuitesContainer.toolbar.clickAddTestSuites();
        UI.dialogs.addExistingTestsDialog.grid.expectVisible();
        UI.dialogs.addExistingTestsDialog.grid.getRowById(testSuiteId).select();
        UI.dialogs.addExistingTestsDialog.clickAddAndClose();
        UI.dialogs.addExistingTestsDialog.expectNotVisible();
    }

    private void addSuiteToSchedule(TestSuite.Data testSuite) {
        UI.docViews.docView.fteTestSuitesContainer.toolbar.clickAddTestSuites();
        UI.dialogs.addExistingTestsDialog.toolbar.search(testSuite.getName());
        UI.dialogs.addExistingTestsDialog.grid.expectVisible();
        UI.dialogs.addExistingTestsDialog.grid.getRowById(testSuite.getId()).select();
        UI.dialogs.addExistingTestsDialog.clickAdd();
        UI.dialogs.addExistingTestsDialog.toolbar.clearSearch();
        UI.dialogs.addExistingTestsDialog.clickClose();
        UI.dialogs.addExistingTestsDialog.expectNotVisible();
    }

    private String startSchedule() {
        UI.docViews.docView.selectTab("fte-schedule-suites-runs-tab");
        UI.docViews.docView.scheduleRunsContainer.grid.expectVisible();
        UI.docViews.docView.scheduleRunsContainer.grid.getRow(1).select();
        UI.docViews.docView.scheduleRunsContainer.grid.getRow(1).clickId();
        UI.docViews.docView.detailsTab.expectVisible();
        UI.docViews.docView.detailsTab.setDropdownField("status", "In progress");
        UI.docViews.docView.detailsTab.toolbar.clickSave();
        UI.notifications.success.expectVisible();
        return UI.docViews.docView.getEntityId();
    }

    private void verifyReport(TestSuite.Data testSuite, List<String> expectedReportText) {
        navigateByUrlToEntity(testSuite);
        UI.docViews.docView.selectTab("suite_runs");
        UI.docViews.docView.runsContainer.grid.getRow(1).clickId();
        UI.docViews.docView.selectTab("test-suite-run-report");
        for (String text : expectedReportText) {
            UI.docViews.docView.suiteReportContainer.expectTextToContain(text);
        }
    }

    private void addCIJenkins(String ciJenkinsName, String jenkinsURL) {
        navigateToDevOpsTab();
        if (!jenkinsCiServerExist()) {
            UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.toolbar.expectVisible();
            UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.toolbar.clickAdd();
            UI.dialogs.devOpsDialogs.addCIServerDialog.setCIName(ciJenkinsName);
            UI.dialogs.devOpsDialogs.addCIServerDialog.selectCIServer(jenkinsURL);
            UI.dialogs.devOpsDialogs.addCIServerDialog.clickAdd();
        }
    }

    private void addTestRunner(String runnerName, String ciServer, String framework, String repository) {
        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.TEST_RUNNERS);
        if (!mbtTestRunnerExist(runnerName)) {
            UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.expectVisible();
            UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickAddTestRunner();
            UI.dialogs.devOpsDialogs.addTestRunnerDialog.ciServer.setValue(ciServer);
            UI.dialogs.devOpsDialogs.addTestRunnerDialog.testRunnerName(runnerName);
            UI.dialogs.devOpsDialogs.addTestRunnerDialog.framework.setValue(framework);
            UI.dialogs.devOpsDialogs.addTestRunnerDialog.repository.sendKeys(repository);
            GeneralUtils.waitAtMostFor(100000, "Test connection was not successful.\nExpected message: Connection succeeded with warning\nActual message: " + UI.dialogs.devOpsDialogs.addTestRunnerDialog.getTestConnectionMessage(), () -> {
                UI.dialogs.devOpsDialogs.addTestRunnerDialog.testConnectionButton.click();
                String connectionMessage = UI.dialogs.devOpsDialogs.addTestRunnerDialog.getTestConnectionMessage();
                return connectionMessage.contains("Connection succeeded with warning") ? true : null;
            });
            UI.dialogs.devOpsDialogs.addTestRunnerDialog.clickSave();
        }
    }

    private Boolean jenkinsCiServerExist() {
        CIServer.Data ciServer = CIServer.getEntityByName(ciJenkinsName, List.of(CIServer.Fields.NAME, CIServer.Fields.IS_CONNECTED));
        return Boolean.parseBoolean(ciServer.getField("is_connected"));
    }

    private Boolean mbtTestRunnerExist(String runnerName) {
        CloudRunner.Data testRunner = CloudRunner.getEntityByName(runnerName, List.of(CloudRunner.Fields.NAME, CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK));
        return Boolean.parseBoolean(testRunner.getField("name"));
    }

    private String getMbtRunnerName() {
        if (ListNode.getEntityByName("MBT \\(UFT\\)").getEntityType() != null) {
            return mbtRunnerNameOld;
        } else return mbtRunnerName;
    }
}