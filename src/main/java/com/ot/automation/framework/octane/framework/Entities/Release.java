package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Release extends Entity {
    public static String ENTITY_TYPE = "release";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public Release() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
    }

    public static Release.Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static Release.Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static Release.Data getEntityByName(String name) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, null);
    }

    public static Release.Data getEntityByName(String name, List<String> fields) {
        return Entity.getEntityByName(ENTITY_TYPE, REQUEST_LEVEL, name, fields);
    }

    public static Entity.Data create(String name, LocalDateTime startTime, LocalDateTime endTime, int sprintDuration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String startDate = startTime.format(formatter);
        String endDate = endTime.format(formatter);
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "start_date", startDate, "end_date", endDate, "sprint_duration", sprintDuration));
        return new Data(entityData);
    }
}