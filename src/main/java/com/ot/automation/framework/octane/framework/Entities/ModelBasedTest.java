package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ModelBasedTest extends Entity {
    public static String ENTITY_TYPE = "model_based_test";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public ModelBasedTest() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
    }

    public static ModelBasedTest.Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static ModelBasedTest.Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static ModelBasedTest.Data getEntityByName(String name) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, null);
    }

    public static ModelBasedTest.Data getEntityByName(String name, List<String> fields) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, fields);
    }

    public static ModelBasedTest.Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name));
        return new Data(entityData);
    }
}