package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Entities.Entity;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BoardCard extends BaseElement {
    public BoardCard(Entity.Data entity, Board board) {
        super(By.cssSelector("[editable-entity-id='" + entity.getId() + "']"), board);
    }

    public void clickStopRun() {
        BaseElement stopRunButton = new BaseElement(Locator.cssSelector("[data-icon='stop-execute']"), this);
        stopRunButton.click();
    }

    public void expectSkippedIcon() {
        BaseElement skippedIcon = new BaseElement(By.cssSelector("[data-icon='f-status-alert']"), this);
        BaseElement skippedIconNew = new BaseElement(By.cssSelector("[data-aid*='skipped-notification-icon-warning']"), this);
        Assert.assertTrue(skippedIcon.isVisible() || skippedIconNew.isVisible());
    }
}