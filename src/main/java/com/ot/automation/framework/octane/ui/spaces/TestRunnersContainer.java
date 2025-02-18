package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.*;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;

public class TestRunnersContainer extends BaseElement {
    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public TestRunnersContainer(BaseElement parent) {
        super(Locator.dataAid("workspace-executors"), parent);
    }
}