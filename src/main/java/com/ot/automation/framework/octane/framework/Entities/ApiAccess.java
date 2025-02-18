package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ApiAccess extends Entity {
    public static String ENTITY_TYPE = "api_access";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.SPACE;

    public ApiAccess() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
        public static final String CLIENT_ID = "client_id";
    }

    public static Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static Data getEntityByName(String name) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, null);
    }

    public static Data getEntityByName(String name, List<String> fields) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, fields);
    }

    public static Data getEntityByNameAndSpace(String name, String spaceId, List<String> fields) {
        return Entity.getEntityByNameAndSpace(ENTITY_TYPE, REQUEST_LEVEL, name, spaceId, fields);
    }

    public static Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name));
        return new Data(entityData);
    }

    public static Data create(String name, String secret, String workspaceRoleId) {
        JSONObject jsonObject = new JSONObject("{\"data\":[{\n" +
                "            \"id\":\"" + workspaceRoleId + "\",\n" +
                "            \"type\":\"workspace_role\"\n" +
                "         }]}");

        JSONObject entityData = createSiteLevel(ENTITY_TYPE, Map.of("workspace_roles", jsonObject, "name", name, "client_secret", secret));
        return new Data(entityData);
    }

    public static void delete(Entity.Data entity) {
        delete(ENTITY_TYPE, entity.getId(), true);
    }
}