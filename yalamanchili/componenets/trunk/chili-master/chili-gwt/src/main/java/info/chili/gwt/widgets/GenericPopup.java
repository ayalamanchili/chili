/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author anu
 */
//TODO move to chili-gwt
public class GenericPopup extends PopupPanel {

    private static GenericPopup instance;

    public static GenericPopup instance() {
        return instance;
    }

    public GenericPopup(Widget widget, int left, int top) {
        instance = this;
        addWidgets(widget, left, top);
        configure();
    }

    public GenericPopup(Widget widget, String styleName, int left, int top) {
        instance = this;
        addWidgets(widget, left, top);
        configure();
        useStyleName(styleName);
    }

    public GenericPopup(Widget widget) {
        instance = this;
        addWidgets(widget, Window.getClientWidth() / 3, Window.getClientHeight() / 3);
        configure();
    }

    protected void addWidgets(Widget widget, int left, int top) {
        FlowPanel panel = new FlowPanel();
        panel.add(widget);
        setWidget(panel);
        super.setPopupPosition(left, top);
    }

    protected void configure() {
        setAutoHideEnabled(true);
        this.addStyleName("genericPopup");
    }

    public void useStyleName(String styleName) {
        this.removeStyleName("genericPopup");
        this.addStyleName(styleName);
    }
}
