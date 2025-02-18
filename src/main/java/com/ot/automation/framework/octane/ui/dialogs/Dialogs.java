package com.ot.automation.framework.octane.ui.dialogs;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;

public class Dialogs {

    public final DevOpsDialogs devOpsDialogs = new DevOpsDialogs();
    public final AddTestSuiteDialog addTestSuiteDialog = new AddTestSuiteDialog();
    public final AddExistingTestsDialog addExistingTestsDialog = new AddExistingTestsDialog();
    public final AddScheduleDialog addScheduleDialog = new AddScheduleDialog();
    public final ModelTestGeneratorDialog modelTestGeneratorDialog = new ModelTestGeneratorDialog();
    public final RunTestDialog runTestDialog = new RunTestDialog();
    public final CreateSampleProjectDialog createSampleProjectDialog = new CreateSampleProjectDialog();
    public final WarningMessageDialog warningMessageDialog = new WarningMessageDialog();
    public final WhatsNewDialog whatsNewDialog = new WhatsNewDialog();

    public void clickOk() {
        BaseElement button = new BaseElement(Locator.dataAid("dialog.ok"), null);
        if (button.isVisible()) {
            button.click();
        }
    }

    public void clickYes() {
        BaseElement button = new BaseElement(Locator.dataAid("dialog.yes"), null);
        button.expectVisible();
        button.click();
    }
}