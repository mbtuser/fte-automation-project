package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;

public class SchedulesContainer extends BaseElement {

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public SchedulesContainer(BaseElement parent) {
        super(By.cssSelector("[ui-state-name='platform-tree-module-fte-scheduler-grid']"), parent);
    }
}