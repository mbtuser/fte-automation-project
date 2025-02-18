package com.ot.automation.framework.octane.ui.spaces;

public class WorkspacesForm extends SpacesForm {

    public final DevOpsView devopsView = new DevOpsView(this);

    public WorkspacesForm() {
        super();
    }

    public enum Tabs {
        ENTITY_LABELING("entitiesLabeling"),
        ENTITIES("entities"),
        LISTS("lists"),
        RELEASES("releases"),
        TEAMS("teams"),
        USERS("users"),
        CREDENTIALS("credentials"),
        CALENDARS("calendars"),
        DEVOPS("devops"),
        ACTIVITY_LOG("activityLog"),
        DEMO_DATA("demoData"),
        DEFAULT("defaults");

        private final String value;

        Tabs(String value) {
            this.value = value;
        }

        public String getValue() {
            return "workspace-header-tab-" + value;
        }
    }
}
