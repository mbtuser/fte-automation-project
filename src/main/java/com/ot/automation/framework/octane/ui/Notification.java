package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class Notification extends BaseElement {

    private final BaseElement entityLink = new BaseElement(Locator.cssSelector("a"),this);

    private final BaseElement closeButton = new BaseElement(Locator.className("close"),this);

    public Notification(String notificationStatus) {
        super(Locator.dataAid("notification-view-" + notificationStatus));
    }

    public Notification clickEntityLink() {
        entityLink.click();
        return this;
    }

    public void close(){
        closeButton.click();
    }

    public String getId() {
        return entityLink.getText();
    }
}