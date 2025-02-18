package com.ot.automation.framework.octane.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Locator extends By {

    private final String attribute;
    private final String value;

    public Locator(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return context.findElement(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    @Override
    public java.util.List<WebElement> findElements(SearchContext context) {
        return context.findElements(By.cssSelector("[" + attribute + "='" + value + "']"));
    }

    @Override
    public String toString() {
        return "By." + attribute + ": " + value;
    }

    public static Locator dataAid(String value) {
        return new Locator("data-aid", value);
    }
}