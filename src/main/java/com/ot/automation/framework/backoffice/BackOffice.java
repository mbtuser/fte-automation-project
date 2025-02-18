package com.ot.automation.framework.backoffice;

public class BackOffice {
    public IPPage ipPage;
    public DialogsBO dialogsBO;

    public BackOffice() {
        this.ipPage = new IPPage();
        this.dialogsBO = new DialogsBO();
    }
}