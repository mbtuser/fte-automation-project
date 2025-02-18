package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.SmartEditor;
import com.ot.automation.framework.octane.ui.Toolbar;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DetailsTab extends BaseElement {

    public Toolbar toolbar = new Toolbar(this);

    public DetailsTab(BaseElement parent) {
        super(Locator.cssSelector("[ui-view='entityForm']"), parent);
    }

    public void setDropdownField(String fieldName, String value) {
        SmartEditor field = new SmartEditor(Locator.cssSelector("smart-editor"), new BaseElement(Locator.cssSelector("[data-aid='" + fieldName + "']")));
        field.expectVisible();
        field.setValue(value);
    }

    public void setMachineTemplate(String value) {
        BaseElement field = new BaseElement(Locator.cssSelector("[data-aid='machine_template']"), this);
        field.click();

        BaseElement dropdownOption = new BaseElement(Locator.cssSelector("[data-text-value*='" + value + "']"), this);
        dropdownOption.click();
    }

    public void setExecutionMode(String value) {
        BaseElement field = new BaseElement(Locator.cssSelector("[data-aid='fte_execution_mode']"), this);
        if(!field.isVisible()){
            displayField("Run mode");
        }
        field.click();

        BaseElement dropdownOption = new BaseElement(Locator.cssSelector("[data-aid*='list_node_list_node.fte_execution_mode." + value.toLowerCase() + "']"), this);
        dropdownOption.click();
    }

    public void displayField(String field) {
        BaseElement customizeFieldsButton = new BaseElement(By.cssSelector("[data-aid*='selectEntityDetailsFieldsAction']"), this);
        customizeFieldsButton.click();

        BaseElement fieldEditorContainer = new BaseElement(Locator.dataAid("field-editor-selector-container"));
        BaseElement fieldEditorInput = new BaseElement(Locator.dataAid("field-editor-search-input-box"), fieldEditorContainer);
        fieldEditorInput.sendKeys(field);

        BaseElement resultElement = new BaseElement(By.cssSelector("[data-text-value='" + field + "']"), fieldEditorContainer);
        resultElement.click();

//        BaseElement firstResult = new BaseElement(By.cssSelector("[data-id='alm-search-results-item']"), fieldEditorContainer);
//        Boolean isChecked = Boolean.valueOf(firstResult.getAttribute("aria-checked"));
//
//        if(!isChecked){
//            firstResult.click();
//        }

//        Assert.assertTrue(Boolean.valueOf(firstResult.getAttribute("aria-checked")), field + " field was not selected");
        customizeFieldsButton.click();
    }
}