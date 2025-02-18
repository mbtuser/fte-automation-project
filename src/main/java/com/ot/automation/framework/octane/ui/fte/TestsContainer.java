package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.EntityContainer;
import com.ot.automation.framework.octane.framework.Locator;

public class TestsContainer extends EntityContainer {

    public TestsContainer(BaseElement parent) {
        super(Locator.dataAid("tests-suite-view"), parent);
    }
}