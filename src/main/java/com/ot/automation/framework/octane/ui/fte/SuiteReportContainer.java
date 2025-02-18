package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.EntityContainer;

public class SuiteReportContainer extends EntityContainer {
    public SuiteReportContainer(BaseElement parent) {
        super(Locator.dataAid("suite-run-report-tab-view"), parent);
    }
}