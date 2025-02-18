package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;

public class SiteSpacesContainer extends BaseElement {

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public SiteSpacesContainer(BaseElement parent) {
        super(Locator.dataAid("shared-spaces-grid"), parent);
    }
}