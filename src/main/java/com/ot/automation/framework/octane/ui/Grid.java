package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;
import org.testng.Assert;

public class Grid extends BaseElement {

    public Grid(BaseElement parent) {
        super(By.cssSelector("[data-aid*='alm-entity-grid']"), parent);
    }

    public Grid(By locator, BaseElement parent) {
        super(locator, parent);
    }

    public GridRow getRow(int rowNum) {
        return new GridRow(rowNum, this);
    }

    public GridRow getRowById(String id) {
        return new GridRow(id, this);
    }

    public GridRow getRow(double rowNum) {
        return new GridRow(rowNum, this);
    }

    public GridRow getSlickRow(int rowNum) {
        return new GridRow(By.cssSelector("div[class*='slick-row']:nth-of-type(" + rowNum + ")"), this);
    }

    public void stopRun() {
        BaseElement stopRunButton = new BaseElement(Locator.cssSelector("[data-icon='stop-execute']"));
        stopRunButton.click();
    }

    public boolean isSkippedIconPresent() {
        BaseElement skippedIcon = new BaseElement(By.cssSelector("[data-icon='f-status-alert']"));
        return skippedIcon != null;
    }

    public void expectNumberOfRows(int expectedRowsNumber) {
        this.expectVisible();
        BaseElement statusBar = new BaseElement(Locator.dataAid("selection-status-bar-container"));
        String totalCount = new BaseElement(Locator.dataAid("status-bar-total-count"), statusBar).getText().replaceAll("[^0-9]", "");
        int totalCountNumber = Integer.parseInt(totalCount);
        Assert.assertEquals(totalCountNumber, expectedRowsNumber, "Number of rows not as expected.");
    }
}