package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.Locator;

public class DevOpsView extends BaseElement {

    public CiServersContainer ciServersContainer = new CiServersContainer(this);
    public TestRunnersContainer testRunnersContainer = new TestRunnersContainer(this);

    public DevOpsView(BaseElement parent) {
        super(Locator.dataAid("workspaces-details-tab-entities"), parent);
    }

    public void selectTab(Enum tabSelector) {
        BaseElement tabName = getTab(tabSelector);
        tabName.click();
        GeneralUtils.waitLoadingBar();
    }

    public BaseElement getTab(Enum tabSelector) {
        return new BaseElement(Locator.dataAid(String.valueOf(tabSelector)), this);
    }

    public enum Tabs {
        CI_SERVER("devops-admin-build-servers"),
        SCM_REPOSITORIES("source-code-repositories"),
        TEST_ASSIGNMENT_RULES("rules"),
        TEST_RUNNERS("devops-admin-executors"),
        LOG_CLASSIFICATION_RULE("log-rule-management"),
        COMMIT_PATTERNS("commit-patterns"),
        COLLABORATION_TOOLS("collaboration-tools");

        private final String value;

        Tabs(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }
}