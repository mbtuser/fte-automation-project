package com.ot.automation.framework.octane.ui.mbt;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class ModelBasedTestingView extends BaseElement {

    public final ModelTree tree = new ModelTree();
    public UnitsContainer unitsContainer = new UnitsContainer(this);
    public ModelsContainer modelsContainer = new ModelsContainer(this);

    public ModelBasedTestingView() {
        super(Locator.cssSelector("[ui-state-name='mbt-container']"));
    }

    public void navigateToModels() {
        new BaseElement(Locator.dataAid("mqm-tab-models")).click();
    }

    public void navigateToUnits() {
        new BaseElement(Locator.dataAid("mqm-tab-units")).click();
    }
}