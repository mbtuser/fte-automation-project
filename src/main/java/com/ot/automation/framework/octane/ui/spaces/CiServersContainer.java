package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Toolbar;

public class CiServersContainer extends BaseElement {

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public CiServersContainer(BaseElement parent) {
        super(Locator.dataAid("workspace-build-servers"), parent);
    }
}