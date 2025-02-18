package com.ot.automation.framework.octane.framework;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static com.ot.automation.framework.octane.framework.BaseAutomationTest.driver;
import static com.ot.automation.framework.octane.framework.GeneralUtils.timeoutSeconds;

public class BaseElement {
    private final By locator;
    private final BaseElement parent;

    public BaseElement(By locator) {
        this.locator = locator;
        this.parent = null;
    }

    public BaseElement(By locator, BaseElement parent) {
        this.locator = locator;
        this.parent = parent;
    }

    public By getLocator() {
        return locator;
    }

    public WebElement find() {
        if (parent != null) {
            return parent.find().findElement(locator);
        } else {
            return driver.findElement(locator);
        }
    }

    private void waitForElementToBeClickable(WebElement element) {
        waitForElementToBeVisible(element);
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        GeneralUtils.waitLoadingBar();
    }

    private void waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
        this.expectVisible();
    }

    public void hover() {
        WebElement element = find();
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void click() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);

        try {
            // Wait for the loading bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("loading-bar")));

            WebElement element = wait.until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    try {
                        WebElement ele = find();
                        ele.click();
                        return ele;
                    } catch (ElementClickInterceptedException e) {
                        // Retry for ElementClickInterceptedException
                        return null;
                    } catch (ElementNotInteractableException e) {
                        // Handle ElementNotInteractableException separately
                        Assert.fail("Element with locator '" + locator + "' is not interactable.");
                        return null; // Return null to break out of the retry loop
                    }
                }
            });

            if (element == null) {
                Assert.fail("Element with locator '" + locator + "' is not clickable.");
            }
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            Assert.fail("Element with locator '" + locator + "' is not found or is stale.");
        } catch (TimeoutException e) {
            Assert.fail("Element with locator '" + locator + "' is not clickable or not found.");
        }
    }

    public void sendKeys(String text) {
        this.expectVisible();
        WebElement element = find();
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public void sendKeysInput(String text) {
        this.expectVisible();
        WebElement element = find();
        element.sendKeys(text);
    }

    public void expectVisible() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        try {
            WebElement element = wait.until(driver -> find());

            if (element == null) {
                Assert.fail("Element with locator '" + locator + "' is not visible.");
            }
        } catch (TimeoutException e) {
            Assert.fail("Timed out waiting for element with locator '" + locator + "' to be visible.");
        }
    }

    public void expectNotVisible() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);

        try {
            wait.until(driver -> {
                try {
                    WebElement element = find();
                    return element == null || !element.isDisplayed();
                } catch (StaleElementReferenceException e) {
                    // Element reference became stale, retry finding the element
                    return false; // Retry
                }
            });
        } catch (TimeoutException e) {
            return; // Element is not visible within the timeout
        }

        Assert.fail("Element with locator '" + locator + "' or its descendants is still visible.");
    }

    public boolean isVisible() {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element != null;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isDisabled() {
        return "disabled".equals(getAttribute("disabled"));
    }

    public void expectTextToContain(String... texts) {
        this.expectVisible();
        long startTime = System.currentTimeMillis();

        String actualText = getText();
        while (System.currentTimeMillis() - startTime < timeoutSeconds.getSeconds() * 1000) {
            try {
                actualText = getText();
                for (String text : texts) {
                    if (actualText.contains(text)) {
                        return; // Exit as soon as one match is found
                    }
                }
            } catch (ElementClickInterceptedException | NoSuchElementException | StaleElementReferenceException e) {
                throw new AssertionError("Expected one of the texts: " + String.join(", ", texts) + " but found: '" + actualText + "'");
            }
        }

        // If the loop completes without a match, it means we couldn't get valid text or find a match
        throw new AssertionError("Expected one of the texts: " + String.join(", ", texts) + " but found: '" + actualText + "'");
    }

    public void expectElementToContainAttrValue(String attrName, String attrValue) {
        String actualAttrValue = getAttribute(attrName);
        if (actualAttrValue == null || !actualAttrValue.contains(attrValue)) {
            throw new AssertionError("Expected attribute value for '" + attrName + "': '"
                    + attrValue + "' but found: '" + actualAttrValue + "'");
        }
    }

    public void expectValue(String text) {
        String actualValue = getAttribute("value");
        if (!actualValue.contains(text)) {
            throw new AssertionError("Expected text: '" + text + "' but found: '" + actualValue + "'");
        }
    }

    public BaseElement getParent() {
        return this.parent;
    }

    public String getText() {
        try {
            return find().getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public BaseElement getChildElement(By locator) {
        BaseElement childElement = new BaseElement(locator, this);
        return childElement;
    }

    public String getAttribute(String attributeName) {
        return driver.findElement(locator).getAttribute(attributeName);
    }

    public void selectByVisibleText(String visibleText) {
        WebElement dropdown = find();
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }

    public void scrollToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }
}