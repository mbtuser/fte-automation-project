package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;

import java.util.List;

public class SuiteRunSchedulerRun extends Entity {
    public static String ENTITY_TYPE = "suite_run_scheduler_run";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public SuiteRunSchedulerRun() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
        public static final String STATUS = "status";
        public static final String GENERAL_RUN_STATUS = "general_run_status";
        public static final String ERROR_MESSAGE = "error_message";
        public static final String TOTAL_SUITE_COUNT = "total_suite_count";
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

//    public static Data create(String name, LocalDateTime startTime, LocalDateTime endTime, int sprintDuration) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        String startDate = startTime.format(formatter);
//        String endDate = endTime.format(formatter);
//        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name, "start_date", startDate, "end_date", endDate, "sprint_duration", sprintDuration));
//        return new Data(entityData);
//    }
}