package tests;//package com.ot.automation.tests;
//
//import com.ot.automation.framework.octane.framework.*;
//import com.ot.automation.framework.octane.framework.Entities.*;
//import com.ot.automation.framework.octane.ui.spaces.DevOpsView;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.json.JSONObject;
//import org.openqa.selenium.By;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Test;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class SanityV2 extends BaseAutomationTest {
//    static RemoteWebDriver driver;
//    static WebDriverWait waiter;
//    private final static String repoUrl = "https://github.com/mbtuser/mbtuser.git";
//    private final static String seleniumRepoUrl = "https://github.com/mbtuser/Selenium-demo.git";
//    private final static String seleniumRepoUrlOld = "https://github.com/onentwoo/aob.git";
//    private final static String uftRunnerName = "UFT";
//    private final static String mbtRunnerName = "MBT (UFT)";
//    private final static String seleniumRunnerName = "Selenium (TestNG)";
//    private final static String seleniumMbtRunnerName = "MBT (Selenium TestNG)";
//    private final static String scheduleNameMbt = "myScheduleMbt_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameUft = "myScheduleUft_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameUftRec = "myScheduleUftRecurring_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameMobile = "myScheduleMobile_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameBrowsers = "myScheduleBrowsers_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameSel = "myScheduleSelenium_" + RandomStringUtils.randomAlphabetic(5);
//    private final static String scheduleNameSelMbt = "myScheduleSeleniumMbt_" + RandomStringUtils.randomAlphabetic(5);
//
//    @BeforeSuite
//    void init() {
//        setupDriver(driver, waiter);
//        initializeEnv();
//
//    }
//
////    @Test
////    @Parameters({"tenantName"})
////    public void initializeTest(String tenantName) {
////        BaseAutomationTest.setDriver(driver, waiter);
////        initializeDriver(tenantName);
////    }
//
//
//    //    @Test
////    public void createTenantScript() throws IOException {
////        GeneralUtils.createTenant("Integration Master Dev", "FT_BUNDLE", "FTE by script");
////    }
////
////    @Test
////    public void createTenantUnit() {
//////        loginBackOffice("mqm_rnd@hpe.com", "JustK33pGoing!");
////
////        try {
////            GeneralUtils.waitAtMostFor(500000, "Navigate failed", () -> {
////                String url = "https://bo.ct-shared.saas.microfocus.com/home/bl/desktop.html?TENANTID=1#/ofs/orders/subscriptions/new/0";
////                navigateByUrl(url);
////
////                if (!BaseAutomationTest.driver.getCurrentUrl().contains(url)) {
////                    return null;
////                }
////                return true;
////            });
////
////        } catch (Exception e) {
////            System.err.println("Exception occurred during navigate: ");
////            e.printStackTrace();
////        }
////
////        BO.ipPage.expectVisible();
////        BO.ipPage.clickGeneralTab();
////        BO.ipPage.generalTab.expectVisible();
////
////        BO.ipPage.generalTab.clickAssignAccountButton();
////        BO.dialogsBO.assignAccountDialog.selectAccount("MQM AWS Integration Branch Prod");
////
////        BO.ipPage.generalTab.selectReasonType("SaaS Flex Order");
////        BO.ipPage.generalTab.setReasonDetails("any");
////        BO.ipPage.generalTab.selectProduct("ALM Octane");
////        BO.ipPage.generalTab.selectCSM("None");
////        BO.ipPage.generalTab.setApprovedBy("mqm_rnd@hpe.com");
////        BO.ipPage.generalTab.setAttachment("C:\\Windows\\System32\\ActiveHours.png");
////
////        String sub1name = "VSM_Quality_FT_CC_User_VE";
////        String sub2name = "FT_Digital_Lab_Concurrent_Devices_VE";
////        String sub3name = "FT_Execution_Concurrent_Runs_VE";
////        Integer sub1quantity = 10;
////        Integer sub2quantity = 1;
////        Integer sub3quantity = 3;
////
////        BO.ipPage.clickSubscriptionTermsTab();
////        BO.ipPage.subscriptionsTab.setSpecialInstructions("none");
////        BO.ipPage.subscriptionsTab.addNewSubscription(sub1quantity, sub1name, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
////        BO.ipPage.subscriptionsTab.addNewSubscription(sub2quantity, sub2name, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
////        BO.ipPage.subscriptionsTab.addNewSubscription(sub3quantity, sub3name, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
////
////        BO.ipPage.clickTenantsTab();
////
////        String farm = "MQM AWS Integration Master Dev: America - North";
////        String timezone = "(GMT +2) Europe/Bucharest)";
////
////        BO.ipPage.tenantsTab.addTenant(OCTANE_TENANT_NAME + " " + RandomStringUtils.randomAlphabetic(5), farm, timezone, "Basic", "Dev", List.of(sub1quantity, sub2quantity, sub3quantity), List.of(sub1name, sub2name, sub3name));
////        BO.ipPage.tenantsTab.clickApproveSubmit();
////        GeneralUtils.delay(2000);
////    }
//
//    @Test
//    public void sanityTestDevopsTab() {
//        // Navigate to Spaces - DevOps tab - CI Servers
//        navigateByUrl(UrlNavigation.Module.SPACES);
//        UI.spacesMainView.expectVisible();
//
//        UI.dialogs.clickOk();
//
//        UI.spacesMainView.tree.selectWorkspace(OCTANE_WORKSPACE_NAME);
//        UI.spacesMainView.tree.expectVisible();
//        UI.spacesMainView.workspaceForm.navigateToDevOps();
//        UI.spacesMainView.workspaceForm.devopsView.expectVisible();
//        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.CI_SERVER);
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.toolbar.clickRefresh();
//
//        // Validate CI Server is connected (Rest)
//        CIServer.Data ciServer = CIServer.getEntityByName("VEFT Execution default OT Cloud CI", List.of(CIServer.Fields.IS_CONNECTED));
//        String isConnected = ciServer.getField("is_connected");
//        Assert.assertTrue(Boolean.parseBoolean(isConnected), "CI Server is_connected field value is " + isConnected);
//        String ciServerId = ciServer.getId();
//
//        // Validate CI Server is connected (UI)
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.expectVisible();
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).expectVisible();
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.NAME).expectVisible();
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.NAME).expectTextToContain("VEFT Execution default OT Cloud CI");
//        UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.IS_CONNECTED).expectVisible();
//        BaseElement successIcon = new BaseElement(By.cssSelector("[href*='#f-status-passed']"), UI.spacesMainView.workspaceForm.devopsView.ciServersContainer.grid.getRowById(ciServerId).getCell(CIServer.Fields.IS_CONNECTED));
//        successIcon.expectVisible();
//
//        //Add release
//        Release.Data release = Release.create("Release_" + RandomStringUtils.randomAlphabetic(5), LocalDateTime.now(), LocalDateTime.now().plusDays(30), 7);
//
//        // Validate codeless runner was created automatically
//        UI.spacesMainView.workspaceForm.devopsView.selectTab(DevOpsView.Tabs.TEST_RUNNERS);
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.expectVisible();
////        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).getCell("framework").expectTextToContain("Codeless");
////        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).getCell("ci_server").expectTextToContain("VEFT Execution default OT Cloud CI");
////        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).getCell("name").expectTextToContain("Codeless Runner");
//
//        // Create test runners
////        String cr1id = createCloudRunner(uftRunnerName, repoUrl);
////        String cr2id = createCloudRunner(mbtRunnerName, repoUrl);
//        String cr3id = createCloudRunner(seleniumRunnerName, seleniumRepoUrl);
//        String cr4id = createCloudRunner(seleniumMbtRunnerName, seleniumRepoUrl);
//
//        // Validate sync status for each
////        GeneralUtils.waitAtMostFor(60000, "Sync status for " + uftRunnerName + " is not as expected.", () -> isSyncSuccessful(cr1id) ? true : null);
////        GeneralUtils.waitAtMostFor(60000, "Sync status for " + mbtRunnerName + " is not as expected.", () -> isSyncSuccessful(cr2id) ? true : null);
//        GeneralUtils.waitAtMostFor(60000, "Sync status for " + seleniumRunnerName + " is not as expected.", () -> isSyncSuccessful(cr3id) ? true : null);
//        GeneralUtils.waitAtMostFor(60000, "Sync status for " + seleniumMbtRunnerName + " is not as expected.", () -> isSyncSuccessful(cr4id) ? true : null);
//
//        // Validate sync status in UI
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickRefresh();
////        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr1id).getCell("sync_status").expectTextToContain("Success");
////        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr2id).getCell("sync_status").expectTextToContain("Success");
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr3id).getCell("sync_status").expectTextToContain("Success");
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRowById(cr4id).getCell("sync_status").expectTextToContain("Success");
//    }
//
//    @Test
//    public void sanityTestExecutionUft() {
//        // Create TS for UFT
//        TestSuite.Data tsUft = TestSuite.create("tsUFT_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsUft);
//        addTestToTestSuite("MBT_Demo_Google_Search_Execuiton_Pass");
//
//        // Create schedule for UFT
//        String scheduleIdUft = createSchedule(scheduleNameUft, "Once");
//        addSuiteToSchedule(tsUft.getId());
//        String scheduleRunId = startSchedule();
//
//        // Create schedule for UFT recurring
//        String scheduleIdRec = createSchedule(scheduleNameUftRec, "Recurring");
//        addSuiteToSchedule(tsUft.getId());
//        String recurringScheduleRunId = startSchedule();
//
//        // Wait for results
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
//
//        expectScheduleCompleted(recurringScheduleRunId);
//        expectGeneralRunStatus(recurringScheduleRunId, 1, 0, 0);
//
//        // Download report
//        navigateByUrlToEntity(tsUft);
//
//        UI.docViews.docView.selectTab("suite_runs");
//        UI.docViews.docView.runsContainer.grid.getRow(1).clickId();
//
//        UI.docViews.docView.selectTab("run");
//        UI.docViews.docView.suiteRunsContainer.grid.getRow(1).clickId();
//
//        UI.docViews.docView.detailsTab.expectVisible();
//        UI.docViews.docView.detailsTab.toolbar.clickDownloadTestRunReport();
//        UI.notifications.success.expectTextToContain("Test run report successfully downloaded");
//    }
//
//    @Test
//    public void sanityTestExecutionMbt() {
//        // Create TS for MBT
//        Model.Data model = createModel("model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), "Login");
//        ModelBasedTest.Data mbtTest = createMbtTest(model);
//        //TODO add iterations
//
//        TestSuite.Data tsMbt = TestSuite.create("tsMBT_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsMbt);
//        addTestToTestSuite(mbtTest.getName());
//
//        // Create schedule for MBT
//        String scheduleIdMbt = createSchedule(scheduleNameMbt, "Once");
//        addSuiteToSchedule(tsMbt.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
//    }
//
////    @Test
////    public void sanityTestExecutionMbtParameters() {
////        // Create TS for MBT
////        Model.Data model = createModel("model_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(mbtRunnerName), "BuyStock");
////        ModelBasedTest.Data mbtTest = createMbtTest(model);
////
//////        UI.docViews.docView.selectTab("mbt-tests-tab");
//////        UI.docViews.docView.testsContainerMbt.grid.getRow(1).clickId();
//////        UI.docViews.docView.selectTab("mbt-data-table");
//////        UI.docViews.docView.dataSetsContainer.grid.getRow(2).getCell(2).sendKeys("bla");
////        //TODO add iterations and parameters
////
////        TestSuite.Data tsMbt = TestSuite.create("tsMBT_" + RandomStringUtils.randomAlphabetic(5));
////        navigateByUrlToEntity(tsMbt);
////        addTestToTestSuite(mbtTest.getName());
////
////        // Create schedule for MBT
////        String scheduleIdMbt = createSchedule(scheduleNameMbt, "Once");
////        addSuiteToSchedule(tsMbt.getId());
////
////        // Run schedule and wait for result
////        String scheduleRunId = startSchedule();
////        expectScheduleCompleted(scheduleRunId);
////        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
////    }
//
//    @Test
//    public void sanityTestExecutionMobile() {
//        // Create TS for Mobile
//        TestSuite.Data tsMobile = TestSuite.create("tsMobile_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsMobile);
//        addTestToTestSuite("iOS_native_InApp", "Android_Native_InApp");
//
//        // Create schedule for mobile
//        String scheduleIdMobile = createSchedule(scheduleNameMobile, "Once");
//        addSuiteToSchedule(tsMobile.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 2, 0, 0);
//    }
//
//    @Test
//    public void sanityTestExecutionBrowsers() {
//        // Create TS for Browsers
//        TestSuite.Data tsBrowsers = TestSuite.create("tsBrowsers_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsBrowsers);
//        addTestToTestSuite("BrowsersTesFireFox", "BrowsersTestEdge");
////        setMachineTemplateAndRunMode("UFTOne 24.2, Firefox 126 (Default)", "Parallel");
//        setMachineTemplateAndRunMode("UFTOne 24.2, Chrome 126 (Default)", "Parallel");
//        UI.docViews.docView.detailsTab.toolbar.clickSave();
//
//        // Create schedule for browsers
//        String scheduleIdBrowsers = createSchedule(scheduleNameBrowsers, "Once");
//        addSuiteToSchedule(tsBrowsers.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 2, 0, 0);
//    }
//
//    @Test
//    public void sanityTestExecutionSelenium() {
//        // Create TS for Selenium
//        TestSuite.Data tsSelenium = TestSuite.create("tsSelenium_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsSelenium);
//        addTestToTestSuite("AOBLogin");
//        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
//        UI.docViews.docView.detailsTab.toolbar.clickSave();
//
//        // Create schedule for Selenium
//        String scheduleIdSel = createSchedule(scheduleNameSel, "Once");
//        addSuiteToSchedule(tsSelenium.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
//    }
//
//    @Test
//    public void sanityTestExecutionSeleniumMbt() {
//        // Create TS for Selenium MBT
//        Model.Data modelSelenium = createModel("modelSelenium_" + RandomStringUtils.randomAlphabetic(5), trimParenthesis(seleniumMbtRunnerName), "AOBRegistration");
//        ModelBasedTest.Data mbtSeleniumTest = createMbtTest(modelSelenium);
//        TestSuite.Data tsSeleniumMbt = TestSuite.create("tsSeleniumMBT_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsSeleniumMbt);
//        addTestToTestSuite(mbtSeleniumTest.getName());
//        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
//        UI.docViews.docView.detailsTab.toolbar.clickSave();
//
//        // Create schedule for Selenium Mbt
//        String scheduleIdSelMbt = createSchedule(scheduleNameSelMbt, "Once");
//        addSuiteToSchedule(tsSeleniumMbt.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
//        expectScheduleCompleted(scheduleRunId);
//        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);
//
//    }
//
//    private boolean isSyncSuccessful(String crId) {
//        CloudRunner.Data cr = CloudRunner.getEntityById(crId, List.of(CloudRunner.Fields.CI_SERVER, CloudRunner.Fields.FRAMEWORK, CloudRunner.Fields.SYNC_STATUS));
//        return cr.getField(CloudRunner.Fields.SYNC_STATUS).contains("sync_status.success");
//    }
//
//    private Model.Data createModel(String modelName, String modelFolder, String unitName) {
//        ModelFolder.Data mbtFolder = ModelFolder.getEntityByName(modelFolder);
//        Model.Data model = Model.create(modelName, mbtFolder);
//        Unit.Data unit = Unit.getEntityByName(unitName);
//
//        List<Unit.Data> units = Arrays.asList(unit);
//        List<Object> transitions = Arrays.asList(
//                Arrays.asList(MBTUtils.DiagramNodes.START_NODE, unit),
//                Arrays.asList(unit, MBTUtils.DiagramNodes.END_NODE));
//        MBTUtils.createDiagram(model, null, units, null, 0, 0, 1, transitions);
//
//        return model;
//    }
//
//    private ModelBasedTest.Data createMbtTest(Model.Data model) {
//        navigateByUrlToEntity(model);
//        UI.docViews.docView.selectTab("mbt-path-tab");
//        UI.docViews.docView.pathsContainer.grid.getRow(1).select();
//        UI.docViews.docView.pathsContainer.toolbar.clickGenerateTest();
//
//        UI.dialogs.modelTestGeneratorDialog.clickGenerate();
//        UI.notifications.success.expectVisible();
//        ModelBasedTest.Data mbtTest = ModelBasedTest.getEntityByName(model.getName() + "_00001", List.of(ModelBasedTest.Fields.NAME, ModelBasedTest.Fields.ID));
//        return mbtTest;
//    }
//
//    private void expectScheduleCompleted(String scheduleRunId) {
//        String status = waitScheduleInProgress(1200000, scheduleRunId);
//        Assert.assertTrue(status.contains("Completed"), "Schedule run expected status: Completed. Actual status: " + status);
//    }
//
//    private void expectGeneralRunStatus(String scheduleRunId, int passed, int failed, int skipped) {
//        SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityById(scheduleRunId, List.of(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS));
//
////        SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityByName(scheduleName, List.of(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS));
//        JSONObject generalRunStatusJson = new JSONObject(suiteRun.getField(SuiteRunSchedulerRun.Fields.GENERAL_RUN_STATUS));
//        int actualPassed = (int) generalRunStatusJson.get("list_node.run_native_status.passed");
//        int actualFailed = (int) generalRunStatusJson.get("list_node.run_native_status.failed");
//        int actualSkipped = (int) generalRunStatusJson.get("list_node.run_native_status.skipped");
//
//        boolean correctStatus = false;
//        if ((passed == actualPassed) && (failed == actualFailed) && skipped == actualSkipped) {
//            correctStatus = true;
//        }
//
//        Assert.assertTrue(correctStatus, "General run status not as expected. Expected: " + passed + " passed, " + failed + " failed, " + skipped + " skipped. Actual: " + actualPassed + " passed, " + actualFailed + " failed, " + actualSkipped + " skipped.");
//    }
//
//    private String waitScheduleInProgress(int waitTime, String scheduleName) {
//        return GeneralUtils.waitAtMostFor(waitTime, "Schedule is still in progress.", () -> {
//            SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityById(scheduleName, List.of(SuiteRunSchedulerRun.Fields.STATUS));
//
////            SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityByName(scheduleName, List.of(SuiteRunSchedulerRun.Fields.STATUS));
//            JSONObject statusJson = new JSONObject(suiteRun.getField(SuiteRunSchedulerRun.Fields.STATUS));
//            String status = (String) statusJson.get("name");
//            if (status.contains("In progress")) {
//                return null;
//            } else {
//                return status.trim();
//            }
//        });
//    }
//
//    private void waitScheduleCompleted(int waitTime, List<String> scheduleNames) {
//        // Create a boolean list for completed (initialized as false) for each schedule in the list
//        List<Boolean> schedulesCompleted = new ArrayList<>();
//        for (int i = 0; i < scheduleNames.size(); i++) {
//            schedulesCompleted.add(false);
//        }
//
//        GeneralUtils.waitAtMostFor(waitTime, "Status is not as expected.", () -> {
//            boolean allCompleted = true;
//            for (int i = 0; i < scheduleNames.size(); i++) {
//                if (!schedulesCompleted.get(i)) {
//                    SuiteRunSchedulerRun.Data suiteRun = SuiteRunSchedulerRun.getEntityByName(scheduleNames.get(i), List.of(SuiteRunSchedulerRun.Fields.STATUS));
//                    String status = suiteRun.getField(SuiteRunSchedulerRun.Fields.STATUS);
//                    if (status.contains("Completed")) {
//                        schedulesCompleted.set(i, true);
//                    } else {
//                        allCompleted = false;
//                    }
//                }
//            }
//            return allCompleted ? true : null;
//        });
//    }
//
//    private String trimParenthesis(String text) {
//        return text.replaceAll("[()]", "");
//    }
//
//    private String createCloudRunner(String framework, String repoUrl) {
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.toolbar.clickAddCloudRunner();
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.expectVisible();
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.name.sendKeys(trimParenthesis(framework));
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.name.expectValue(trimParenthesis(framework));
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.framework.setValue(framework);
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.framework.expectValue(framework);
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.url.sendKeys(repoUrl);
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.url.expectValue(repoUrl);
//
//        GeneralUtils.waitAtMostFor(100000, "Test connection was not successful.\nExpected message: Connection succeeded with warning\nActual message: " + UI.dialogs.devOpsDialogs.addCloudRunnerDialog.getTestConnectionMessage(), () -> {
//            UI.dialogs.devOpsDialogs.addCloudRunnerDialog.testConnectionButton.click();
//            String connectionMessage = UI.dialogs.devOpsDialogs.addCloudRunnerDialog.getTestConnectionMessage();
//            if (connectionMessage.contains("Connection succeeded with warning")) {
//                return true;
//            }
//            return null;
//        });
//
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.testConnectionMessage.expectTextToContain("Connection succeeded");
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.clickSave();
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.expectNotVisible();
//        String id = UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).getId();
//        UI.spacesMainView.workspaceForm.devopsView.testRunnersContainer.grid.getRow(1).clickId();
//
//
//        GeneralUtils.waitAtMostFor(100000, "Sync was not successful.", () -> {
//            UI.dialogs.devOpsDialogs.addCloudRunnerDialog.syncNowButton.expectVisible();
//            UI.dialogs.devOpsDialogs.addCloudRunnerDialog.syncNowButton.click();
//            String syncMessage = UI.dialogs.devOpsDialogs.addCloudRunnerDialog.getSyncNowMessage();
//
//            if (syncMessage.contains("Sync process started")) {
//                return true;
//            }
//            return null;
//        });
//
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.syncNowMessage.expectTextToContain("Sync process started");
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.clickSave();
//        UI.dialogs.devOpsDialogs.addCloudRunnerDialog.expectNotVisible();
//
//        return id;
//    }
//
//    private void addTestToTestSuite(String... testsName) {
//        UI.docViews.docView.testsContainer.toolbar.clickAddExistingTests();
//        UI.dialogs.addExistingTestsDialog.grid.expectVisible();
//
//        for (String testName : testsName) {
//            ManualTest.Data test = ManualTest.getEntityByName(testName);
//            UI.dialogs.addExistingTestsDialog.toolbar.search(testName);
//            UI.dialogs.addExistingTestsDialog.grid.getRowById(test.getId()).select();
//            UI.dialogs.addExistingTestsDialog.clickAdd();
//        }
//
//        UI.dialogs.addExistingTestsDialog.clickClose();
//        UI.dialogs.addExistingTestsDialog.expectNotVisible();
//    }
//
//    private void addTestToTestSuite(Entity.Data... tests) {
//        UI.docViews.docView.testsContainer.toolbar.clickAddExistingTests();
//        UI.dialogs.addExistingTestsDialog.grid.expectVisible();
//
//        for (Entity.Data test : tests) {
//            UI.dialogs.addExistingTestsDialog.toolbar.search(test.getName());
//            UI.dialogs.addExistingTestsDialog.grid.getRowById(test.getId()).select();
//            UI.dialogs.addExistingTestsDialog.clickAdd();
//        }
//
//        UI.dialogs.addExistingTestsDialog.clickClose();
//        UI.dialogs.addExistingTestsDialog.expectNotVisible();
//    }
//
//    private void setMachineTemplateAndRunMode(String machineTemplate, String runMode) {
//        UI.docViews.docView.selectTab("details");
//        UI.docViews.docView.detailsTab.expectVisible();
//        UI.docViews.docView.detailsTab.setMachineTemplate(machineTemplate);
//        UI.docViews.docView.detailsTab.setExecutionMode(runMode);
//    }
//
//    private String createSchedule(String scheduleName, String runMode) {
//        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
//        UI.functionalTestingView.schedulesContainer.expectVisible();
//        UI.functionalTestingView.schedulesContainer.toolbar.clickAdd();
//
//        UI.dialogs.addScheduleDialog.name.sendKeys(scheduleName);
//        UI.dialogs.addScheduleDialog.runMode.setValue(runMode);
//        UI.dialogs.addScheduleDialog.startTime.setValue(LocalDateTime.now().plusMinutes(1));
//
//        if (runMode == "Recurring") {
//            UI.dialogs.addScheduleDialog.repeatEvery.sendKeys("4");
//        }
//
//        UI.dialogs.addScheduleDialog.clickAddAndEdit();
//        return "";
//    }
//
//    private void addSuiteToSchedule(String testSuiteId) {
//        UI.docViews.docView.fteTestSuitesContainer.toolbar.clickAddTestSuites();
//        UI.dialogs.addExistingTestsDialog.grid.expectVisible();
//        UI.dialogs.addExistingTestsDialog.grid.getRowById(testSuiteId).select();
//        UI.dialogs.addExistingTestsDialog.clickAddAndClose();
//        UI.dialogs.addExistingTestsDialog.expectNotVisible();
//    }
//
//    private String startSchedule() {
//        UI.docViews.docView.selectTab("fte-schedule-suites-runs-tab");
//        UI.docViews.docView.scheduleRunsContainer.grid.expectVisible();
//        UI.docViews.docView.scheduleRunsContainer.grid.getRow(1).select();
//        UI.docViews.docView.scheduleRunsContainer.grid.getRow(1).clickId();
//        UI.docViews.docView.detailsTab.expectVisible();
//        UI.docViews.docView.detailsTab.setDropdownField("status", "In progress");
//        UI.docViews.docView.detailsTab.toolbar.clickSave();
//        UI.notifications.success.expectVisible();
//        return UI.docViews.docView.getEntityId();
//    }
//}