package com.ot.automation.framework.octane.ui.fte;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.EntityContainer;
import org.openqa.selenium.By;

public class TestsContainerFte extends EntityContainer {

    public TestsContainerFte(BaseElement parent) {
        super(By.cssSelector("[ui-state-name='platform-tree-module-fte-scheduler-tests-main']"), parent);
    }
}