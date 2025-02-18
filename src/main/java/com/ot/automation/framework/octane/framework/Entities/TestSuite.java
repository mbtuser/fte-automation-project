package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class TestSuite extends Entity {
    public static String ENTITY_TYPE = "test_suite";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public TestSuite() {
        super(ENTITY_TYPE, RequestLevel.WORKSPACE);
    }

    public static class Fields extends FieldsCommon {
    }

    public static TestSuite.Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name));
        return new TestSuite.Data(entityData);
    }

    public static TestSuite.Data create(String name, String machineTemplate, String runMode) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "machine_template", machineTemplate, "run_mode", runMode));
        return new TestSuite.Data(entityData);
    }

    public static TestSuite.Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static TestSuite.Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static TestSuite.Data getEntityByName(String name) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, null);
    }

    public static TestSuite.Data getEntityByName(String name, List<String> fields) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, fields);
    }
}