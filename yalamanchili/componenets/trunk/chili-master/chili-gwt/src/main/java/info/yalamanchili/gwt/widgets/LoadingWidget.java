package info.yalamanchili.gwt.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadingWidget.
 */
public class LoadingWidget extends PopupPanel {

    /**
     * The loading.
     */
    Label loading = new Label("Loading...");

    /**
     * Instantiates a new loading widget.
     */
    public LoadingWidget() {
        super(true);
        setWidget(loading);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.PopupPanel#show()
     */
    public void show() {
        int left = Window.getClientWidth() / 3;
        this.setPopupPosition(left, 0);
        addStyleName("y-gwt-ResponseStatusBar");
        super.show();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.PopupPanel#hide()
     */
    public void hide() {
        removeStyleName("y-gwt-ResponseStatusBar");
        super.hide();
    }
}
