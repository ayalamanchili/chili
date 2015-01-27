/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.widgets;

import info.chili.gwt.composite.LocalStorage;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import info.chili.gwt.composite.ALComposite;

/**
 *
 * @author ayalamanchili
 */
public class DocumentationWidget extends ALComposite implements ClickHandler {

    protected FlowPanel panel = new FlowPanel();
    protected CaptionPanel cp = new CaptionPanel();
    protected CheckBox doNotShowMsg = new CheckBox("Do not show this Information again");
    protected String docUrl;
    protected String key;

    public DocumentationWidget(String key, String docUrl) {
        this.key = key;
        this.docUrl = docUrl;
        init(cp);
    }

    @Override
    protected void addListeners() {
        doNotShowMsg.addClickHandler(this);
    }

    @Override
    protected void configure() {
        cp.setCaptionHTML("Documentation");

    }

    @Override
    protected void addWidgets() {
        panel.add(doNotShowMsg);
        Frame docPanel = new Frame(docUrl);
        docPanel.setHeight("35em");
        docPanel.setWidth("89em");
        panel.add(docPanel);
        cp.setContentWidget(panel);
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource().equals(doNotShowMsg)) {
            if (doNotShowMsg.getValue()) {
                LocalStorage.putValue(key, "true");
                GenericPopup.instance().hide();
            }
        }
    }

}
