package com.ot.automation.framework.octane.framework.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class ModelBasedTestPath {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String hash = UUID.randomUUID().toString();

    @JsonProperty("path_structure")
    private String pathStructure;

    @JsonProperty("test_model")
    private String testModel = "{" +
            "            \"id\": %s," +
            "            \"type\": \"model_item\"," +
            "            \"subtype\": \"model\"" +
            "      }";

    public ModelBasedTestPath(List<String> unitsPath, String modelId) {
        this.testModel = String.format(testModel, modelId);

        String unitsStructure = "";
        for (int i = 1; i <= unitsPath.size(); i++) {
            unitsStructure = unitsStructure +
                    "          {" +
                    "            \"id\": \"" + i + "\"," +
                    "            \"modelItemId\": %s," +
                    "            \"type\": \"unit\"," +
                    "            \"position\": \"-962 " + (7 - i) + (i * 10 + 46) + "\"," +
                    "            \"size\": null" +
                    "          },";
        }

        String nodesStructure = "";
        for (int i = 1; i <= unitsPath.size(); i++) {
            nodesStructure = nodesStructure +
                    "          {" +
                    "            \"id\": \"" + -i + "\"," +
                    "            \"textPosition\": null," +
                    "            \"source\": \"" + (i - 1) + "\"," +
                    "            \"target\": \"" + i + "\"," +
                    "            \"type\": null" +
                    "          },";
        }

        String pathsStructure = "{" +
                "        \"nodes\": [" +
                "          {" +
                "            \"id\": \"0\"," +
                "            \"text\": \"Start\"," +
                "            \"type\": \"start\"," +
                "            \"position\": \"-962 -746\"," +
                "            \"size\": null" +
                "          }," + unitsStructure +
                "          {" +
                "            \"id\": \"6\"," +
                "            \"text\": \"Decision\"," +
                "            \"type\": \"gateway\"," +
                "            \"position\": \"-962 -206\"," +
                "            \"size\": null" +
                "          }," +
                "          {" +
                "            \"id\": \"7\"," +
                "            \"text\": \"End\"," +
                "            \"type\": \"end\"," +
                "            \"position\": \"-962 -116\"," +
                "            \"size\": null" +
                "          }" +
                "        ]," +
                "        \"edges\": [" + nodesStructure +
                "          {" +
                "            \"id\": \"0\"," +
                "            \"textPosition\": null," +
                "            \"source\": \"0\"," +
                "            \"target\": \"0\"," +
                "            \"type\": null" +
                "          }" +
                "        ]" +
                "      }";

        this.pathStructure = String.format(pathsStructure, unitsPath.toArray());
    }

    public JSONObject getPathStructure() throws JsonProcessingException {
        return objectMapper.readValue(this.pathStructure, JSONObject.class);
    }

    public String getHash() {
        return hash;
    }

    public JSONObject getTestModel() throws JsonProcessingException {
        return objectMapper.readValue(this.testModel, JSONObject.class);
    }
}