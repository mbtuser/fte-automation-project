package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.EntityContainer;
import com.ot.automation.framework.octane.framework.Locator;

public class FteTestSuitesContainer extends EntityContainer {

    public FteTestSuitesContainer(BaseElement parent) {
        super(Locator.dataAid("fte-schedule-test_suites-tab"), parent);
    }
}