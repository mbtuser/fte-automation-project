package com.ot.automation.framework.backoffice;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import org.openqa.selenium.By;

import java.time.LocalDateTime;

public class IPSubscriptionTermsTab extends BaseElement {
    public IPSubscriptionTermsTab(BaseElement parent) {
        super(By.cssSelector("[class*='subscriptionTerms']"), parent);
    }

    public void setSpecialInstructions(String instructionsText) {
        BaseElement textbox = new BaseElement(By.cssSelector("[ng-model='product.product.specialInstructions']"), this);
        textbox.sendKeys(instructionsText);
    }

    public void checkApplyDateToAll() {
        BaseElement checkbox = new BaseElement(By.cssSelector("[class*='lostReasons']"), this);
        checkbox.click();
    }

    public void addNewSubscription(Integer quantity, String subscription, LocalDateTime from, LocalDateTime to) {
        clickAddNewSubscriptionTerm();
        BaseElement lastLine = new BaseElement(By.cssSelector("div[data-ng-repeat='item in product.orderLines']:last-of-type"), this);
        lastLine.expectVisible();
        lastLine.click();

        BaseElement quantityTextbox = new BaseElement(By.cssSelector("[ng-model='item.subscription.quantity']"), lastLine);
        quantityTextbox.sendKeys(quantity.toString());

        BaseElement dropdownButton = new BaseElement(By.cssSelector("[title='Select an Option']"), lastLine);
        dropdownButton.click();

        BaseElement searchBox = new BaseElement(By.cssSelector("[placeholder='Search']"), lastLine);
        searchBox.sendKeys(subscription);

        BaseElement resultsList = new BaseElement(By.cssSelector("[class='chosen-results']"), lastLine);
        resultsList.click();

        if (from != null) {
            BaseElement fromSelectorContainer = new BaseElement(By.cssSelector("[ng-model='item.subscription.startDate']"), lastLine);
            BaseElement fromSelector = new BaseElement(By.cssSelector("[type='datetime']"), fromSelectorContainer);
            fromSelector.click();
            BaseElement calendarSelector = new BaseElement(By.cssSelector("[class='pika-single is-bound']"));
            BaseElement selectedDate = new BaseElement(By.cssSelector("td[data-day='" + from.getDayOfMonth() + "'][data-month='" + (from.getMonthValue() - 1) + "'][data-year='" + from.getYear() + "']"), calendarSelector);
            selectedDate.expectVisible();
            selectedDate.click();
        }

        if (to != null) {
            BaseElement toSelectorContainer = new BaseElement(By.cssSelector("[ng-model='item.subscription.endDate']"), lastLine);
            BaseElement toSelector = new BaseElement(By.cssSelector("[type='datetime']"), toSelectorContainer);
            toSelector.click();
            BaseElement calendarSelectorTo = new BaseElement(By.cssSelector("[class='pika-single is-bound']"));
            BaseElement selectedDateTo = new BaseElement(By.cssSelector("td[data-day='" + to.getDayOfMonth() + "'][data-month='" + (to.getMonthValue() - 1) + "'][data-year='" + to.getYear() + "']"), calendarSelectorTo);
            selectedDateTo.expectVisible();
            selectedDateTo.click();
        }

        GeneralUtils.delay(100);
    }

    private void clickAddNewSubscriptionTerm() {
        BaseElement addNewSubscriptionButton = new BaseElement(By.cssSelector("[ng-click='addRowToNewSubscriptionOrderLines()']"), this);
        addNewSubscriptionButton.click();
    }
}