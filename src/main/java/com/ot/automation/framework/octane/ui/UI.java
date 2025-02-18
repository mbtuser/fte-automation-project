package com.ot.automation.framework.octane.ui;

import com.ot.automation.framework.octane.framework.BaseElement;
import com.ot.automation.framework.octane.framework.Locator;
import com.ot.automation.framework.octane.ui.dialogs.Dialogs;
import com.ot.automation.framework.octane.ui.fte.FunctionalTestingExecutionView;
import com.ot.automation.framework.octane.ui.mbt.ModelBasedTestingView;
import com.ot.automation.framework.octane.ui.spaces.SiteAdminMainView;
import com.ot.automation.framework.octane.ui.spaces.SpacesMainView;

import static com.ot.automation.framework.octane.framework.GeneralUtils.waitLoadingBar;

public class UI {
    public SiteAdminMainView siteAdminMainView;
    public SpacesMainView spacesMainView;
    public FunctionalTestingExecutionView functionalTestingView;
    public ModelBasedTestingView modelBasedTestingView;
    public LoginPage loginPage;
    public Dialogs dialogs;
    public Notifications notifications;
    public DocViews docViews;

    public UI() {
        try {
            this.siteAdminMainView = new SiteAdminMainView();
            this.spacesMainView = new SpacesMainView();
            this.functionalTestingView = new FunctionalTestingExecutionView();
            this.modelBasedTestingView = new ModelBasedTestingView();
            this.loginPage = new LoginPage();
            this.dialogs = new Dialogs();
            this.notifications = new Notifications();
            this.docViews = new DocViews();

        } catch (Throwable exception) {
            System.err.println("Failed to init UI class: " + exception);
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static void expectUiToBeLoaded() {
        //We want to make sure UI is loaded by expecting a common element that always appear for both VE menu and the old menu.
        waitLoadingBar();
        new BaseElement(Locator.dataAid("masthead-view")).expectVisible();
    }
}