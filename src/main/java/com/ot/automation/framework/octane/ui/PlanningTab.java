package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class PlanningTab extends BaseElement {
    public PlanningTab(BaseElement parent) {
        super(Locator.dataAid("tests-suite-view"), parent);
    }

    public Grid grid = new Grid(this);
    public Toolbar toolbar = new Toolbar(this);

    public void selectRunModeAutomatically() {
        BaseElement runMode = new BaseElement(Locator.cssSelector("[field-name='run_mode']"), this);
        runMode.click();
        runMode.click();
        BaseElement selectAutomatically = new BaseElement(Locator.cssSelector("[data-text-value='Automatically']"));
        selectAutomatically.click();
    }

    public void selectRunner(String testRunner) {
        BaseElement runner = new BaseElement(Locator.cssSelector("[field-name='test_runner']"), this);
        runner.click();
        BaseElement selectRunner = new BaseElement(Locator.cssSelector("[data-text-value=" + testRunner + "]"));
        selectRunner.click();
    }

}
