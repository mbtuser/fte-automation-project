package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;

public class TestSuiteContainer extends BaseElement {
    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public TestSuiteContainer(BaseElement parent) {
        super(Locator.dataAid("tests-suite-view"), parent);
    }
}