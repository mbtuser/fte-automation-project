package tests;//package com.ot.automation.tests;
//
//import com.ot.automation.framework.octane.general.BaseAutomationTest;
//import com.ot.automation.framework.octane.general.Entities.TestSuite;
//import com.ot.automation.framework.octane.general.UrlNavigation;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//public class ClassParam extends BaseAutomationTest {
//    static RemoteWebDriver driver;
//    static WebDriverWait waiter;
//
//    @BeforeSuite
//    @Parameters({"spaceName","workspaceName"})
//    void init(String spaceName, String workspaceName) {
//        BaseAutomationTest.setDriver(driver, waiter);
//        BaseAutomationTest.setWorkspace(spaceName, workspaceName);
//    }
//
//    @Test
//    public void paramTest() {
//        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
//        UI.functionalTestingView.expectVisible();
//        UI.functionalTestingView.navigateToSchedules();
//        UI.functionalTestingView.schedulesContainer.grid.expectVisible();
//
//        // Create TS for UFT
//        TestSuite.Data tsUft = TestSuite.create("testSuite_X");
//        navigateByUrlToEntity(tsUft);
//    }
//
////    @Test
////    public void emptyTest() {
////        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
////        UI.functionalTestingView.expectVisible();
////        UI.functionalTestingView.navigateToSchedules();
////        UI.functionalTestingView.schedulesContainer.grid.expectVisible();
////
////        // Create TS for UFT
////        TestSuite.Data tsUft = TestSuite.create("testSuiteUFT_" );
////        navigateByUrlToEntity(tsUft);
////    }
//}