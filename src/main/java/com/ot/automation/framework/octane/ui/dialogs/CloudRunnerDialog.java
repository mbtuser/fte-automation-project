package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.SmartEditor;

public class CloudRunnerDialog extends BaseElement {
    public CloudRunnerDialog() {
        super(Locator.dataAid("cloud-runner-dialog"), null);
    }

    public BaseElement name = new BaseElement(Locator.dataAid("test-runner-name-input"), this);
    public SmartEditor framework = new SmartEditor(Locator.cssSelector("[field-metadata='cloudRunnerDialogCtrl.testRunnerDialogService.frameworkField']"), this);
    public BaseElement url = new BaseElement(Locator.cssSelector("[ng-model='cloudRunnerDialogCtrl.cloudExecutorEntity.scm_repository.url']"), this);
    public BaseElement username = new BaseElement(Locator.dataAid("test-runner-username-input"), this);
    public BaseElement password = new BaseElement(Locator.dataAid("test-runner-password-input"), this);
    public BaseElement testConnectionButton = new BaseElement(Locator.dataAid("test-connection-button"), this);
    public BaseElement syncNowButton = new BaseElement(Locator.dataAid("sync-now-button"), this);
    public BaseElement testConnectionMessage = new BaseElement(Locator.cssSelector("[ng-class*='cloudRunnerDialogCtrl.testRunnerDialogService.connStatus']"), this);
    public BaseElement syncNowMessage = new BaseElement(Locator.cssSelector("[ng-class*='cloudRunnerDialogCtrl.testRunnerDialogService.syncNowStatus']"), this);
    public BaseElement scheduleToggle = new BaseElement(Locator.cssSelector("[ng-click='cloudRunnerDialogCtrl.toggleScheduleMode()']"), this);
    public BaseElement xButton = new BaseElement(Locator.cssSelector("[data-icon='close']"), this);

    public void clickSave() {
        new BaseElement(Locator.dataAid("admin:test-runner-dialog-action-tab-add-test-runner"), this).click();
    }

    public void clickCancel() {
        new BaseElement(Locator.dataAid("admin:test-runner-dialog-action-tab-cancel"), this).click();
    }

    public String getTestConnectionMessage() {
        String innerText = testConnectionMessage.getText();
        return innerText;
    }

    public String getSyncNowMessage() {
        String innerText = syncNowMessage.getText();
        return innerText;
    }

    public void setPackagePath(String packagePath, int rowNumber) {
        BaseElement packagePathRow = new BaseElement(Locator.dataAid("package-path-row-" + rowNumber), this);
        BaseElement packagePathBox = new BaseElement(Locator.cssSelector("input"), packagePathRow);
        packagePathBox.expectVisible();
        packagePathBox.sendKeys(packagePath);
    }

    public void selectExecutionStorage() {
        BaseElement executionStorageButton = new BaseElement(Locator.dataAid("external-storage-button alm-radio-button"), this);
        executionStorageButton.click();
    }
}