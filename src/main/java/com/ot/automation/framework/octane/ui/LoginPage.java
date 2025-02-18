package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class LoginPage extends BaseElement {

    public LoginPage() {
        super(Locator.dataAid("login-app"));
    }

    private void setUsername(String username) {
        BaseElement usernameField = new BaseElement(Locator.dataAid("login-app-input-username"));
        usernameField.expectVisible();
        usernameField.sendKeys(username);
    }

    private void setPassword(String password) {
        BaseElement passwordField = new BaseElement(Locator.dataAid("login-app-input-password"));
        passwordField.expectVisible();
        passwordField.sendKeys(password);
    }

    public void login(String username, String password) {
        setUsername(username);
        setPassword(password);
        new BaseElement(Locator.dataAid("login-app-buttons-authenticate")).click();
    }
}