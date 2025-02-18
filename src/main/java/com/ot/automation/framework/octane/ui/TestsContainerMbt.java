package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.EntityContainer;

public class TestsContainerMbt extends EntityContainer {

    public TestsContainerMbt(BaseElement parent) {
        super(Locator.dataAid("model-tests-tab"), parent);
    }
}