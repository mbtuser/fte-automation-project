package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class ModelTestGeneratorDialog extends BaseElement {
    public ModelTestGeneratorDialog() {
        super(Locator.className("generate-tests-dialog"));
    }

    public void clickGenerate() {
        BaseElement generateButton = new BaseElement(Locator.dataAid("report-configuration-dialog-generate-btn"), this);
        generateButton.click();
    }
}