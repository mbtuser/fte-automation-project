package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;

public class DataSetsContainer extends BaseElement {

    public Grid grid = new Grid(By.cssSelector("[data-aid*='test-parameters-grid']"), this);
    public Toolbar toolbar = new Toolbar(this);

    public DataSetsContainer(BaseElement parent) {
        super(Locator.cssSelector("[ui-state-name*='entity-doc-view-mbt-data-table'"), parent);
    }
}