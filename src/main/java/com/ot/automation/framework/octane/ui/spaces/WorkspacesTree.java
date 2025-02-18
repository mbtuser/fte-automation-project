package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.Locator;

public class WorkspacesTree extends BaseElement {

    public WorkspacesTree() {
        super(Locator.dataAid("workspaces-tree-frame"));
    }

    public void selectWorkspace(String workspaceName) {
        BaseElement workspace = new BaseElement(Locator.dataAid(workspaceName), this);
        workspace.click();
        BaseElement workspaceTitle = new BaseElement(Locator.dataAid("workspace-header-name"), null);
        workspaceTitle.expectTextToContain(workspaceName);
        GeneralUtils.waitLoadingBar();
    }
}