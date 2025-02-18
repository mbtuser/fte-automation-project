package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import org.openqa.selenium.By;

public class GridRow extends BaseElement {

    private final Grid grid;

    public GridRow(int rowNum, Grid grid) {
        super(By.cssSelector("div[class*='item-index-" + (rowNum - 1) + "'" + "]"), grid);
        this.grid = grid;
    }

    public GridRow(String entityId, Grid grid) {
        super(By.cssSelector("div[class*='item-id-" + entityId + "'" + "]"), grid);
        this.grid = grid;
    }

    public GridRow(double rowNum, Grid grid) {
        super(By.cssSelector("div[class*='slick-row']:nth-of-type(" + (int)rowNum + ")"), grid);
        this.grid = grid;
    }

    public GridRow(By selector, Grid grid) {
        super(selector, grid);
        this.grid = grid;
    }

    public GridCell getCell(int cellNum) {
        return new GridCell(this, cellNum);
    }

    public GridCell getCell(String fieldName) {
        return new GridCell(this, fieldName);
    }

    public Grid getGrid() {
        return grid;
    }

    public void clickId() {
        BaseElement idColumn = new BaseElement(By.cssSelector("[class*='alm-entity-grid-id-column']"), this);
        idColumn.click();
    }

    public String getId() {
        BaseElement idColumn = new BaseElement(By.cssSelector("[class*='alm-entity-grid-id-column']"), this);
        return idColumn.getText();
    }

    public void select() {
        this.expectVisible();
        this.hover();
        BaseElement link = new BaseElement(By.cssSelector("[class='checkboxes']"), this);
        link.click();
    }
}
