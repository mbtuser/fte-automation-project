package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.SmartEditor;

public class AddTestDialog extends AddEntityDialog {
    public AddTestDialog() {
        super(Locator.dataAid("new-entity-dialog-container"));
    }

    public BaseElement name = new BaseElement(Locator.dataAid("test-runner-name-input"), this);
    public SmartEditor framework = new SmartEditor(Locator.cssSelector("[field-metadata='testRunnerDialogCtrl.testRunnerDialogService.frameworkField']"), this);
    public SmartEditor ciServer = new SmartEditor(Locator.cssSelector("[field-metadata='testRunnerDialogCtrl.ciServerField']"), this);

    public BaseElement xButton = new BaseElement(Locator.cssSelector("[data-icon='close']"), this);

}