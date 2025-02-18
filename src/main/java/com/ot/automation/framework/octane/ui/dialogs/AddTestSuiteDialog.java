package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class AddTestSuiteDialog extends BaseElement {
    public AddTestSuiteDialog() {
        super(Locator.dataAid("new-entity-dialog-container"), null);
    }

    private final BaseElement nameParent = new BaseElement(Locator.dataAid("name"), this);
    public BaseElement name = new BaseElement(Locator.dataAid("alm-string-field-editor-input-box"), nameParent);

    public void clickAdd() {
        new BaseElement(Locator.dataAid("alm-entity-form-buttons-add"), this).click();
    }
}