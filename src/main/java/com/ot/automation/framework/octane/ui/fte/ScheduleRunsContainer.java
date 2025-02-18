package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.Board;
import com.ot.automation.framework.octane.ui.Grid;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;

public class ScheduleRunsContainer extends BaseElement {

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public ScheduleRunsContainer(BaseElement parent) {
        super(By.cssSelector("[ui-state-name='platform-tree-module-fte-scheduler-grid-grid-edit-entity-doc-view-fte-schedule-suites-runs-tab']"), parent);
    }
}