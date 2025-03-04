package com.ot.automation.tests;

import com.ot.automation.framework.octane.framework.BaseAutomationTest;
import com.ot.automation.framework.octane.framework.Entities.ModelFolder;
import com.ot.automation.framework.octane.framework.Entities.Release;
import com.ot.automation.framework.octane.framework.Entities.TestSuite;
import com.ot.automation.framework.octane.framework.Entities.Unit;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.UrlNavigation;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

public class MbtToDesign extends BaseAutomationTest {
    static RemoteWebDriver driver;
    static WebDriverWait waiter;

    @BeforeSuite
    @Test
    @Parameters({"octaneUrl", "tenantId", "tenantName", "workspaceName", "userName", "password"})
    void beforeTest(@Optional("https://integration-master-dev.almoctane.com") String octaneUrl, @Optional String tenantId, @Optional("FTE_*") String tenantName, @Optional("sanity_workspace") String workspaceName, @Optional("mqm_rnd@hpe.com") String userName, @Optional("JustK33pGoing!") String password) {
        initializeEnv(octaneUrl, tenantId, tenantName, workspaceName, userName, password);
        setupDriver(driver, waiter);
    }

    @Test
    public void loginOctaneUnit() {
        loginOctane();
    }

    @Test
    public void mbtToDesign() {
        Unit.Data unit = Unit.create("myUnit");
        navigateByUrlToEntity(unit);
        UI.docViews.docView.detailsTab.toolbar.clickCreateInDesignButton();

        //TODO continue test
    }
}