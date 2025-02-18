package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class FunctionalTestingExecutionView extends BaseElement {

    public TestsContainerFte testsContainerFte = new TestsContainerFte(this);
    public SchedulesContainer schedulesContainer = new SchedulesContainer(this);
    public ScheduledRunsContainer scheduledRunsContainer = new ScheduledRunsContainer(this);

    public FunctionalTestingExecutionView() {
        super(Locator.dataAid("scheduler-module-tabs"));
    }

    public void navigateToSchedules() {
        new BaseElement(Locator.cssSelector("[data-tab-key='fte-module-scheduler-tab-main']")).click();
    }
}