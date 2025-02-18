package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class AddEntityDialog extends BaseElement {
    public AddEntityDialog(Locator locator) {
        super(locator, null);
    }

    public BaseElement xButton = new BaseElement(Locator.cssSelector("[data-icon='close']"), this);
    public BaseElement addButton = new BaseElement(Locator.dataAid("alm-entity-form-buttons-add"), this);
}