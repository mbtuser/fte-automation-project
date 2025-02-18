package com.ot.automation.tests;

import com.ot.automation.framework.octane.framework.BaseAutomationTest;
import com.ot.automation.framework.octane.framework.Entities.ModelFolder;
import com.ot.automation.framework.octane.framework.Entities.Release;
import com.ot.automation.framework.octane.framework.Entities.TestSuite;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.UrlNavigation;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

public class DataPopulation extends BaseAutomationTest {
    static RemoteWebDriver driver;
    static WebDriverWait waiter;

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
    public void dataPopulationSelenium() {
        validateDataPopulation(true);
    }

    @Test
    public void dataPopulationOTFT() {
        validateDataPopulation(false);
    }

    @Test
    public void dataPopulationManualTesting() {
        navigateByUrl(UrlNavigation.Module.MODEL_BASED_TESTING);
        UI.modelBasedTestingView.expectVisible();
        UI.modelBasedTestingView.tree.toolbar.clickCreateSampleButton();
        UI.dialogs.createSampleProjectDialog.expectVisible();
        UI.dialogs.createSampleProjectDialog.expectTextToContain("Create Sample Project");

        // Generate sample data for Manual Testing
        UI.dialogs.createSampleProjectDialog.selectManualTesting();
        UI.dialogs.createSampleProjectDialog.clickNext();
        UI.dialogs.createSampleProjectDialog.clickBack();
        UI.dialogs.createSampleProjectDialog.clickNext();
        UI.dialogs.createSampleProjectDialog.clickCreate();
        UI.dialogs.createSampleProjectDialog.closeByX();

        // Wait for data population (root folder to be created)
        GeneralUtils.waitAtMostFor(180000, "Cannot retrieve test suite 'Open new account*'", () -> TestSuite.getEntityByName("Open new account*", List.of(TestSuite.Fields.NAME)).getName());

        // Validate success notification when task is running in background
        UI.notifications.universalNotification.expectVisible();
        UI.notifications.universalNotification.expectTextToContain("The MBT sample resources are being processed", "MBT sample resources were created successfully");
        UI.notifications.universalNotification.clickButton();

        UI.dialogs.createSampleProjectDialog.waitForDataPopulation();
        UI.dialogs.createSampleProjectDialog.expectTextToContain("All the resources were created successfully");

        // Validate Units, Models, Test & Test suite links and generated entities
        UI.dialogs.createSampleProjectDialog.clickUnitsLinkManual();
        GeneralUtils.switchTab();
        UI.docViews.docView.unitsTab.expectVisible();
        UI.docViews.docView.unitsTab.grid.getRow(7).expectVisible();
        UI.docViews.docView.unitsTab.grid.expectNumberOfRows(7);
        GeneralUtils.closeCurrentTab();

        UI.dialogs.createSampleProjectDialog.clickModelsLinkManual();
        GeneralUtils.switchTab();
        UI.docViews.docView.modelsTab.expectVisible();
        UI.docViews.docView.modelsTab.grid.getRow(2).expectVisible();
        UI.docViews.docView.modelsTab.grid.expectNumberOfRows(2);
        GeneralUtils.closeCurrentTab();

        UI.dialogs.createSampleProjectDialog.clickTestsLinkManual();
        GeneralUtils.switchTab();
        UI.docViews.docView.testsContainer.grid.getRow(3).expectVisible();
        UI.docViews.docView.testsContainer.grid.expectNumberOfRows(3);
        GeneralUtils.closeCurrentTab();

        // Go back to main dialog and check Populated message
        UI.dialogs.createSampleProjectDialog.clickCreateAnotherProject();
        UI.dialogs.createSampleProjectDialog.expectPopulatedManualTesting();
        UI.dialogs.createSampleProjectDialog.closeByX();
        UI.dialogs.createSampleProjectDialog.expectNotVisible();

        // Validate generated manual testing data in mbt module
        UI.modelBasedTestingView.tree.refresh();
        UI.modelBasedTestingView.tree.expandFolder(ModelFolder.getEntityByName("Demo Data Samples"));
        UI.modelBasedTestingView.tree.selectFolder(ModelFolder.getEntityByName("MBT manual testing"));

        UI.modelBasedTestingView.modelsContainer.grid.getRow(2).expectVisible();
        UI.modelBasedTestingView.modelsContainer.grid.expectNumberOfRows(2);
        UI.modelBasedTestingView.navigateToUnits();
        UI.modelBasedTestingView.unitsContainer.grid.getRow(7).expectVisible();
        UI.modelBasedTestingView.unitsContainer.grid.expectNumberOfRows(7);

        // Delete the sample
        UI.modelBasedTestingView.tree.toolbar.clickCreateSampleButton();
        UI.dialogs.createSampleProjectDialog.selectManualTesting();
        UI.dialogs.createSampleProjectDialog.clickNext();
        UI.dialogs.createSampleProjectDialog.clickDeleteSample();

        UI.dialogs.warningMessageDialog.clickDelete();
        UI.notifications.success.expectTextToContain("The mbt sample was deleted.");
    }

    private void validateDataPopulation(boolean isSelenium) {
        // Generate sample data for Selenium / OTFT
        navigateByUrl(UrlNavigation.Module.MODEL_BASED_TESTING);
        UI.modelBasedTestingView.expectVisible();
        UI.modelBasedTestingView.tree.toolbar.clickCreateSampleButton();
        if (isSelenium) {
            UI.dialogs.createSampleProjectDialog.selectSelenium();
        } else {
            UI.dialogs.createSampleProjectDialog.selectOpenTextFunctionalTesting();
        }
        UI.dialogs.createSampleProjectDialog.clickNext();
        UI.dialogs.createSampleProjectDialog.clickCreate();
        UI.dialogs.createSampleProjectDialog.waitForDataPopulation();
        UI.dialogs.createSampleProjectDialog.expectTextToContain("All the resources were created successfully");

        // Validate Units, Models, Test & Test suite links and generated entities
        UI.dialogs.createSampleProjectDialog.clickUnitsLink();
        GeneralUtils.switchTab();
        UI.docViews.docView.unitsTab.expectVisible();
        UI.docViews.docView.unitsTab.grid.getRow(7).expectVisible();
        UI.docViews.docView.unitsTab.grid.expectNumberOfRows(7);
        GeneralUtils.closeCurrentTab();

        UI.dialogs.createSampleProjectDialog.clickModelsLink();
        GeneralUtils.switchTab();
        UI.docViews.docView.modelsTab.expectVisible();
        UI.docViews.docView.modelsTab.grid.getRow(2).expectVisible();
        UI.docViews.docView.modelsTab.grid.expectNumberOfRows(2);
        GeneralUtils.closeCurrentTab();

        UI.dialogs.createSampleProjectDialog.clickTestsLink();
        GeneralUtils.switchTab();
        UI.docViews.docView.testsContainer.grid.getRow(1).expectVisible();
        UI.docViews.docView.testsContainer.grid.expectNumberOfRows(1);

        // Run the test suite
        Release.create("Release_" + RandomStringUtils.randomAlphabetic(5), LocalDateTime.now(), LocalDateTime.now().plusDays(30), 7);
        if (UI.dialogs.whatsNewDialog.isVisible()) {
            UI.dialogs.whatsNewDialog.closeByX();
        }

        UI.docViews.docView.testSuiteContainer.toolbar.clickRunSuite();
        UI.dialogs.runTestDialog.clickRun();
        UI.docViews.docView.selectTab("suite_runs");
        String runId = UI.docViews.docView.runsContainer.grid.getRow(1).getId();
        String status = GeneralUtils.waitSuiteRunInProgress(1200000, runId);
        Assert.assertTrue(status.contains("Passed"), "Suite run expected status: Passed. Actual status: " + status);

        // Go back to main dialog and check Populated message
        GeneralUtils.closeCurrentTab();
        UI.dialogs.createSampleProjectDialog.clickCreateAnotherProject();
        if (isSelenium) {
            UI.dialogs.createSampleProjectDialog.expectPopulatedSelenium();
        } else {
            UI.dialogs.createSampleProjectDialog.expectPopulatedOpenTextFunctionalTesting();
        }

        UI.dialogs.createSampleProjectDialog.closeByX();
        UI.dialogs.createSampleProjectDialog.expectNotVisible();

        // Validate generated manual testing data in mbt module
        UI.modelBasedTestingView.tree.refresh();
        UI.modelBasedTestingView.tree.expandFolder(ModelFolder.getEntityByName("Demo Data Samples"));
        if (isSelenium) {
            UI.modelBasedTestingView.tree.selectFolder(ModelFolder.getEntityByName("MBT Selenium*"));
        } else {
            UI.modelBasedTestingView.tree.selectFolder(ModelFolder.getEntityByName("MBT UFT One Framework"));
        }

        UI.modelBasedTestingView.navigateToModels();
        UI.modelBasedTestingView.modelsContainer.grid.getRow(2).expectVisible();
        UI.modelBasedTestingView.modelsContainer.grid.expectNumberOfRows(2);
        UI.modelBasedTestingView.navigateToUnits();
        UI.modelBasedTestingView.unitsContainer.grid.getRow(7).expectVisible();
        UI.modelBasedTestingView.unitsContainer.grid.expectNumberOfRows(7);
    }
}