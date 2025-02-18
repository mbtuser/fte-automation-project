package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.ui.DateTimeEditor;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.SmartEditor;

public class AddScheduleDialog extends BaseElement {
    public AddScheduleDialog() {
        super(Locator.dataAid("new-entity-dialog-container"), null);
    }

    private final BaseElement nameParent = new BaseElement(Locator.dataAid("name"), this);
    public BaseElement name = new BaseElement(Locator.dataAid("alm-string-field-editor-input-box"), nameParent);
    public SmartEditor runMode = new SmartEditor(Locator.cssSelector("[data-aid='run_type']"), this);
    public DateTimeEditor startTime = new DateTimeEditor(Locator.cssSelector("[data-aid='start_time']"), this);

    private final BaseElement repeatEveryParent = new BaseElement(Locator.dataAid("cron-expression-editor"), this);
    public BaseElement repeatEvery = new BaseElement(Locator.cssSelector("input"), repeatEveryParent);

    public void clickAdd() {
        new BaseElement(Locator.dataAid("alm-entity-form-buttons-add"), this).click();
    }

    public void clickAddAndEdit() {
        new BaseElement(Locator.dataAid("alm-entity-form-buttons-add-and-edit"), this).click();
    }
}