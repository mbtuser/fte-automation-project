package tests;//package com.ot.automation.tests;
//
//import com.ot.automation.framework.octane.general.BaseAutomationTest;
//import com.ot.automation.framework.octane.general.Entities.TestSuite;
//import com.ot.automation.framework.octane.general.UrlNavigation;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//public class EmptyTest extends BaseAutomationTest {
//    static RemoteWebDriver driver;
//    static WebDriverWait waiter;
//
//    @BeforeSuite
//    void init() {
//        BaseAutomationTest.setDriver(driver, waiter);
//    }
//
//    @Test
//    @Parameters({"param1","param2"})
//    public void paramTest(String param1, String param2) {
//        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
//        UI.functionalTestingView.expectVisible();
//        UI.functionalTestingView.navigateToSchedules();
//        UI.functionalTestingView.schedulesContainer.grid.expectVisible();
//
//        // Create TS for UFT
//        TestSuite.Data tsUft = TestSuite.create("testSuite_" + param1);
//        TestSuite.Data tsUf2 = TestSuite.create("testSuite_" + param2);
//        navigateByUrlToEntity(tsUft);
//    }
//
//    @Test
//    public void emptyTest() {
//        navigateByUrl(UrlNavigation.Module.FUNCTIONAL_TESTING_EXECUTION_SCHEDULES);
//        UI.functionalTestingView.expectVisible();
//        UI.functionalTestingView.navigateToSchedules();
//        UI.functionalTestingView.schedulesContainer.grid.expectVisible();
//
//        // Create TS for UFT
//        TestSuite.Data tsUft = TestSuite.create("testSuiteUFT_" );
//        navigateByUrlToEntity(tsUft);
//    }
//}