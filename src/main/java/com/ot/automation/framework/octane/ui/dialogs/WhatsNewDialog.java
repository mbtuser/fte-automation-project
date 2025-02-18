package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.Locator;

public class WhatsNewDialog extends BaseElement {
    public WhatsNewDialog() {
        super(Locator.dataAid("whats-new-body"));
    }

    public void closeByX() {
        new BaseElement(Locator.cssSelector("class*='close-button'"), this).click();
    }
}