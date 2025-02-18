package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.EntityContainer;
import com.ot.automation.framework.octane.framework.Locator;

public class RunsContainer extends EntityContainer {

    public RunsContainer(BaseElement parent) {
        super(Locator.dataAid("test-runs-view"), parent);
    }
}