package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Toolbar;

public class AddExistingTestsDialog extends BaseElement {
    public AddExistingTestsDialog() {
        super(Locator.dataAid("entities-selection-dialog-container"), null);
    }

    public Toolbar toolbar = new Toolbar(this);
    public Grid grid = new Grid(this);

    public void clickAdd() {
        new BaseElement(Locator.dataAid("entities-selection-dialog-add"), this).click();
    }

    public void clickAddAndClose() {
        new BaseElement(Locator.dataAid("entities-selection-dialog-add-close"), this).click();
    }

    public void clickClose() {
        new BaseElement(Locator.dataAid("entities-selection-dialog-cancel"), this).click();
    }
}