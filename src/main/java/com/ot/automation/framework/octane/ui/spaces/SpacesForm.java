package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class SpacesForm extends BaseElement {

    public SpacesForm() {
        super(Locator.dataAid("workspaces-main-frame-new"));
    }

    public void navigateToDevOps() {
        new BaseElement(Locator.dataAid("workspace-header-tab-devops")).click();
    }
}