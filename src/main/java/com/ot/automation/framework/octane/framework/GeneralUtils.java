package com.ot.automation.framework.octane.framework;

import com.ot.automation.framework.octane.framework.Entities.Run;
import com.ot.automation.framework.octane.framework.Entities.Workspace;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.driver;

public class GeneralUtils {
    public static final Duration timeoutSeconds = Duration.ofSeconds(10);

    public static String executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8192); // Increased buffer size
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Also read standard error (stderr) stream
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()), 8192); // Increased buffer size
            StringBuilder errorOutput = new StringBuilder();

            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            process.waitFor(); // Wait for the process to complete.

            if (process.exitValue() != 0) {
                System.err.println("Error: " + errorOutput);
            }

            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> executeCommand(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8192); // Increased buffer size
            String line;
            List<String> cmdResults = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                cmdResults.add(line);
            }

            // Also read standard error (stderr) stream
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()), 8192); // Increased buffer size
            StringBuilder errorOutput = new StringBuilder();

            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            process.waitFor(); // Wait for the process to complete.

            if (process.exitValue() != 0) {
                System.err.println("Error: " + errorOutput);
            }

            return cmdResults;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSpaceIdByName(String spaceName) {
        RestCallResult serverVersionResult = GeneralUtils.makeRestCallOctane("GET", "/admin/shared_spaces?fields=name,id&query=%22(name%3D%27" + spaceName + "%27)%22", null);
        assert serverVersionResult != null;
        String json = serverVersionResult.getResponseBody();
        String spaceId = "1001";
        try {
            JSONObject jsonObject = new JSONObject(json);
            spaceId = jsonObject.getJSONArray("data").getJSONObject(0).getString("id");

        } catch (JSONException e) {
            System.out.println("Cannot find space with name '" + spaceName + "', using default space 1001");
        }
        return spaceId;
    }

//    public static void deactivateSpace(String spaceId) {
//        RestCallResult deactivateSpaceResult = GeneralUtils.makeRestCall("POST", "/admin/manage_shared_space", "{\"status\":\"INACTIVE\",\"id\":\"" + spaceId + "\"}");
//        assert deactivateSpaceResult != null;
//        String json = deactivateSpaceResult.getResponseBody();
//        try {
//            JSONObject jsonObject = new JSONObject(json);
////            spaceId = jsonObject.getJSONArray("data").getJSONObject(0).getString("id");
//
//        } catch (JSONException e) {
////            System.out.println("Cannot find space with name '" + spaceName + "', using default space 1001");
//        }
//    }

    public static String getWorkspaceIdByName(String spaceId, String workspaceName) {
        RestCallResult serverVersionResult = GeneralUtils.makeRestCallOctane("GET", "/api/shared_spaces/" + spaceId + "/workspaces?fields=name,id&query=%22(name%3D%27" + workspaceName.replace("'", "\\'") + "%27)%22", null);
        String workspaceId = "1002"; // initialize with default_workspace id
        if (serverVersionResult != null) {
            String json = serverVersionResult.getResponseBody();
            try {
                JSONObject jsonObject = new JSONObject(json);
                workspaceId = jsonObject.getJSONArray("data").getJSONObject(0).getString("id");

            } catch (JSONException e) {
                System.out.println("Cannot find workspace with name '" + workspaceName + "', creating the workspace");
                Workspace.Data newWorkspace = Workspace.create(workspaceName);
                workspaceId = newWorkspace.getId();
            }
        }
        return workspaceId;
    }

    public static String createTenant(String farm, String licenseType, String tenantName) throws IOException {
        String command = "java -DFUNC=" + "createTenant" +
                " \"-DSAAS_FARM=" + farm + "\"" +
                " -DLICENSE_TYPE=" + licenseType +
                " \"-DTENANT_NAME=" + tenantName + "\"" +
                " -jar " + downloadOctaneJar(getServerVersion());

        String output = executeCommand(command);
        assert output != null;
        return getCreatedSpaceId(output);
    }

    public static RestCallResult makeRestCallNewMethod(String method, String resource, String payload) {
        HttpResponse<String> response = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BaseAutomationTest.OCTANE_URL + resource))
                    .build();


            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Response: " + response.body());
        return null;
    }

    public static RestCallResult makeRestCallOctane(String method, String resource, Object payload) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json, text/plain");

        if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method) || resource.contains("model")) {
            headers.put("HPECLIENTTYPE", "HPE_MQM_UI");
        }

        return makeRestCall(method, BaseAutomationTest.OCTANE_URL + resource, payload, headers);
    }

    public static RestCallResult makeRestCall(String method, String url, Object payload, Map<String, String> headers) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

            // Set the request method
            connection.setRequestMethod(method);

            // Set request headers
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Accept", "application/json, text/plain");
//
//            if ("GET".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method) || url.contains("model")) {
//                connection.setRequestProperty("HPECLIENTTYPE", "HPE_MQM_UI");
//            }

            // Set cookies if provided
            if (BaseAutomationTest.sharedLoginCookies != null) {
                for (Map.Entry<String, List<String>> entry : BaseAutomationTest.sharedLoginCookies.entrySet()) {
                    String key = entry.getKey();
                    List<String> values = entry.getValue();
                    if (key != null) {
                        connection.setRequestProperty(key, String.join("; ", values));
                    }
                }
            }

            // Set payload for POST requests
            if (("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) && payload != null) {
                connection.setDoOutput(true);

                if (payload instanceof String) {
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = ((String) payload).getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }
                } else if (payload instanceof File) {
                    try (OutputStream os = connection.getOutputStream();
                         FileInputStream fis = new FileInputStream((File) payload)) {
                        byte[] buffer = new byte[4096]; // 4KB buffer size
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }

            int statusCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            Map<String, List<String>> responseHeaders = connection.getHeaderFields();

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (Exception e) {
                return null;
            }

            return new RestCallResult(statusCode, (response == null) ? "" : response.toString(), responseHeaders);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String constructQueryString(Map<String, String> params) {
        StringJoiner queryString = new StringJoiner("&");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String encodedKey = null;
            try {
                encodedKey = URLEncoder.encode(entry.getKey(), "UTF-8");
                String encodedValue = URLEncoder.encode(entry.getValue(), "UTF-8");
                queryString.add(encodedKey + "=" + encodedValue);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return "?" + queryString;
    }

    public static <T> T waitAtMostFor(long millisToWait, String message, Supplier<T> condition) {
        if (millisToWait < 1000) {
            throw new IllegalArgumentException("for less than 1 sec await please use the GeneralUtils.delay method");
        }

        T conditionEvaluationResult;
        long started = System.currentTimeMillis(),
                sleepPeriod = 300;
        if (millisToWait > 60000)
            sleepPeriod = 5000;
        while (System.currentTimeMillis() - started < millisToWait) {
            conditionEvaluationResult = condition.get();
            if (conditionEvaluationResult != null) {
                return conditionEvaluationResult;
            } else {
                delay(sleepPeriod);
                driver.getCurrentUrl(); // browser ping
            }
        }
        throw new IllegalStateException("Can't wait anymore. " + millisToWait / 1000 + " seconds passed but still not got the requested result " + message);
    }

    public static void delay(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted, aborting");
        }
    }

    private static String getServerVersion() {
        RestCallResult serverVersionResult = GeneralUtils.makeRestCallOctane("GET", "/admin/server/version", null);
        assert serverVersionResult != null;
        String json = serverVersionResult.getResponseBody();
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString("version");
    }

    private static String downloadOctaneJar(String octaneVersion) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String relativePath = "downloads";
        String downloadDirectory = projectPath + File.separator + relativePath;

        String fileName = "mqm-qa-rest-test-impl-" + octaneVersion + "-master-jar-with-dependencies.jar";
        String jarFileUrl = "https://nexus.octane.admlabs.aws.swinfra.net/nexus/content/groups/mqm-all/com/hp/mqm/qa/mqm-qa-rest-test-impl/" + octaneVersion + "-master/" + fileName;
        String filePath = downloadDirectory + File.separator + fileName;

        try (BufferedInputStream in = new BufferedInputStream(new URL(jarFileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
        return filePath;
    }

    private static String getCreatedSpaceId(String output) {
        String targetString = "Shared Space Id = ";
        int index = output.indexOf(targetString);

        if (index != -1) {
            int startIndex = index + targetString.length();
            int endIndex = output.indexOf('\n', startIndex);
            if (endIndex != -1) {
                return output.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    public static void waitLoadingBar() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("alm-loading-bar")));
    }

    public void dropFile(File filePath, WebElement target, int offsetX, int offsetY) {
        if (!filePath.exists())
            throw new WebDriverException("File not found: " + filePath.toString());

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        String JS_DROP_FILE =
                "var target = arguments[0]," +
                        "    offsetX = arguments[1]," +
                        "    offsetY = arguments[2]," +
                        "    document = target.ownerDocument || document," +
                        "    window = document.defaultView || window;" +
                        "" +
                        "var input = document.createElement('INPUT');" +
                        "input.type = 'file';" +
                        "input.style.display = 'none';" +
                        "input.onchange = function () {" +
                        "  var rect = target.getBoundingClientRect()," +
                        "      x = rect.left + (offsetX || (rect.width >> 1))," +
                        "      y = rect.top + (offsetY || (rect.height >> 1))," +
                        "      dataTransfer = { files: this.files };" +
                        "" +
                        "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
                        "    var evt = document.createEvent('MouseEvent');" +
                        "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
                        "    evt.dataTransfer = dataTransfer;" +
                        "    target.dispatchEvent(evt);" +
                        "  });" +
                        "" +
                        "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
                        "};" +
                        "document.body.appendChild(input);" +
                        "return input;";

        WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
        input.sendKeys(filePath.getAbsoluteFile().toString());
    }

    public static void setSiteParameter(String parameterName, String parameterValue) {
        RestCallResult setSiteParameterResult = GeneralUtils.makeRestCallOctane("PUT", "/admin/params/" + parameterName, "{\"id\":\"" + parameterName + "\",\"value\":\"" + parameterValue + "\"}");
        assert setSiteParameterResult.getStatusCode() == 200;
    }

    public static String getSiteParameter(String parameterName) {
        RestCallResult getSiteParameterResult = GeneralUtils.makeRestCallOctane("GET", "/admin/params/" + parameterName, null);
        assert getSiteParameterResult.getStatusCode() == 200;

        assert getSiteParameterResult != null;
        String json = getSiteParameterResult.getResponseBody();
        String value = "";
        try {
            JSONObject jsonObject = new JSONObject(json);
            value = jsonObject.getString("value");

        } catch (JSONException e) {
            System.out.println("Cannot find value for parameter with name '" + parameterName);
        }
        return value;
    }

    public static void switchTab() {
        Set<String> windowHandles = driver.getWindowHandles();

        // Switch to the new tab
        String currentWindowHandle = driver.getWindowHandle(); // The original window handle
        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {
                driver.switchTo().window(handle); // Switch to the new tab
                break;
            }
        }
    }

    public static String waitSuiteRunInProgress(int waitTime, String suiteName) {
        return GeneralUtils.waitAtMostFor(waitTime, "Suit Run is still In Progress.", () -> {
            Run.Data suiteRun = Run.getEntityById(suiteName, List.of(Run.Fields.STATUS));
            JSONObject statusJson = new JSONObject(suiteRun.getField(Run.Fields.STATUS));
            String status = (String) statusJson.get("name");

            return (status.contains("In Progress") || status.contains("Planned")) ? null : status.trim();
        });
    }

    public static void closeCurrentTab() {
        Set<String> allWindows = driver.getWindowHandles();
        Iterator<String> iterator = allWindows.iterator();

        driver.close();

        String nextWindowHandle = iterator.next(); // Get the handle of the next window
        driver.switchTo().window(nextWindowHandle); // Switch to the remaining window
    }
}