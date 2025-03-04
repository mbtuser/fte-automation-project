package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class CreateCodelessScriptDialog extends BaseElement {
    public CreateCodelessScriptDialog() {
        super(Locator.dataAid("unit-create-codeless-script-dialog-container"));
    }

    public BaseElement createBlankCodelessScriptRadioButton = new BaseElement(Locator.cssSelector("value*='blank_script'"), this);
    public BaseElement generateCodelessScriptFromManualTest = new BaseElement(Locator.cssSelector("value*='from_manual_test'"), this);

    public void closeByX() {
        new BaseElement(Locator.cssSelector("class*='close-button'"), this).click();
    }
}