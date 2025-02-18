package com.ot.automation.framework.octane.framework;

import com.ot.automation.framework.octane.framework.Entities.Entity;
import com.ot.automation.framework.octane.framework.Entities.Model;
import com.ot.automation.framework.octane.framework.Entities.Unit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MBTUtils {

    public static class DiagramNodes {
        public static final String MODEL = "model";
        public static final String UNIT = "unit";
        public static final String PLACEHOLDER = "placeholder";
        public static final String DECISION = "decision";
        public static final String COMMENT = "comment";
        public static final String START_NODE = "start_node";
        public static final String END_NODE = "end_node";
    }

    public static void createDiagram(Model.Data parentModel, List<Model.Data> models, List<Unit.Data> units, List<Object> comments, Integer decisions, Integer placeholders, Integer endPoints, List<Object> transitions) {
        int index = 1;
        // Initialize elements lists
        Collection<Object> nodes = new ArrayList();
        Collection<Object> transitionsFinal = new ArrayList();
        ArrayList<JSONObject> modelNodes = new ArrayList();
        ArrayList<JSONObject> unitNodes = new ArrayList();
        ArrayList<JSONObject> decisionNodes = new ArrayList();
        ArrayList<JSONObject> placeholderNodes = new ArrayList();
        ArrayList<JSONObject> commentNodes = new ArrayList();
        ArrayList<JSONObject> endPointNodes = new ArrayList();

        //region Create the diagram elements (decision points, placeholders, units, models and comments)
        JSONObject startNode = createStartNode(0);

        // If a list of MODELS is passed, create a model node for each one
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                modelNodes.add(createNode(models.get(i), index++));
            }
        }

        // If a list of UNITS is passed, create a unit node for each one
        if (units != null) {
            for (int i = 0; i < units.size(); i++) {
                unitNodes.add(createNode(units.get(i), index++));
            }
        }

        // If a number of DECISION POINTS is passed, create a gateway node for each one
        if (decisions != null) {
            for (int i = 0; i < decisions; i++) {
                decisionNodes.add(createGatewayNode(index++));
            }
        }

        // If a number of PLACEHOLDERS is passed, create a placeholder node for each one
        if (placeholders != null) {
            for (int i = 0; i < placeholders; i++) {
                placeholderNodes.add(createPlaceholderNode(index++));
            }
        }

        // If a number of END POINTS is passed, create an end node for each one
        if (endPoints != null) {
            for (int i = 0; i < endPoints; i++) {
                endPointNodes.add(createEndNode(index++));
            }
        }

        // If a list of COMMENTS is passed, create a comment node for each one
        if (comments != null) {
            for (Object comment : comments) {
                String commentText = ((List<Object>) comment).get(0).toString();
                Object linkedNodeObj = ((List<Object>) comment).get(1);
                JSONObject linkedNode = null;
                JSONObject commentNode = createCommentNode(commentText, true, index++);
                commentNodes.add(commentNode);

                // If the comment is linked to another element
                if (linkedNodeObj != null) {
                    // If the comment is linked to a MODEL:
                    if (linkedNodeObj instanceof Model.Data) {
                        for (int i = 0; i < models.size(); i++) {
                            if (models.get(i).equals(linkedNodeObj)) {
                                linkedNode = modelNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the comment is linked to a UNIT:
                    else if (linkedNodeObj instanceof Unit.Data) {
                        for (int i = 0; i < units.size(); i++) {
                            if (units.get(i).equals(linkedNodeObj)) {
                                linkedNode = unitNodes.get(i);
                                break;
                            }
                        }
                    }
                    transitionsFinal.add(createCommentTransition(commentNode, linkedNode, index++));
                }
            }
        }
        //endregion

        //region Create the transitions (if a list of TRANSITIONS is passed)
        if (transitions != null) {
            for (Object transition : transitions) {
                // For each transition, initialize t1 (source node) and t2 (destination node)
                JSONObject t1 = null, t2 = null;
                Object sourceElementList = ((List<Object>) transition).get(0);
                Object destinationElementList = ((List<Object>) transition).get(1);
                String transitionText = null;
                String fromPort = null;

                // 3rd element is the transition text
                if (((List<Object>) transition).size() > 2) {
                    transitionText = Objects.toString(((List<Object>) transition).get(2), null);

                    // 4th element is the decision node port
                    if (((List<Object>) transition).size() > 3) {
                        fromPort = Objects.toString(((List<Object>) transition).get(3), null);
                    }
                }

                //region Checking the transition SOURCE nodes:
                // If the source element is a List (used in case an element is duplicated and we need to specify which one)
                if (sourceElementList instanceof List) {
                    Object sourceElement = ((List<Object>) sourceElementList).get(0);
                    int expectedPosition = (int) ((List<Object>) sourceElementList).get(1);
                    int pos = 1;


                    // If the transition source node is DECISION POINT:
                    if (sourceElement.toString().contains(DiagramNodes.DECISION)) {
                        for (int i = 0; i < decisions; i++) {
                            if (pos < expectedPosition) {
                                pos++;
                            } else {
                                t1 = decisionNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the transition source node is PLACEHOLDER:
                    else if (sourceElement.toString().contains(DiagramNodes.PLACEHOLDER)) {
                        for (int i = 0; i < placeholders; i++) {
                            if (pos < expectedPosition) {
                                pos++;
                            } else {
                                t1 = placeholderNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the transition source node is MODEL:
                    else if (((Entity.Data) sourceElement).getEntityType().equals("model")) {
                        for (int i = 0; i < models.size(); i++) {
                            if (models.get(i).equals(sourceElement)) {
                                if (pos < expectedPosition) {
                                    pos++;
                                } else {
                                    t1 = modelNodes.get(i);
                                    break;
                                }
                            }
                        }
                    }
                    // If the transition source node is UNIT:
                    else if (((Entity.Data) sourceElement).getEntityType().equals("unit")) {
                        for (int i = 0; i < units.size(); i++) {
                            if (units.get(i).equals(sourceElement)) {
                                if (pos < expectedPosition) {
                                    pos++;
                                } else {
                                    t1 = unitNodes.get(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                // If the source element is a simple element AND
                // If the transition source node is DECISION POINT:
                else if (sourceElementList.toString().contains(DiagramNodes.DECISION)) {
                    t1 = decisionNodes.get(0);
                }
                // If the transition source node is PLACEHOLDER:
                else if (sourceElementList.toString().contains(DiagramNodes.PLACEHOLDER)) {
                    t1 = placeholderNodes.get(0);
                }
                // If the transition source node is START NODE:
                else if (sourceElementList.equals(DiagramNodes.START_NODE)) {
                    t1 = startNode;
                }
                // If the transition source node is MODEL:
                else if (((Entity.Data) sourceElementList).getEntityType().equals("model")) {
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).equals(sourceElementList)) {
                            t1 = modelNodes.get(i);
                            break;
                        }
                    }
                }
                // If the transition source node is UNIT:
                else if (((Entity.Data) sourceElementList).getEntityType().equals("unit")) {
                    for (int i = 0; i < units.size(); i++) {
                        if (units.get(i).equals(sourceElementList)) {
                            t1 = unitNodes.get(i);
                            break;
                        }
                    }
                }
                //endregion

                //region Checking the transition DESTINATION nodes:
                // If the destination element is a List (used in case an element is duplicated and we need to specify which one)
                if (destinationElementList instanceof List) {
                    Object destinationElement = ((List<Object>) destinationElementList).get(0);
                    int expectedPosition = (int) ((List<Object>) destinationElementList).get(1);
                    int pos = 1;

                    // If the transition destination node is DECISION POINT:
                    if (destinationElement.toString().contains(DiagramNodes.DECISION)) {
                        for (int i = 0; i < decisions; i++) {
                            if (pos < expectedPosition) {
                                pos++;
                            } else {
                                t2 = decisionNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the transition destination node is END POINT:
                    else if (destinationElement.toString().contains(DiagramNodes.END_NODE)) {
                        for (int i = 0; i < endPoints; i++) {
                            if (pos < expectedPosition) {
                                pos++;
                            } else {
                                t2 = endPointNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the transition destination node is PLACEHOLDER:
                    else if (destinationElement.toString().contains(DiagramNodes.PLACEHOLDER)) {
                        for (int i = 0; i < placeholders; i++) {
                            if (pos < expectedPosition) {
                                pos++;
                            } else {
                                t2 = placeholderNodes.get(i);
                                break;
                            }
                        }
                    }
                    // If the transition destination node is MODEL:
                    else if (destinationElement instanceof Model.Data) {
                        for (int i = 0; i < models.size(); i++) {
                            if (models.get(i).equals(destinationElement)) {
                                if (pos < expectedPosition) {
                                    pos++;
                                } else {
                                    t2 = modelNodes.get(i);
                                    break;
                                }
                            }
                        }
                    }
                    // If the transition destination node is UNIT:
                    else if (destinationElement instanceof Unit.Data) {
                        for (int i = 0; i < units.size(); i++) {
                            if (units.get(i).equals(destinationElement)) {
                                if (pos < expectedPosition) {
                                    pos++;
                                } else {
                                    t2 = unitNodes.get(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                // If the destination element is a simple element AND
                // If the transition destination node is MODEL:

                // If the transition destination node is DECISION POINT:
                else if (destinationElementList.toString().contains(DiagramNodes.DECISION)) {
                    t2 = decisionNodes.get(0);
                }
                // If the transition destination node is PLACEHOLDER:
                else if (destinationElementList.toString().contains(DiagramNodes.PLACEHOLDER)) {
                    t2 = placeholderNodes.get(0);
                }
                // If the transition destination node is END NODE:
                else if (destinationElementList.equals(DiagramNodes.END_NODE)) {
                    t2 = endPointNodes.get(0);
                } else if (((Entity.Data) destinationElementList).getEntityType().equals("model")) {
                    for (int i = 0; i < models.size(); i++) {
                        if (models.get(i).equals(destinationElementList)) {
                            t2 = modelNodes.get(i);
                            break;
                        }
                    }
                }
                // If the transition destination node is UNIT:
                else if (((Entity.Data) destinationElementList).getEntityType().equals("unit")) {
                    for (int i = 0; i < units.size(); i++) {
                        if (units.get(i).equals(destinationElementList)) {
                            t2 = unitNodes.get(i);
                            break;
                        }
                    }
                }
                //endregion

                // Adding all transitions to the final transitions array
                transitionsFinal.add(createTransition(t1, t2, index++, transitionText, fromPort));
            }
        }
        //endregion

        //region Add all elements to the nodes array
        nodes.add(startNode);
        for (JSONObject node : decisionNodes) {
            nodes.add(node);
        }
        for (JSONObject node : placeholderNodes) {
            nodes.add(node);
        }
        for (JSONObject node : modelNodes) {
            nodes.add(node);
        }
        for (JSONObject node : unitNodes) {
            nodes.add(node);
        }
        for (JSONObject node : commentNodes) {
            nodes.add(node);
        }
        for (JSONObject node : endPointNodes) {
            nodes.add(node);
        }
        //endregion

        // Create JSON Arrays from nodes and update the model with the diagram
        JSONArray nodesArray = createJsonArrayFromNodes(nodes);
        JSONArray transitionsArray = createJsonArrayFromNodes(transitionsFinal);

        Model.update(parentModel, "model_structure", createModelData(nodesArray, transitionsArray).toString());
    }

    public static JSONObject createModelData(JSONArray nodesArray, JSONArray transitionsArray) {
        JSONObject modelData = new JSONObject();
        modelData.put("nodes", nodesArray);
        modelData.put("transitions", transitionsArray);
        modelData.put("strategy", createStrategyNode());
        return modelData;
    }

    public static JSONArray createJsonArrayFromNodes(JSONObject... nodes) {
        return new JSONArray(nodes);
    }

    public static JSONArray createJsonArrayFromNodes(Collection<Object> collection) {
        return new JSONArray(collection);
    }

    public static String countPosition(int i) {
        return "0 " + 120 * i;
    }

    public static JSONObject createNode(Entity.Data nodeToCreate, int index) {
        JSONObject node = new JSONObject();
        node.put("id", index);
        node.put("type", nodeToCreate.getField("type").toString().toLowerCase());
        node.put("position", countPosition(index));
        node.put("modelItemId", nodeToCreate.getId());
        return node;
    }

    public static JSONObject createStartNode(int index) {
        /*
        { "id": "0",
           "type": "start",
           "position": "-1230 -313",
           "text": "Start"  },
        */
        JSONObject node = new JSONObject();
        node.put("id", index);
        node.put("type", "start");
        node.put("position", countPosition(index));
        node.put("text", "Start");

        return node;
    }

    public static JSONObject createStrategyNode() {
        JSONObject node = new JSONObject();
        node.put("strategy", "FULL_COVERAGE");
        node.put("includeUnitIds", new JSONArray());
        node.put("avoidUnitIds", new JSONArray());
        node.put("unitPhasesIds", new JSONArray());
        node.put("backlogTypes", new JSONArray());
        node.put("releases", new JSONArray());
        node.put("milestones", new JSONArray());
        return node;
    }

    public static JSONObject createEndNode(int index) {
        /*
        { "id": "0",
           "type": "start",
           "position": "-1230 -313",
           "text": "Start"  },
        */
        JSONObject node = new JSONObject();
        node.put("id", index);
        node.put("type", "end");
        node.put("position", countPosition(index));
        node.put("text", "End");

        return node;
    }

    public static JSONObject createGatewayNode(int index) {
        /*
        {"id": "3",
          "type": "gateway",
          "position": "-1229 -229",
          "text": "Decision"  },
        */
        JSONObject node = new JSONObject();
        node.put("id", index);
        node.put("type", "gateway");
        node.put("position", countPosition(index));
        node.put("text", "Decision");

        return node;
    }

    public static JSONObject createPlaceholderNode(int index) {
        /*  { "id": 2,
              "type": "placeholder",
              "position": "-1250 15",
              "text": "Placeholder" },
        */
        JSONObject node = new JSONObject();
        node.put("id", index);
        node.put("type", "placeholder");
        node.put("position", countPosition(index));
        node.put("text", "Placeholder");

        return node;
    }

    public static JSONObject createCommentNode(String commentText, boolean isExpanded, int index) {
        /*  { "expanded": true,
              "id": 4,
              "position": "-1250 15",
              "text": "Comment",
              "type": "comment" },
        */
        JSONObject node = new JSONObject();
        node.put("expanded", isExpanded);
        node.put("id", index);
        node.put("position", countPosition(index));
        node.put("text", commentText);
        node.put("type", "comment");
        return node;
    }

    public static JSONObject createTransition(JSONObject source, JSONObject target, int index, String transitionText, String fromPort) {
        JSONObject transition = new JSONObject();
        transition.put("id", index);
        transition.put("source", source.get("id"));
        transition.put("target", target.get("id"));
        transition.put("type", "");
        transition.put("text", transitionText);
        transition.put("fromPort", fromPort);

        return transition;
    }

    public static JSONObject createCommentTransition(JSONObject source, JSONObject target, int index) {
        JSONObject transition = new JSONObject();
        transition.put("id", index);
        transition.put("source", source.get("id"));
        transition.put("target", target.get("id"));
        transition.put("type", "comment_node");

        return transition;
    }
}