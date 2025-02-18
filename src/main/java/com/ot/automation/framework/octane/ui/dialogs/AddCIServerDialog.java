package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class AddCIServerDialog extends BaseElement {
    public AddCIServerDialog() {
        super(Locator.dataAid("add-build-server-dialog"), null);
    }

    public void setCIName(String ciName) {
        BaseElement name = new BaseElement(Locator.dataAid("alm-string-field-editor-input-box"), this);
        BaseElement clickName = new BaseElement(Locator.dataAid("build-server-name-input"), this);
        clickName.click();
        name.sendKeys(ciName);
    }

    public void selectCIServer(String url) {
        new BaseElement(Locator.dataAid("build-server-location-input"), this).click();
        BaseElement selectCIServer = new BaseElement(Locator.dataAid(url), this);
        selectCIServer.click();
    }

    public void clickAdd() {
        new BaseElement(Locator.dataAid("admin:add-build-server-dialog-ok-btn"), this).click();
    }
}
