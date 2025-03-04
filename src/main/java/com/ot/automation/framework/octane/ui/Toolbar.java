package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

public class Toolbar extends BaseElement {
    public Toolbar(BaseElement parent) {
        super(By.cssSelector("[role='toolbar']"), parent);
    }

    public void clickAddTestSuite() {
        clickDropdownButton();
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='add-test_suite']"), this);
        addButton.click();
    }

    public void clickAdd() {
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='add']"), this);
        addButton.click();
    }

    public void clickAddCloudRunner() {
        clickDropdownButton();
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='addCloudRunnerAction']"), this);
        addButton.click();
    }

    public void clickAddTestRunner() {
        clickDropdownButton();
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='add']"), this);
        addButton.click();
    }

    public void clickUploadTracker() {
        BaseElement uploadTrackerButton = new BaseElement(By.cssSelector("[data-action-name='uploadTrackerService']"), this);
        uploadTrackerButton.click();
    }

    public void clickAddCodelessTest() {
        clickDropdownButton();
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='add-codeless_test']"), this);
        addButton.click();
    }

    private void clickDropdownButton() {
        BaseElement dropdown = new BaseElement(Locator.dataAid("dropdown-container"), this);
        dropdown.click();
    }

    public void clickAddExistingTests() {
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='addExistingTestToTestSuite']"), this);
        addButton.click();
    }

    public void clickAddTestSuites() {
        BaseElement addButton = new BaseElement(By.cssSelector("[data-action-name='addTestSuites']"), this);
        addButton.click();
    }

    public void clickRefresh() {
        BaseElement refreshButton = new BaseElement(By.cssSelector("[data-action-name='refresh']"), this);
        refreshButton.click();
    }

    public void clickSave() {
        BaseElement saveButton = new BaseElement(Locator.dataAid("save"), this);
        saveButton.click();
    }

    public void clickSearch() {
        BaseElement searchButton = new BaseElement(By.cssSelector("[data-aid*='context-search-advanced']"), this);
        searchButton.click();
    }

    public void clearSearch() {
        BaseElement clearButton = new BaseElement(By.cssSelector("[data-aid*='-clear-button']"), this);
        if (clearButton.isVisible()) {
            clearButton.click();
        }
    }

    public void search(String searchText) {
        clickSearch();
        BaseElement clearButton = new BaseElement(By.cssSelector("[data-aid*='icon-button context-search-'][data-aid*='-clear-button']"), this);
        if (clearButton.isVisible()) {
            clearButton.click();
        }

        BaseElement searchBox = new BaseElement(By.cssSelector("[data-aid*='context-search-'][data-aid*='-input']:not([class*='icon'])"), this);
        searchBox.sendKeys(searchText);
    }

    public void clickGenerateTest() {
        BaseElement generateButton = new BaseElement(By.cssSelector("[data-action-name='generateTestFromPath']"), this);
        generateButton.click();
    }

    public void clickDownloadTestRunReport() {
        BaseElement downloadReportButton = new BaseElement(By.cssSelector("[data-aid*='testRun'][data-aid*='ExternalReportAction']"), this);
        downloadReportButton.click();
    }

    public void clickDownloadRecording() {
        BaseElement downloadRecordingButton = new BaseElement(By.cssSelector("[data-aid*='testRunDownloadExternalAssetsAction']"), this);
        downloadRecordingButton.click();
    }

    public void clickLinkParameters() {
        BaseElement linkParamButton = new BaseElement(By.cssSelector("[data-action-name='parameterLink']"), this);
        linkParamButton.click();
    }

    public void clickDeactivateSpace() {
        BaseElement deactivateButton = new BaseElement(By.cssSelector("[data-action-name='deactivateSharedSpaceAction']"), this);
        deactivateButton.click();
    }

    public void clickActivateSpace() {
        BaseElement activateButton = new BaseElement(By.cssSelector("[data-action-name='activateSharedSpaceAction']"), this);
        activateButton.click();
    }

    public void clickRunSuite() {
        BaseElement activateButton = new BaseElement(By.cssSelector("[data-action-name='runTestAction']"), this);
        activateButton.click();
    }

    public void clickCreateSampleButton() {
        BaseElement dataPopulationButton = new BaseElement(By.cssSelector("[data-aid*='data-population']"), this);
        dataPopulationButton.click();
    }

    public void clickCreateInDesignButton() {
        BaseElement createInDesignButton = new BaseElement(By.cssSelector("[data-aid*='unitGenerateCodelessScriptAction']"), this);
        createInDesignButton.click();
    }
}