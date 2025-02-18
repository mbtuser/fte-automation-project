package com.ot.automation.framework.octane.ui.spaces;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class SiteAdminMainView extends BaseElement {

    public SiteSpacesContainer spacesForm = new SiteSpacesContainer(this);
    public SiteAdminMainView() {
        super(Locator.dataAid("admin-site-main-frame"));
    }
}