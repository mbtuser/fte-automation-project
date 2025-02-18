package com.ot.automation.framework.octane.ui.mbt;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Entities.ModelFolder;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;

public class ModelTree extends BaseElement {

    public Toolbar toolbar = new Toolbar(this);

    public ModelTree() {
        super(By.cssSelector("[data-aid='alm-splitter-box-column-west']"));
    }

    public void selectFolder(ModelFolder.Data folder) {
        new BaseElement(Locator.cssSelector("[data-entity-type-and-id*='" + folder.getId() + "']"), this).click();
    }

    public void expandFolder(ModelFolder.Data folder) {
        BaseElement folderRow = new BaseElement(Locator.cssSelector("[data-entity-type-and-id*='" + folder.getId() + "']"), this);

        String objectClass = folderRow.getAttribute("class");

        if (!objectClass.contains("dynatree-expanded"))
            new BaseElement(Locator.cssSelector("[class*='dynatree-expander']"), folderRow).click();
    }

    public void refresh() {
        new BaseElement(Locator.cssSelector("[data-aid*='tree-toolbar-refresh']"), this).click();
    }
}