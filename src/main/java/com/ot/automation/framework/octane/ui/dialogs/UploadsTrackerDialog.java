package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.EntityContainer;
import com.ot.automation.framework.octane.ui.SmartEditor;

public class UploadsTrackerDialog extends BaseElement {
    public UploadsTrackerDialog() {
        super(Locator.dataAid("entity-list-dialog-container"), null);
    }
    public final EntityContainer artifactsContainer = new EntityContainer(Locator.dataAid("entity-list-view"), this);
}