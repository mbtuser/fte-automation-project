package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.EntityContainer;
import com.ot.automation.framework.octane.framework.Locator;

public class SuiteRunsContainer extends EntityContainer {

    public SuiteRunsContainer(BaseElement parent) {
        super(Locator.dataAid("suite-run-tab-runs-view-container"), parent);
    }
}