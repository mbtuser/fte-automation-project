package com.ot.automation.framework.jenkins;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

public class JenkinsUtils extends BaseElement {

    public JenkinsUtils() {
        super(By.xpath("//*[@id=\"jenkins\"]"));
    }

    BaseElement pluginSection = new BaseElement(Locator.id("jenkins"));
    BaseElement newCIServerSection = new BaseElement(Locator.cssSelector("[class*='repeated-chunk last']"), pluginSection);

    public void clickAddServerButton() {
        BaseElement addServerButton = new BaseElement(By.xpath("//button[text()='Add ALM Octane server']"), pluginSection);
        BaseElement scrollToServerButton = new BaseElement(By.xpath("//*[@id=\"miscellaneous-opentext-plugin-settings\"]"), pluginSection);
        scrollToElement(scrollToServerButton.find());
        addServerButton.click();
    }

    public void setLocationField(String location) {
        BaseElement scrollToServerButton = new BaseElement(By.xpath("//button[text()='Add ALM Octane server']"), pluginSection);
        scrollToElement(scrollToServerButton.find());
        BaseElement locationField = new BaseElement(By.cssSelector("[name*='_.uiLocation']"), newCIServerSection);
        locationField.sendKeys(location);
    }

    public void setClientIDField(String clientID) {
        BaseElement clientIDField = new BaseElement(By.cssSelector("[name*='_.username']"), newCIServerSection);
        clientIDField.sendKeys(clientID);
    }

    public void setClientSecretField(String clientSecret) {
        BaseElement clientSecretField = new BaseElement(By.cssSelector("[name*='_.password']"), newCIServerSection);
        clientSecretField.sendKeys(clientSecret);
    }

    public void clickApplyButton() {
        BaseElement applyButton = new BaseElement(By.cssSelector("[class*='jenkins-button apply-button']"));
        applyButton.click();
    }

    public void clickSaveButton() {
        BaseElement saveButton = new BaseElement(By.cssSelector("[class*='jenkins-button jenkins-submit-button jenkins-button--primary ']"));
        saveButton.click();
    }
}

