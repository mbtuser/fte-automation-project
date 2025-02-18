package com.ot.automation.framework.backoffice;

import com.ot.automation.framework.octane.framework.BaseElement;
import org.openqa.selenium.By;

public class IPPage extends BaseElement {

    public IPGeneralTab generalTab;
    public IPSubscriptionTermsTab subscriptionsTab;
    public IPTenantsTab tenantsTab;

    public IPPage() {
        super(By.id("appWin"));
        generalTab = new IPGeneralTab(this);
        subscriptionsTab = new IPSubscriptionTermsTab(this);
        tenantsTab = new IPTenantsTab(this);
    }

    public BaseElement getAssignAccountButton() {
        return new BaseElement(By.id("assignAccountButtonId"), this);
    }

    public void clickGeneralTab() {
        new BaseElement(By.cssSelector("[data-ng-click='showGeneralTab()']"), this).click();
    }

    public void clickSubscriptionTermsTab() {
        new BaseElement(By.cssSelector("[data-ng-click='showSubscriptions()']"), this).click();
    }

    public void clickTenantsTab() {
        new BaseElement(By.cssSelector("[data-ng-click='buildTenantsAndMoveToShowTenants();']"), this).click();
    }

    public void clickApproveSubmit() {
        BaseElement approveButton = new BaseElement(By.cssSelector("[ng-click*='save(true, $event)']"), this);
        approveButton.click();
    }
}