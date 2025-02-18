package com.ot.automation.framework.octane.framework;

import java.util.List;
import java.util.Map;

public class RestCallResult {
    private final int statusCode;
    private final String responseBody;
    private final Map<String, List<String>> responseHeaders;

    public RestCallResult(int statusCode, String responseBody, Map<String, List<String>> responseHeaders) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }
}