package tests;//package com.ot.automation.tests;
//
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SanityV1 {
//
//    static RemoteWebDriver driver;
//    static WebDriverWait waiter;
//
//    @BeforeSuite
//    void prepare() {
////        Boolean localRun = false;
////        if (localRun){
////            String chromeVersion = getChromeVersionFromCmd();
////            String chromeDriverPath = downloadChromeDriver(chromeVersion);
////
////            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
////            ChromeOptions options = new ChromeOptions();
////            options.addArguments("--start-maximized");
////            options.addArguments("--remote-allow-origins=*");
////            driver = new ChromeDriver(options);
////        }
//        driver.manage().window().maximize();
//        driver.get("https://integration-master-dev.almoctane.com/");
//    }
//
//
//    @Parameters({"user","password"})
//    @Test
//    void navigate_param(String user, String password) {
//        System.out.println("log in");
//    }
//
//    @Test
//    void navigate() {
//        System.out.println("log in");
//    }
//
////    private String getChromeVersionFromCmd() {
////        String majorVersion = null;
////        try {
////            Process process = Runtime.getRuntime().exec("reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version");
////            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
////
////            String line;
////            while ((line = reader.readLine()) != null) {
////                if (line.contains("version")) {
////                    String[] parts = line.split("\\s+");
////                    String version = parts[parts.length - 1];
////                    majorVersion = version.split("\\.")[0];
////                    reader.close();
////                    break;
////                }
////            }
////        } catch (IOException e) {
////            throw new RuntimeException("Failed to get local chrome version. ", e);
////        }
////        return majorVersion;
////    }
////
////    private String downloadChromeDriver(String chromeVersion) {
////        String downloadedFilePath = downloadUsingPuppeteer("chromedriver", chromeVersion);
////        System.out.println("chrome driver file path: " + downloadedFilePath);
////        return downloadedFilePath;
////    }
////
////    private String downloadUsingPuppeteer(String type, String version) {
////        final String[] arguments = {"npx.cmd", "@puppeteer/browsers", "install", type + "@" + version, "--path", "C:\\dev\\FteSanity\\src\\test\\resources"};
////        System.out.println("using @puppeteer/browsers to download " + type);
////        System.out.println(String.join(" ", arguments));
////        List<String> output = executeCommand(arguments);
////        assert output != null;
////        return getDownloadedFilePath(output);
////    }
////
////    public static List<String> executeCommand(String[] command) {
////        try {
////            Process process = Runtime.getRuntime().exec(command);
////
////            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8192); // Increased buffer size
////            String line;
////            List<String> cmdResults = new ArrayList<>();
////
////            while ((line = reader.readLine()) != null) {
////                cmdResults.add(line);
////            }
////
////            // Also read standard error (stderr) stream
////            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()), 8192); // Increased buffer size
////            StringBuilder errorOutput = new StringBuilder();
////
////            while ((line = errorReader.readLine()) != null) {
////                errorOutput.append(line).append("\n");
////            }
////
////            process.waitFor(); // Wait for the process to complete.
////
////            if (process.exitValue() != 0) {
////                System.err.println("Error: " + errorOutput);
////            }
////
////            return cmdResults;
////        } catch (IOException | InterruptedException e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    private String getDownloadedFilePath(List<String> results) {
////        if (results.isEmpty()) {
////            throw new RuntimeException("Expected output response is empty");
////        }
////
////        String filePath = null;
////        for (String result : results) {
////            if (result.contains("chrome")) {
////                filePath = result.split(" ")[1];
////                break;
////            }
////        }
////        return filePath;
////    }
//}

//////// upload tracker

//        String invalidJarFilename = "invalid.jar";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("runner_id", cr.getId());
//        params.put("filename", invalidJarFilename);
//        params.put("fileversion", "0003");
//        String resource = "/api/shared_spaces/" + OCTANE_TENANT_ID + "/workspaces/" + OCTANE_WORKSPACE_ID + "/presigned_url" + constructQueryString(params);


//        RestCallResult getEntityResult = GeneralUtils.makeRestCallOctane("GET", resource, null);

//        String generatedUrl = getEntityResult.getResponseBody();

//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/java-archive");
//        headers.put("Accept", "application/json, text/plain");
//        headers.put("Accept-Encoding", "gzip, deflate, br");
//        headers.put("Connection", "keep-alive");
//        headers.put("x-amz-meta-uploadby", apiClientId);
//        headers.put("x-amz-meta-fileversion", "0003");


//        String fileURL = "https://github.com/jarirajari/helloworld/blob/master/helloworld.jar";
//        String filePath = System.getProperty("user.home") + "/Downloads/hello.jar";
//        String filePath = "C:/hello.jar";

//        try (InputStream in = new URL(fileURL).openStream()) {
//            Files.copy(in, Paths.get(filePath));  // Download and save to the local file
//            System.out.println("File downloaded to " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try (InputStream inputStream = SanityUnits.class.getClassLoader().getResourceAsStream(invalidJarFilename)) {
//            if (inputStream == null) {
//                System.out.println("File not found!");
//                return;
//            }
//
//            File resourceFile = new File(SanityUnits.class.getClassLoader().getResource(invalidJarFilename).toURI());
//            RestCallResult putEntity = GeneralUtils.makeRestCall("PUT", generatedUrl, resourceFile, headers);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

// UI Actions

//        String filePath = "C:/testProject.jar";
//        File file = new File(resourceFile.getAbsolutePath());

//        RestCallResult putEntity = GeneralUtils.makeRestCall("PUT", generatedUrl, file, headers);



//        // Create Execution Storage runner
//
//        String cr5id = createCloudRunner(trimParenthesis(seleniumRunnerName) + " Execution Storage", seleniumRunnerName, null, packagePath);
//
//        String loginResource = "/authentication/sign_out";
//        String loginBody = "{ \"user\":\"" + OCTANE_USERNAME + "\", \"password\":\"" + OCTANE_PASSWORD + "\" }";
//
//        RestCallResult loginResult = GeneralUtils.makeRestCall("POST", loginResource, loginBody);
//        assert loginResult.getStatusCode() == 200;
//

//
//        TestSuite.Data tsSelenium = TestSuite.create("tsSelenium_" + RandomStringUtils.randomAlphabetic(5));
//        navigateByUrlToEntity(tsSelenium);
//        addTestToTestSuite(seleniumTestToRun);
//        setMachineTemplateAndRunMode("Selenium TestNG,   (Default)", "Parallel");
//        UI.docViews.docView.detailsTab.toolbar.clickSave();
//
//        // Create schedule for Selenium
//        String scheduleIdSel = createSchedule(scheduleNameSel, "Once");
//        addSuiteToSchedule(tsSelenium.getId());
//
//        // Run schedule and wait for result
//        String scheduleRunId = startSchedule();
////        expectScheduleCompleted(scheduleRunId);
////        expectGeneralRunStatus(scheduleRunId, 1, 0, 0);