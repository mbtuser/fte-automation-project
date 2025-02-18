package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;

import java.util.List;

public class SharedSpace extends Entity {
    public static String ENTITY_TYPE = "shared_space";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.SITE;

    public SharedSpace() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
        public static final String VALUE_EDGE_INSTANCE_ID = "ve_instance_id";
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
}