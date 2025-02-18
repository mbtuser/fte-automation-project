package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.GeneralUtils;
import com.ot.automation.framework.octane.framework.Locator;

import java.awt.*;

public class CreateSampleProjectDialog extends BaseElement {
    private BaseElement seleniumButtonContainer = new BaseElement(Locator.cssSelector("[class*=\"mbt-data-population-slide-select-framework-options-item\"]"), this);
    private BaseElement openTextFunctionalTestingButtonContainer = new BaseElement(Locator.cssSelector("[class*=\"mbt-data-population-slide-select-framework-options-uft\"]"), this);
    private BaseElement manualTestingButtonContainer = new BaseElement(Locator.cssSelector("[class*=\"mbt-data-population-slide-select-framework-options-manual\"]"), this);

    public CreateSampleProjectDialog() {
        super(Locator.className("mbt-data-population-wizard-dialog"));
    }

    public void selectSelenium() {
        new BaseElement(Locator.dataAid("radio-button"), seleniumButtonContainer).click();
    }

    public void selectOpenTextFunctionalTesting() {
        new BaseElement(Locator.dataAid("radio-button"), openTextFunctionalTestingButtonContainer).click();
    }

    public void selectManualTesting() {
        new BaseElement(Locator.dataAid("radio-button"), manualTestingButtonContainer).click();
    }

    public void expectPopulatedSelenium() {
        seleniumButtonContainer.expectTextToContain("Populated");
    }

    public void expectPopulatedOpenTextFunctionalTesting() {
        openTextFunctionalTestingButtonContainer.expectTextToContain("Populated");
    }

    public void expectPopulatedManualTesting() {
        manualTestingButtonContainer.expectTextToContain("Populated");
    }

    public void clickTryAgain() {
        BaseElement tryAgainButtonContainer = new BaseElement(Locator.cssSelector("[class=\"mbt-data-population-summary-slide-failed-button-container\"]"), this);
        BaseElement tryAgainButton = new BaseElement(Locator.dataAid("button"), tryAgainButtonContainer);
        tryAgainButton.click();
    }

    public void clickNext() {
        BaseElement nextButton = new BaseElement(Locator.cssSelector("[class*=\"mbt-data-population-body-navigation-active-button\"]"), this);
        nextButton.click();
    }

    public void clickBack() {
        clickLeftFooterButton();
    }

    public void clickCreateAnotherProject() {
        clickLeftFooterButton();
    }

    public void clickCreate() {
        clickRightFooterButton();
    }

    public void closeByX() {
        BaseElement xButton = new BaseElement(Locator.cssSelector("[class*=\"close-button\"]"), this);
        xButton.click();
    }

    public void clickDeleteSample() {
        BaseElement deleteButton = new BaseElement(Locator.cssSelector("[data-aid*=\"remove-sample\"]"), this);
        deleteButton.click();
    }

    public void waitForDataPopulation() {
        GeneralUtils.waitAtMostFor(180000, "Timeout while waiting for data population", () -> {
            BaseElement summaryMessage = new BaseElement(Locator.cssSelector("[class*=\"mbt-data-population-summary-slide-\"]"), this);
            return summaryMessage.isVisible() ? true : null;
        });
    }

    public void clickUnitsLink() {
        clickLink(2);
    }

    public void clickModelsLink() {
        clickLink(3);
    }

    public void clickTestsLink() {
        clickLink(4);
    }

    public void clickUnitsLinkManual() {
        clickLink(1);
    }

    public void clickModelsLinkManual() {
        clickLink(2);
    }

    public void clickTestsLinkManual() {
        clickLink(3);
    }

    private void clickLeftFooterButton() {
        BaseElement leftButton = new BaseElement(Locator.cssSelector("[class*=\"left-footer-button\"]"), this);
        leftButton.click();
    }

    private void clickRightFooterButton() {
        BaseElement rightButton = new BaseElement(Locator.cssSelector("[class*=\"right-footer-button\"]"), this);
        rightButton.click();
    }

    private void clickLink(int rowNum) {
        BaseElement entityTypesContainer = new BaseElement(Locator.cssSelector("[class*=\"data-population-summary-slide-content-container\"]"), this);
        BaseElement row = new BaseElement(Locator.cssSelector("[class=\"mbt-data-population-summary-slide-entity-types-entry\"]:nth-of-type(" + rowNum + ")"), entityTypesContainer);
        BaseElement link = new BaseElement(Locator.cssSelector("a"), row);
        link.click();
    }
}