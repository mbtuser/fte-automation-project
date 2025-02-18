package com.ot.automation.framework.backoffice;

import com.ot.automation.framework.octane.framework.BaseElement;
import org.openqa.selenium.By;

public class AssignAccountDialog extends BaseElement {
    private BaseElement customerNameFilterInput = new BaseElement(By.cssSelector("[ng-model='filter.customerNameFilter']"), this);
    private BaseElement searchButton = new BaseElement(By.cssSelector("[ng-click='filterCustomers();']"), this);

    public AssignAccountDialog() {
        super(By.name("selectCustomerForm"));
    }

    public void selectAccount(String account) {
        customerNameFilterInput.sendKeys(account);
        searchButton.click();
        BaseElement result = new BaseElement(By.cssSelector("[title='" + account + "']"), this);
        result.click();
    }
}