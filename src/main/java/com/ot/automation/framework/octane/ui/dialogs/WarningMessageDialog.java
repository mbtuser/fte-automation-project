package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class WarningMessageDialog extends BaseElement {

    public WarningMessageDialog() {
        super(Locator.cssSelector("[data-aid*='dialog-view-warn']"));
    }

    public void clickDelete() {
        new BaseElement(Locator.dataAid("dialog.delete"), this).click();
    }

    public void clickCancel() {
        new BaseElement(Locator.dataAid("dialog.close"), this).click();
    }
}