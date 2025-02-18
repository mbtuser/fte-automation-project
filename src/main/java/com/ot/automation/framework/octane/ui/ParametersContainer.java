package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

public class ParametersContainer extends BaseElement {

    public Grid grid = new Grid(By.cssSelector("[data-aid*='automation-grid-container']"), this);
    public Toolbar toolbar = new Toolbar(this);

    public ParametersContainer(BaseElement parent) {
        super(Locator.cssSelector("[ui-state-name*='entity-doc-view-model-based-test-parameters-tab'"), parent);
    }
}