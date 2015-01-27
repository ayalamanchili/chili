/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import info.chili.gwt.resources.ChiliImages;

/**
 *
 * @author ayalamanchili
 */
public class NewWindowPanel extends PopupPanel implements ClickHandler {

    private static NewWindowPanel instance;

    ClickableImage closeL = new ClickableImage("close", ChiliImages.INSTANCE.closeIcon_16_16());

    public static NewWindowPanel instance() {
        return instance;
    }

    public NewWindowPanel(Widget widget, int left, int top) {
        instance = this;
        addWidgets(widget, left, top);
        configure();
    }

    public NewWindowPanel(Widget widget, String styleName, int left, int top) {
        instance = this;
        addWidgets(widget, left, top);
        configure();
        useStyleName(styleName);
    }

    public NewWindowPanel(Widget widget) {
        instance = this;
        addWidgets(widget, Window.getClientWidth() / 3, Window.getClientHeight() / 3);
        configure();
    }

    protected void addWidgets(Widget widget, int left, int top) {
        FlowPanel panel = new FlowPanel();
        panel.add(closeL);
        panel.add(widget);
        setWidget(panel);
        super.setPopupPosition(left, top);
    }

    protected void configure() {
        closeL.getElement().getStyle().setProperty("float", "left");
        closeL.addClickHandler(this);
        this.addStyleName("genericPopup");
    }

    public void useStyleName(String styleName) {
        this.removeStyleName("genericPopup");
        this.addStyleName(styleName);
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource().equals(closeL)) {
            hide();
        }
    }
}
