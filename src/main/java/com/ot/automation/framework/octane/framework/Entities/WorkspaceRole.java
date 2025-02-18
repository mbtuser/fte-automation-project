package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_TENANT_ID;
import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_WORKSPACE_ID;
import static com.ot.automation.framework.octane.framework.UrlUtils.getEntityTypeForUrl;

public class WorkspaceRole extends Entity {
    public static String ENTITY_TYPE = "workspace_role";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.SPACE;

    public WorkspaceRole() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
    }

    public static Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static Data getRole(String roleId, String workspaceId) {
        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/" + ENTITY_TYPE + "s";
        resource += "?fields=workspace_id,workspace,role,name,id,api_access&query=\"(role={id=" + roleId + "});(workspace={id=" + workspaceId + "})\"";
        return Entity.getEntityCustomResource(resource);
    }

    public static Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name));
        return new Data(entityData);
    }
}