package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import org.openqa.selenium.By;

public class SmartEditor extends BaseElement {

    private final BaseElement searchBox = new BaseElement(Locator.dataAid("field-editor-search-input-box"), this);
    private final BaseElement dropdownButton = new BaseElement(Locator.dataAid("field-editor-drop-down-button"), this);

    public SmartEditor(By cssSelector, BaseElement parent) {
        super(cssSelector, parent);
    }

    public void setValue(String value) {
        this.expectVisible();
        this.hover();
        dropdownButton.expectVisible();
        dropdownButton.click();
        searchBox.expectVisible();
        searchBox.sendKeys(value);
        BaseElement dropdownOption = new BaseElement(Locator.cssSelector("[data-aid*='" + value + "']"), this);

        //BaseElement dropdownOption = new BaseElement(Locator.dataAid(value), this);
        dropdownOption.click();
    }

    public void expectValue(String expectedValue) {
        String actualValue = getText();
        if (!actualValue.contains(expectedValue)) {
            throw new AssertionError("Expected text: '" + expectedValue + "' but found: '" + actualValue + "'");
        }
    }
}