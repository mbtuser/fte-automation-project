package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.SmartEditor;

public class AddTestRunnerDialog extends BaseElement {
    public AddTestRunnerDialog() {
        super(Locator.dataAid("test-runner-dialog"), null);
    }

    public SmartEditor framework = new SmartEditor(Locator.cssSelector("[field-metadata='testRunnerDialogCtrl.testRunnerDialogService.frameworkField']"), this);
    public SmartEditor ciServer = new SmartEditor(Locator.cssSelector("[field-metadata='testRunnerDialogCtrl.ciServerField']"), this);
    public BaseElement xButton = new BaseElement(Locator.cssSelector("[data-icon='close']"), this);
    public BaseElement repository = new BaseElement(Locator.dataAid("test-runner-repository-input"), this);
    public BaseElement testConnectionButton = new BaseElement(Locator.dataAid("test-connection-button"), this);
    public BaseElement testConnectionMessage = new BaseElement(Locator.cssSelector("[ng-class*='testRunnerDialogCtrl.testRunnerDialogService.connStatus']"), this);

    public void clickSave() {
        new BaseElement(Locator.dataAid("admin:test-runner-dialog-action-tab-add-test-runner"), this).click();
    }

    public void testRunnerName(String name) {
        BaseElement setRunnerName = new BaseElement(Locator.dataAid("test-runner-name-input"), this);
        setRunnerName.sendKeys(name);
    }

    public String getTestConnectionMessage() {
        String innerText = testConnectionMessage.getText();
        return innerText;
    }
}