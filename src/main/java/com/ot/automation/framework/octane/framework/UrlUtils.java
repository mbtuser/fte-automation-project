package com.ot.automation.framework.octane.framework;

import com.ot.automation.framework.octane.framework.Entities.Entity;
import com.ot.automation.framework.octane.framework.Entities.ManualTest;
import com.ot.automation.framework.octane.framework.Entities.ModelBasedTest;
import com.ot.automation.framework.octane.framework.Entities.TestSuite;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.*;

public class UrlUtils {
    private static String uiProjectUrl = "<server>/ui/?p=<sharedspaceId>/<workspaceId>";
    private static String base = "<server>";

//    private static String uiProjectUrl = "<scheme>://<server>:<port><contextPath>/ui/?p=<sharedspaceId>/<workspaceId>";
//    private static String base = "<scheme>://<server>:<port><contextPath>";
//    private static String scheme = SCHEME;
//    private static String server = SERVER;
//    private static String port = PORT;
//    private static String contextPath = CONTEXT_PATH;

    public static String getEntityTypeForUrl(Entity.Data entity) {
        String entityType = entity.getEntityType();
        if (entityType != null) {
            if (entityType.equals(ManualTest.ENTITY_TYPE) ||
                    entityType.equals(TestSuite.ENTITY_TYPE) ||
                    entityType.equals(ModelBasedTest.ENTITY_TYPE)) {
                entityType = "test";
            }
        }
        return entityType;
    }

    public static String getEntityTypeForUrl(String entityType) {
        if (entityType.equals(ManualTest.ENTITY_TYPE) ||
                entityType.equals(TestSuite.ENTITY_TYPE)) {
            entityType = "test";
        }
        return entityType;
    }

    public static String getUiProjectUrl() {
        return getUrl(uiProjectUrl);
    }

//    public static String getBaseUrl() {
//        String url = base.replace("<scheme>", scheme);
//        url = url.replace("<server>", server);
//        if (port != null && !port.isEmpty()) {
//            url = url.replace(":<port>", ":" + port);
//        } else {
//            url = url.replace(":<port>", "");
//        }
//        url = url.replace("<contextPath>", contextPath);
//        return url;
//    }

    public static String getBaseUrl() {
        String url = base.replace("<server>", OCTANE_URL);
        return url;
    }

    private static String getUrl(String urlPattern) {
        String url = urlPattern.replace("<server>", OCTANE_URL);
        url = url.replace("<userName>", OCTANE_USERNAME);
        url = url.replace("<sharedspaceId>", OCTANE_TENANT_ID);
        url = url.replace("<workspaceId>", OCTANE_WORKSPACE_ID);

        return url;
    }

//    private static String getUrl(String urlPattern) {
//        String url = urlPattern.replace("<scheme>", scheme);
//        url = url.replace("<server>", server);
//
//        if (port != null && !port.isEmpty()) {
//            url = url.replace(":<port>", ":" + port);
//        } else {
//            url = url.replace(":<port>", "");
//        }
//        url = url.replace("<contextPath>", contextPath);
//        url = url.replace("<userName>", OCTANE_USERNAME);
//        url = url.replace("<sharedspaceId>", OCTANE_TENANT_ID);
//        url = url.replace("<workspaceId>", OCTANE_WORKSPACE_ID);
//
//        return url;
//    }
}