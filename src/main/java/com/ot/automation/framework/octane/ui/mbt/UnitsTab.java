package com.ot.automation.framework.octane.ui.mbt;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;

public class UnitsTab extends BaseElement {

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public UnitsTab(BaseElement parent) {
        super(By.cssSelector("[ui-state-name='entity-navigation-edit-entity-doc-view-units']"), parent);
    }
}