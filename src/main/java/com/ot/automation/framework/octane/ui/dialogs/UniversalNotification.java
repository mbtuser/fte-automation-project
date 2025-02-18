package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class UniversalNotification extends BaseElement {

    public UniversalNotification() {
        super(Locator.dataAid("notification-layout"));
    }

    public void clickButton() {
        BaseElement button = new BaseElement(Locator.cssSelector("[data-aid=\"button\"]"), this);
        button.click();
    }
}