package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Unit extends Entity {
    public static String ENTITY_TYPE = "model_item";
    public static String ENTITY_SUBTYPE = "unit";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public Unit() {
        super(ENTITY_TYPE, RequestLevel.WORKSPACE);
    }

    public static class Fields extends FieldsCommon {
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

    public static Data getEntityByRepositoryPath(String repositoryPath) {
        return Entity.getEntityByField(ENTITY_SUBTYPE, REQUEST_LEVEL, "repository_path", repositoryPath, null);
    }

    public static Data getEntityByRepositoryPath(String repositoryPath, List<String> fields) {
        return Entity.getEntityByField(ENTITY_SUBTYPE, REQUEST_LEVEL, "repository_path", repositoryPath, fields);
    }

    public static Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "subtype", "unit"));
        return new Data(entityData);
    }

    public static Data create(String name, Data parent) {
        JSONObject jsonObject = new JSONObject("{\n" +
                "            \"type\":\"model_item\",\n" +
                "            \"id\":\"" + parent.getId() + "\"\n" +
                "         }");

        String parentJson =
                "{\n" +
                        "            \"type\":\"model_item\",\n" +
                        "            \"id\":\"" + parent.getId() + "\"\n" +
                        "         }";
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "subtype", "unit", "parent", jsonObject));
        return new Data(entityData);
    }

    public static Data update(Data model, String field, String value) {
        JSONObject jsonObject = new JSONObject(value);
        JSONObject entityData = update(ENTITY_TYPE, model.getId(), Map.of(field, jsonObject));
        return new Data(entityData);
    }
}