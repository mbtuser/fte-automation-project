package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.fte.*;
import com.ot.automation.framework.octane.ui.mbt.ModelsTab;
import com.ot.automation.framework.octane.ui.mbt.UnitsTab;

public class DocView extends BaseElement {
    public TestsContainer testsContainer = new TestsContainer(this);
    public TestsContainerMbt testsContainerMbt = new TestsContainerMbt(this);
    public RunsContainer runsContainer = new RunsContainer(this);
    public SuiteRunsContainer suiteRunsContainer = new SuiteRunsContainer(this);
    public SuiteReportContainer suiteReportContainer = new SuiteReportContainer(this);
    public FteTestSuitesContainer fteTestSuitesContainer = new FteTestSuitesContainer(this);
    public SchedulesContainer schedulesContainer = new SchedulesContainer(this);
    public ScheduleRunsContainer scheduleRunsContainer = new ScheduleRunsContainer(this);
    public PathsContainer pathsContainer = new PathsContainer(this);
    public DataSetsContainer dataSetsContainer = new DataSetsContainer(this);
    public ParametersContainer parametersContainer = new ParametersContainer(this);
    public DetailsTab detailsTab = new DetailsTab(this);
    public TestSuiteContainer testSuiteContainer = new TestSuiteContainer(this);
    public PlanningTab planningTab = new PlanningTab(this);
    public ModelsTab modelsTab = new ModelsTab(this);
    public UnitsTab unitsTab = new UnitsTab(this);

    public DocView() {
        super(Locator.cssSelector("[name='entityForm']"));
    }

    public void selectTab(String tabName) {
        new BaseElement(Locator.cssSelector("[tab-name='" + tabName + "']"), this).click();
        new BaseElement(Locator.cssSelector("[ui-state-name*='" + tabName + "']"), this).expectVisible();
    }

    public String getEntityId() {
        return new BaseElement(Locator.cssSelector("[class*='entity-form-document-view-header-entity-id-container']"), this).getText();
    }
}