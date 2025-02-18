package com.ot.automation.framework.backoffice;

import com.ot.automation.framework.octane.framework.BaseElement;
import org.openqa.selenium.By;

public class IPGeneralTab extends BaseElement {

    private BaseElement openSelectCustomerButton = new BaseElement(By.cssSelector("[ng-click='openSelectCustomer()']"), this);
//    private BaseElement dropdownContainer = new BaseElement(By.cssSelector("div.chosen-container"), this);
//    private BaseElement reasonTypeDropdownContainer = new BaseElement(By.cssSelector("div.Flexbox .chosen-container:nth-of-type(1)"), this);

//    public IPGeneralTab(By locator, BaseElement parent) {
//        super(locator, parent);
//    }

    public IPGeneralTab(BaseElement parent) {
        super(By.cssSelector("[class*='new-subscription-general']"), parent);
    }

    public void clickAssignAccountButton() {
        openSelectCustomerButton.click();
    }

    //
//    private void selectOptionInDropdown(BaseElement dropdownContainer, String optionText) {
//        // Click the dropdown to open the list
//        BaseElement dropdownSingle = new BaseElement(By.className("chosen-single"), this);
//        dropdownSingle.click();
//
//        // Find the search input element within the dropdown
//        WebElement searchInput = dropdownContainer.findElement(By.cssSelector(".chosen-search input"));
//        searchInput.clear();
//        searchInput.sendKeys(optionText);
//
//        // Click the first result in the list
//        WebElement firstResult = dropdownContainer.findElement(By.cssSelector(".chosen-results li"));
//        firstResult.click();
//    }
//

//     public void selectReasonType(String reasonType) {
//        selectValueInDropdown(1, reasonType);
//
//        WebElement dropdownElement = BackOffice.driver.findElement(By.cssSelector("div.Flexbox select"));
//        Select dropdown = new Select(dropdownElement);
//        dropdown.selectByVisibleText(reasonType);
//
//
//        // Click the dropdown to open the list
////        WebElement dropdownSingle = dropdownContainer.findElement(By.className("chosen-single"));
//
//        dropdownContainer.expectVisible();
//
//        BaseElement dropdownSingle = new BaseElement(By.className("chosen-single"), dropdownContainer);
//        dropdownSingle.click();
//
//        // Find the search input element within the dropdown
//        BaseElement searchInput = new BaseElement(By.cssSelector(".chosen-search input"), dropdownContainer);
////        WebElement searchInput = dropdownContainer.findElement(By.cssSelector(".chosen-search input"));
//        searchInput.sendKeys(reasonType);
//
//        // Click the first result in the list
//        BaseElement firstResult = new BaseElement(By.cssSelector(".chosen-results li"), dropdownContainer);
//
////        WebElement firstResult = dropdownContainer.findElement(By.cssSelector(".chosen-results li"));
//        firstResult.click();
//    }

//    public void selectValueInDropdown(int dropdownNumber, String value) {
//        BaseElement dropdown = new BaseElement(By.cssSelector("[class*=Flexbox__flex-1]:nth-of-type(" + dropdownNumber + ")"), dropdownContainer);
//        dropdown.click();
//
//        BaseElement chosenDropdown = new BaseElement(By.cssSelector("[class*=chosen-with-drop]"));
//
//        BaseElement searchInput = new BaseElement(By.cssSelector(".chosen-search input"), chosenDropdown);
//        searchInput.sendKeys(value);
//        BaseElement firstResult = new BaseElement(By.cssSelector(".chosen-results li"), dropdownContainer);
//        firstResult.click();
//    }
//
//    public void selectValueInDropdownOld(int dropdownNumber, String value) {
//        WebDriverWait wait = new WebDriverWait(BackOffice.driver, timeoutSeconds);
//
//        // Determine the correct CSS selector based on dropdownNumber
//        String dropdownSelector = (dropdownNumber == 1) ?
//                "div.Flexbox select:nth-of-type(1)" :
//                "div.Flexbox select:nth-of-type(2)";
//
//        // Locate and click on the chosen-single element
//        WebElement chosenSingle = wait.until(ExpectedConditions.elementToBeClickable(
//                By.cssSelector(dropdownSelector + " > .chosen-container > .chosen-single")
//
//        ));
//
////        WebElement chosenSingle = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(dropdownSelector + " + .chosen-container .chosen-single")));
//        chosenSingle.click();
//
//        // Locate and type text into the search box
//        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(dropdownSelector + " + .chosen-container .chosen-search input")));
//        searchBox.sendKeys(value);
//
//        // Wait for the result to appear and click it
//        By resultLocator = By.cssSelector(dropdownSelector + " + .chosen-container .chosen-results li[title='" + value + "']");
//        WebElement result = wait.until(ExpectedConditions.elementToBeClickable(resultLocator));
//        result.click();
//    }

    public void selectReasonType(String reasonType) {
        selectInDropdown("Reason Type:", reasonType);
    }

    public void setReasonDetails(String reasonDetails) {
        BaseElement reasonDetailsTextBox = new BaseElement(By.cssSelector("[ng-model='newSubscription.reason']"));
        reasonDetailsTextBox.sendKeys(reasonDetails);
        reasonDetailsTextBox.click();
    }

    public void selectProduct(String product) {
        selectInDropdown("Product:", product);
    }

    public void selectCSM(String csm) {
        selectInDropdown("CSM:", csm);
    }

    public void setApprovedBy(String email) {
        BaseElement emailTextBox = new BaseElement(By.cssSelector("[ng-model='newOrder.swoCreationLog.approvedBy']"));
        emailTextBox.sendKeys(email);
    }

    public void setAttachment(String fileLocation) {
        BaseElement emailTextBox = new BaseElement(By.cssSelector("[id*='uploadAttachmentInput']"));
        emailTextBox.sendKeysInput(fileLocation);
    }

    private void selectInDropdown(String dropdownName, String dropdownValue) {
        BaseElement dropdownContainer = new BaseElement(By.xpath("//div[text()='" + dropdownName + "']/.."), this);
        BaseElement dropdown = new BaseElement(By.cssSelector("[class*=chosen-single]"), dropdownContainer);
        dropdown.click();

        BaseElement searchBox = new BaseElement(By.cssSelector("[type=text]"), dropdownContainer);
        searchBox.sendKeys(dropdownValue);

        BaseElement firstResult = new BaseElement(By.cssSelector("[title='" + dropdownValue + "']"), dropdownContainer);
        firstResult.click();
    }

}
