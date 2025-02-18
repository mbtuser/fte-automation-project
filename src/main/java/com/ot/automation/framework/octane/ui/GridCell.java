package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

public class GridCell extends BaseElement {

    public GridCell(GridRow row, int columnNum) {
        super(Locator.cssSelector("[class~='l" + (columnNum - 1) + "']"), row);
//        Grid grid = row.getGrid();
    }

    public GridCell(GridRow row, String fieldName) {
        super(Locator.cssSelector("[field-name='" + fieldName + "']"), row);
//        Grid grid = row.getGrid();
    }

    public void sendKeys(String text) {
        BaseElement inputBox = new BaseElement(By.cssSelector("input"), this);
        inputBox.expectVisible();
        inputBox.sendKeys(text);
    }
}