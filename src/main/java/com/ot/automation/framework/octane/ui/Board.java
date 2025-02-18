package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Entities.Entity;
import org.openqa.selenium.By;

public class Board extends BaseElement {

    public Board(BaseElement parent) {
        super(By.cssSelector("[data-aid*='entities-container-board']"), parent);
    }

    public Board(By locator, BaseElement parent) {
        super(locator, parent);
    }

    public BoardCard getBoardCard(Entity.Data entity) {
        return new BoardCard(entity, this);
    }
}