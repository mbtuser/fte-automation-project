package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Model extends Entity {
    public static String ENTITY_TYPE = "model_item";
    public static String ENTITY_SUBTYPE = "model";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public Model() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
//        public static final String STATUS = "status";
    }

    public static Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_SUBTYPE, REQUEST_LEVEL, id, null);
    }

    public static Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_SUBTYPE, REQUEST_LEVEL, id, fields);
    }

    public static Data getEntityByName(String name) {
        return Entity.getEntityByName(ENTITY_SUBTYPE, REQUEST_LEVEL, name, null);
    }

    public static Data getEntityByName(String name, List<String> fields) {
        return Entity.getEntityByName(ENTITY_SUBTYPE, REQUEST_LEVEL, name, fields);
    }

    public static Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "subtype", "model"));
        return new Model.Data(entityData);
    }

    public static Data create(String name, ModelFolder.Data parent) {
        JSONObject jsonObject = new JSONObject("{\n" +
                "            \"type\":\"model_item\",\n" +
                "            \"id\":\"" + parent.getId() + "\"\n" +
                "         }");

        String parentJson =
                "{\n" +
                        "            \"type\":\"model_item\",\n" +
                        "            \"id\":\"" + parent.getId() + "\"\n" +
                        "         }";
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "subtype", "model", "parent", jsonObject));
        return new Data(entityData);
    }


    //    protected static JSONObject update(String entityType, String entityId, Map<String, Object> fieldValues) {
    public static Data update(Model.Data model, String field, String value) {
        JSONObject jsonObject = new JSONObject(value);
        JSONObject entityData = update(ENTITY_TYPE, model.getId(), Map.of(field, jsonObject));
        return new Data(entityData);
    }
}