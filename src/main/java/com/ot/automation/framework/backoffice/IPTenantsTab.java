package com.ot.automation.framework.backoffice;

import com.ot.automation.framework.octane.framework.BaseElement;
import org.openqa.selenium.By;

import java.util.List;

public class IPTenantsTab extends BaseElement {
    public IPTenantsTab(BaseElement parent) {
        super(By.cssSelector("[class*='tenants-wrapper']"), parent);
    }

    public void addTenant(String tenantName, String farm, String timezone, String offeringType, String serverEnv, List<Integer> subQuantities, List<String> subNames) {
        clickAddTenant();
        clickCreateTenant();

        BaseElement createTenantDialog = new BaseElement(By.cssSelector("[class*='tenant-selection-modal']"));

        BaseElement tenantNameTextbox = new BaseElement(By.cssSelector("[ng-model='editedTenant.displayName']"), createTenantDialog);
        tenantNameTextbox.sendKeys(tenantName);

        BaseElement farmButton = new BaseElement(By.cssSelector("[value='farm']"), createTenantDialog);
        farmButton.click();

        // Select farm
        BaseElement farmDropdown = new BaseElement(By.cssSelector("[title='Select farm']"), createTenantDialog);
        farmDropdown.click();

        BaseElement farmSearchBox = new BaseElement(By.xpath("//span[@title='Select farm']/parent::a/following-sibling::div/div[@class='chosen-search']/input[@type='text']"), createTenantDialog);
        farmSearchBox.sendKeys(farm);

        BaseElement farmResults = new BaseElement(By.cssSelector("[title='" + farm + "']"), createTenantDialog);
        farmResults.click();

        // Select timezone
        BaseElement timezoneDropdown = new BaseElement(By.cssSelector("[title='Select the data center\\'s time zone']"), createTenantDialog);
        timezoneDropdown.click();

        BaseElement timezoneResults = new BaseElement(By.cssSelector("[title='(GMT +2) Europe/Bucharest"), createTenantDialog);
        timezoneResults.click();

        // Select offering type
        BaseElement offeringTypeDropdownParent = new BaseElement(By.cssSelector("[ng-class*='offeringTypeError']"), createTenantDialog);
        BaseElement offeringTypeDropdown = new BaseElement(By.cssSelector("[title='Select an Option']"), offeringTypeDropdownParent);
        offeringTypeDropdown.click();

        BaseElement offeringTypeResults = new BaseElement(By.cssSelector("[title='Basic']"), offeringTypeDropdownParent);
        offeringTypeResults.click();

        // Select server environment
        BaseElement serverEnvDropdownParent = new BaseElement(By.cssSelector("[ng-class*='envError']"), createTenantDialog);
        BaseElement serverEnvDropdown = new BaseElement(By.cssSelector("[title='Select an Option']"), serverEnvDropdownParent);
        serverEnvDropdown.click();

        BaseElement serverEnvResults = new BaseElement(By.cssSelector("[title='Dev']"), serverEnvDropdownParent);
        serverEnvResults.click();

        // Set quantities
        for (int i = 0; i < subNames.size(); i++) {
            BaseElement textbox = new BaseElement(By.xpath("//span[span[normalize-space(@title) = '" + subNames.get(i) + "']]/span/input[@type='text']"), createTenantDialog);
            textbox.sendKeys(subQuantities.get(i).toString());
        }

        BaseElement doneButton = new BaseElement(By.cssSelector("[ng-click='save();']"), createTenantDialog);
        doneButton.click();
    }

    private void clickAddTenant() {
        BaseElement addButton = new BaseElement(By.cssSelector("[data-ng-click='addNewTenant();']"), this);
        addButton.click();
    }

    private void clickCreateTenant() {
        BaseElement createTenantPopup = new BaseElement(By.cssSelector("[class*='tenant-selection-modal']"));

        BaseElement searchBox = new BaseElement(By.cssSelector("[ng-model='tenantSearchText']"), createTenantPopup);
        searchBox.sendKeys("Create tenant");

        BaseElement createTenantButton = new BaseElement(By.cssSelector("[ng-click='selectTenant();']"), createTenantPopup);
        createTenantButton.click();
    }
}