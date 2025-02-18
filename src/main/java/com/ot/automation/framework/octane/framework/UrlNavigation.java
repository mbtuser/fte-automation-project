package com.ot.automation.framework.octane.framework;

import java.lang.annotation.*;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.*;

/**
 * The UrlNavigation annotation should be used by each test in the application.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface UrlNavigation {

    enum Module {
        /**
         * Blank module used for discarding content of current module
         */
        BLANK("#/blank"),
        HOME("#/home"),

        MODEL_BASED_TESTING("#/mbt-designer"),
        MODEL_BASED_TESTING_MODELS("#/mbt-designer/hierarchy/models"),
        MODEL_BASED_TESTING_UNITS("#/mbt-designer/hierarchy/units"),

        FUNCTIONAL_TESTING_EXECUTION_OVERVIEW("#fte/fte-overview"),
        FUNCTIONAL_TESTING_EXECUTION_TESTS("#/fte/fte-scheduler-tests"),
        FUNCTIONAL_TESTING_EXECUTION_SCHEDULES("#/fte/fte-scheduler"),
        FUNCTIONAL_TESTING_EXECUTION_PLAN("#/fte/fte-scheduler-run"),
        FUNCTIONAL_TESTING_EXECUTION_DEFECTS("#fte/execution-defects"),

        // Settings area:
        SITE_ADMIN("&site#/site-admin"),
        SITE_SERVERS("&site#/site-admin/site-admin-servers"),
        SITE_PARAMETERS("&site#/site-admin/site-params"),
        SITE("/ui/?site"),
        SITE_SPACES("&site#/site-admin/site-admin-shared-spaces"),
        SITE_API_ACCESS("&site#/site-admin/site-admin-api-access"),
        SITE_SESSIONS("&site#/site-admin/sessions"),
        SITE_USERS("&site#/site-admin/site-admin-users"),
        SITE_LICENSES("&site#/site-admin/site-admin-licenses"),

        SPACES("&admin#/settings/shared-space"),
        SPACES_USERS("&admin#/settings/shared-space/users"),
        SPACE_API_ACCESS("&admin#/settings/shared-space/applications"),
        SPACE_PARAMETERS("&admin#/settings/shared-space/params"),
        SPACE_PERMISSIONS("&admin#/settings/shared-space/roles"),
        SPACE_MAINTENANCE("&admin#/settings/shared-space/maintenance"),
        SPACE_DEFAULTS_EMAIL("&admin#/settings/shared-space/defaults/email"),
        SPACE_DEFAULTS_PURGING("&admin#/settings/shared-space/defaults/purging"),

        WORKSPACES("&admin#/settings/workspace"),
        WORKSPACES_ENTITY_LABELING("&admin#/settings/workspace/entity-labels"),
        WORKSPACES_ENTITIES("&admin#/settings/workspace/entities"),
        WORKSPACES_LISTS("&admin#/settings/workspace/lists"),
        WORKSPACES_PROGRAMS("&admin#/settings/workspace/programs"),
        WORKSPACES_RELEASES("&admin#/settings/workspace/releases"),
        WORKSPACES_TEAMS("&admin#/settings/workspace/teams"),
        WORKSPACES_USERS("&admin#/settings/workspace/users"),
        WORKSPACES_CALENDARS("&admin#/settings/workspace/calendars"),
        WORKSPACES_CREDENTIALS("&admin#/settings/workspace/credentials"),
        WORKSPACES_DEV_OPS("&admin#/settings/workspace/devops"),
        WORKSPACES_DEV_OPS_TEST_RUNNERS("&admin#/settings/workspace/devops/executors"),
        WORKSPACES_DEV_OPS_TEST_RUNNERS_RULES("&admin#/settings/workspace/devops/pa-rule-assignment"),
        WORKSPACES_DEMO_DATA("&admin#/settings/workspace/demo-data"),
        WORKSPACES_ACTIVITY_LOG("&admin#/settings/workspace/activity-log"),
        WORKSPACES_RECYCLE_BIN("&admin#/settings/workspace/recycle-bin"),
        WORKSPACE_DEFAULTS_DOCUMENT_REPORT("&admin#/settings/workspace/defaults/report");

        final String urlSuffix;

        Module(String urlSuffix) {
            this.urlSuffix = urlSuffix;
        }

        public String getUrl() {
            String urlPattern = "<server>/ui/?p=<sharedspaceId>/<workspaceId>";
//            String url = urlPattern.replace("<scheme>", SCHEME);

            String url = urlPattern.replace("<server>", OCTANE_URL);

//            url = url.replace("<server>", SERVER);
//            if (PORT != null && !PORT.isEmpty()) {
//                url = url.replace(":<port>", ":" + PORT);
//            } else {
//                url = url.replace(":<port>", "");
//            }
//            url = url.replace("<contextPath>", CONTEXT_PATH);
            url = url.replace("<sharedspaceId>", OCTANE_TENANT_ID);
            url = url.replace("<workspaceId>", OCTANE_WORKSPACE_ID);
            return url + urlSuffix;
        }

//        public String getUrl() {
//            String urlPattern = "<scheme>://<server>:<port><contextPath>/ui/?p=<sharedspaceId>/<workspaceId>";
//            String url = urlPattern.replace("<scheme>", SCHEME);
//            url = url.replace("<server>", SERVER);
//
//            if (PORT != null && !PORT.isEmpty()) {
//                url = url.replace(":<port>", ":" + PORT);
//            } else {
//                url = url.replace(":<port>", "");
//            }
//            url = url.replace("<contextPath>", CONTEXT_PATH);
//            url = url.replace("<sharedspaceId>", OCTANE_TENANT_ID);
//            url = url.replace("<workspaceId>", OCTANE_WORKSPACE_ID);
//            return url + urlSuffix;
//        }
    }

    /**
     * The module to which the framework should navigate before each test in the class begins
     */
    Module value() default Module.HOME;
}
