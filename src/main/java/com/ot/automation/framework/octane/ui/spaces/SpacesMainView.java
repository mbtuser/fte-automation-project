package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class SpacesMainView extends BaseElement {

    public WorkspacesForm workspaceForm = new WorkspacesForm();
    public WorkspacesTree tree = new WorkspacesTree();

    public SpacesMainView() {
        super(Locator.dataAid("workspaces-main-frame-new"));
    }
}