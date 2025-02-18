package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.RequestLevel;
import com.ot.automation.framework.octane.framework.RestCallResult;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_TENANT_ID;
import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_WORKSPACE_ID;
import static com.ot.automation.framework.octane.framework.UrlUtils.getEntityTypeForUrl;

public abstract class Entity {
    protected String ENTITY_TYPE;
    protected RequestLevel REQUEST_LEVEL;

    public Entity(String entityType, RequestLevel requestLevel) {
        this.ENTITY_TYPE = entityType;
        this.REQUEST_LEVEL = requestLevel;
    }

    //    private static RequestLevel getEntityLevel(Entity entity) {
//        return entity.REQUEST_LEVEL;
//    }
    public static Entity.Data getEntityById(String entityType, RequestLevel requestLevel, String id, List<String> fields) {
//        JSONObject entityData = getEntityByIdJson(entityType, id, fields);
//        JSONObject entityData = getEntityByField(entityType, "id", id, fields);

        Map<String, Object> byFields = new HashMap<>();
        byFields.put("id", id);

        JSONObject entityData = getEntityJsonObject(entityType, requestLevel, byFields, fields);
        return new Entity.Data(entityData);
    }

    public static Entity.Data getEntityByName(String entityType, RequestLevel requestLevel, String name, List<String> fields) {
        Map<String, Object> byFields = new HashMap<>();
        byFields.put("name", "%27" + name + "%27");

        JSONObject entityData = getEntityJsonObject(entityType, requestLevel, byFields, fields);
        return new Entity.Data(entityData);
    }

    public static Entity.Data getEntityByNameAndSpace(String entityType, RequestLevel requestLevel, String name, String spaceId, List<String> fields) {
        Map<String, Object> byFields = new HashMap<>();
        byFields.put("name", "%27" + name + "%27");
        byFields.put("(shared_spaces", "{id=" + spaceId + "})");

        JSONObject entityData = getEntityJsonObject(entityType, requestLevel, byFields, fields);
        return new Entity.Data(entityData);
    }

    public static Entity.Data getEntityByField(String entityType, RequestLevel requestLevel, String fieldName, String fieldValue, List<String> fieldsToRetrieve) {
        Map<String, Object> byFields = new HashMap<>();
        byFields.put(fieldName, fieldValue);

        JSONObject entityData = getEntityJsonObject(entityType, requestLevel, byFields, fieldsToRetrieve);
        return new Entity.Data(entityData);
    }

    private static JSONObject getEntityJsonObject(String ENTITY_TYPE, RequestLevel requestLevel, Map<String, Object> byFields, List<String> fieldsToRetrieve) {
        Boolean byId = (byFields.entrySet().iterator().next().getKey() == "id");
        String entityType = byId ? ENTITY_TYPE : getEntityTypeForUrl(ENTITY_TYPE);

        String resource = entityType.endsWith("s") ? entityType + "es" : entityType + "s";

        switch (requestLevel) {
            case SITE:
                resource = "/admin/" + resource;
                break;
            case SPACE:
                resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/" + resource;
                break;
            case WORKSPACE:
                resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + resource;
                break;
            default:
                throw new IllegalArgumentException("Unknown request level: " + requestLevel);
        }

//        if (isSiteLevel) {
//            resource = "/admin/" + resource;
//        } else {
//            resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + resource;
//        }
//        resource += byId ? "/" + byValue : "?text_search={\"type\":\"context\",\"text\":\"" + byValue + "\"}";

        StringJoiner queryJoiner = new StringJoiner(";");
        for (Map.Entry<String, Object> entry : byFields.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            String fieldValueString;
//            if (fieldValue instanceof String) {
//                fieldValueString = "%27" + fieldValue + "%27";
//            } else {
//                fieldValueString = fieldValue.toString();
//            }
            String query = fieldName + "%3D" + fieldValue.toString();
            queryJoiner.add(query);
        }

        String query = queryJoiner.toString();
        resource += byId ? "/" + byFields.entrySet().iterator().next().getValue() : "?query=%22(" + query + ")%22";

        if (fieldsToRetrieve != null) {
            resource += byId ? "?" : "&";
            resource += "fields=";

            for (int i = 0; i < fieldsToRetrieve.size(); i++) {
                resource += fieldsToRetrieve.get(i);
                if (i < fieldsToRetrieve.size() - 1) {
                    resource += ",";
                }
            }
        }

        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);
        String responseBody = getEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    public static Entity.Data getEntityCustomResource(String resource) {
        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);
        String responseBody = getEntityResult.getResponseBody();
        JSONObject entityData = new JSONObject(responseBody);
        return new Entity.Data(entityData);
    }

    private static JSONObject getEntityJsonObjectRecent(String ENTITY_TYPE, String fieldName, String fieldValue, List<String> fields) {
        Boolean byId = (fieldName == "id");
        String entityType = byId ? ENTITY_TYPE : getEntityTypeForUrl(ENTITY_TYPE);
        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + entityType + "s";
//        resource += byId ? "/" + byValue : "?text_search={\"type\":\"context\",\"text\":\"" + byValue + "\"}";

        String query = fieldName + "%3D%27" + fieldValue + "%27";
        resource += byId ? "/" + fieldValue : "?query=%22(" + query + ")%22";

        if (fields != null) {
            resource += byId ? "?" : "&";
            resource += "fields=";

            for (int i = 0; i < fields.size(); i++) {
                resource += fields.get(i);
                if (i < fields.size() - 1) {
                    resource += ",";
                }
            }
        }

        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);
        String responseBody = getEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    private static JSONObject getEntityJsonOld(String ENTITY_TYPE, String byValue, List<String> fields, Boolean byId) {
        String entityType = byId ? ENTITY_TYPE : getEntityTypeForUrl(ENTITY_TYPE);
        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + entityType + "s";
//        resource += byId ? "/" + byValue : "?text_search={\"type\":\"context\",\"text\":\"" + byValue + "\"}";

        resource += byId ? "/" + byValue : "?query=%22(name%3D%27" + byValue + "%27)%22";

        if (fields != null) {
            resource += byId ? "?" : "&";
            resource += "fields=";

            for (int i = 0; i < fields.size(); i++) {
                resource += fields.get(i);
                if (i < fields.size() - 1) {
                    resource += ",";
                }
            }
        }

        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);
        String responseBody = getEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

//    private static JSONObject getEntityByIdJson(String ENTITY_TYPE, String id, List<String> fields) {
//        return getEntityJsonObject(ENTITY_TYPE, "id", id, fields);
//    }
//
//    private static JSONObject getEntityByNameJson(String ENTITY_TYPE, String name, List<String> fields) {
//        return getEntityJsonObject(ENTITY_TYPE, "name", name, fields);
//    }
//
//    private static JSONObject getEntityJson(String ENTITY_TYPE, String fieldName, String fieldValue, List<String> fields) {
//        return getEntityJsonObject(ENTITY_TYPE.toString(), fieldName, fieldValue, fields);
//    }

    //  (String ENTITY_TYPE, String fieldName, String fieldValue, List<String> fields) {
    protected static JSONObject create(String entityType, Map<String, Object> fieldValues) {
        String createResource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + entityType + "s";
        String createBody = "{\n" +
                "   \"data\":[\n" +
                "      {\n" +
                getFields(fieldValues) +
                "      }\n" +
                "   ]\n" +
                "}";

        RestCallResult createEntityResult = GeneralUtils.makeRestCallOctane("POST", createResource, createBody);
        String responseBody = createEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    protected static JSONObject createSiteLevel(String entityType, Map<String, Object> fieldValues) {
        String entityTypeResource = entityType.endsWith("s") ? entityType + "es" : entityType + "s";
        String createResource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/" + entityTypeResource;
        String createBody = "{\n" +
                "   \"data\":[\n" +
                "      {\n" +
                getFields(fieldValues) +
                "      }\n" +
                "   ]\n" +
                "}";

        RestCallResult createEntityResult = GeneralUtils.makeRestCallOctane("POST", createResource, createBody);
        String responseBody = createEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }


    protected static JSONObject update(String entityType, String entityId, Map<String, Object> fieldValues) {
        String updateResource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + entityType + "s/" + entityId;
        String updateBody = "{\n" +
                "\"id\":\"" + entityId + "\"," +
                getFields(fieldValues) +
                "}";

        RestCallResult updateEntityResult = GeneralUtils.makeRestCallOctane("PUT", updateResource, updateBody);
        String responseBody = updateEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    protected static JSONObject delete(String entityType, String entityId, Boolean isSiteLevel) {
        String entityTypeResource = entityType.endsWith("s") ? entityType + "es" : entityType + "s";
        String deleteResourcePrefix = isSiteLevel ? "/admin/" : "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/";
        String deleteResource = deleteResourcePrefix + entityTypeResource + "?query=\"(id='" + entityId + "')\"";
        RestCallResult deleteEntityResult = GeneralUtils.makeRestCallOctane("DELETE", deleteResource, null);
        String responseBody = deleteEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    protected static JSONObject delete(String entityType, String entityId, RequestLevel requestLevel, String query) {
        String resource = entityType.endsWith("s") ? entityType + "es" : entityType + "s";

        switch (requestLevel) {
            case SITE:
                resource = "/admin/" + resource;
                break;
            case SPACE:
                resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/" + resource;
                break;
            case WORKSPACE:
                resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + resource;
                break;
            default:
                throw new IllegalArgumentException("Unknown request level: " + requestLevel);
        }

        resource += "?query=\"(id='" + entityId + "')\"";
        if (query != null){
            resource += "&" + query;
        }
        RestCallResult deleteEntityResult = GeneralUtils.makeRestCallOctane("DELETE", resource, null);
        
        String responseBody = deleteEntityResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    private static String getFields(Map<String, Object> fieldValues) {
        StringBuilder jsonBody = new StringBuilder("");
        boolean firstField = true;

        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            if (!firstField) {
                jsonBody.append(",");
            }
            String key = entry.getKey();
            Object value = entry.getValue();
            String valueString;
            if (value instanceof String) {
                valueString = "\"" + value + "\"";
            } else {
                valueString = String.valueOf(value);
            }
            jsonBody.append("\"").append(key).append("\":").append(valueString);
            firstField = false;
        }
        return jsonBody.toString();
    }

    public static class Data extends Object {
        Map<String, String> fieldsData = new HashMap<>();
        String entityType;// = ENTITY_TYPE;// this.getEntityType();

        public Data(JSONObject entityData) {
            if (entityData.has("data")) {
                JSONArray dataArray = entityData.getJSONArray("data");
                if (dataArray.length() > 0) {
                    JSONObject dataObject = dataArray.getJSONObject(0);  // Get the first element
                    for (String key : dataObject.keySet()) {
                        fieldsData.put(key, String.valueOf(dataObject.get(key)));
                    }
                    entityType = fieldsData.get("type");
                }
            } else {
                Iterator<String> keys = entityData.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    fieldsData.put(key, String.valueOf(entityData.get(key)));
                }
            }
        }

        public String getField(String field) {
            return fieldsData.get(field);
        }

        public String getId() {
            return getField("id");
        }

        public String getName() {
            return getField("name");
        }

        public String getEntityType() {
            return fieldsData.get("type");
//            return entityType;
        }
    }
}