package com.ot.automation.framework.octane.framework.Entities;

import com.ot.automation.framework.octane.framework.FieldsCommon;
import com.ot.automation.framework.octane.framework.RequestLevel;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_TENANT_ID;
import static com.ot.automation.framework.octane.framework.BaseAutomationTest.OCTANE_WORKSPACE_ID;

public class FteArtifact extends Entity {
    public static String ENTITY_TYPE = "fte_artifact";
    public static RequestLevel REQUEST_LEVEL = RequestLevel.WORKSPACE;

    public FteArtifact() {
        super(ENTITY_TYPE, REQUEST_LEVEL);
    }

    public static class Fields extends FieldsCommon {
        public static final String UPLOAD_DATE = "upload_date";
        public static final String FILE_NAME = "file_name";
        public static final String UPLOAD_BY = "upload_by";
        public static final String IS_CONNECTED = "is_connected";
        public static final String SIZE = "size";
        public static final String VERSION = "version";
        public static final String ERROR_MESSAGE = "error_message";
        public static final String UPLOAD_PHASE = "fte_artifact_upload_phase";
    }

    public static Data getEntityById(String id) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, null);
    }

    public static Data getEntityById(String id, List<String> fields) {
        return Entity.getEntityById(ENTITY_TYPE, REQUEST_LEVEL, id, fields);
    }

    public static Data getEntityByUploaderAndJarFilename(String uploadBy, String jarFileName, String cloudRunnerId) {
        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/" + ENTITY_TYPE + "s";
        resource += "?fields=file_name,upload_by,upload_date,size,version,error_message,is_active,id,fte_artifact_upload_phase" +
                "&query=\"(upload_by='" + uploadBy + "*';file_name='" + jarFileName + "';(cloud_test_runner={(id='" + cloudRunnerId + "')}))\"";
        return Entity.getEntityCustomResource(resource);
    }

    public static Data create(String name) {
        JSONObject entityData = create(ENTITY_TYPE, Map.of("name", name));
        return new Data(entityData);
    }
}