package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class RunTestDialog extends BaseElement {
    public RunTestDialog() {
        super(Locator.dataAid("new-entity-dialog-container"), null);
    }

    public void clickRun() {
        new BaseElement(Locator.dataAid("alm-entity-form-buttons-save"), this).click();
    }
}
