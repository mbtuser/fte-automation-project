package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

import java.time.LocalDateTime;

public class DateTimeEditor extends BaseElement {

    private final BaseElement monthSelect = new BaseElement(Locator.cssSelector("[class='ui-datepicker-month']"), this);
    private final BaseElement yearSelect = new BaseElement(Locator.cssSelector("[class='ui-datepicker-year']"), this);
    private final BaseElement hourInput = new BaseElement(Locator.cssSelector("[ng-model='hours']"), this);
    private final BaseElement minutesInput = new BaseElement(Locator.cssSelector("[ng-model='minutes']"), this);

    public DateTimeEditor(By cssSelector, BaseElement parent) {
        super(cssSelector, parent);
    }

    public void setValue(LocalDateTime currentDateTime) {
        int hour = currentDateTime.getHour();
        int minutes = currentDateTime.getMinute();
        int day = currentDateTime.getDayOfMonth();
        int year = currentDateTime.getYear();
        String month = currentDateTime.getMonth().toString().substring(0, 1) +
                currentDateTime.getMonth().toString().substring(1, 3).toLowerCase();

        this.click();
        hourInput.sendKeys(String.valueOf(hour));
        minutesInput.sendKeys(String.valueOf(minutes));
        monthSelect.selectByVisibleText(month);
        yearSelect.selectByVisibleText(String.valueOf(year));
        final BaseElement daySelect = new BaseElement(Locator.cssSelector("[data-date='" + day + "']"), this);
        daySelect.click();
    }

    public void expectValue(String expectedValue) {
        String actualValue = getText();
        if (!actualValue.contains(expectedValue)) {
            throw new AssertionError("Expected text: '" + expectedValue + "' but found: '" + actualValue + "'");
        }
    }
}