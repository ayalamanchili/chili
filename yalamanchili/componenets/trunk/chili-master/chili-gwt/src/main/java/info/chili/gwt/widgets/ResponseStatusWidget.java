package info.chili.gwt.widgets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseStatusWidget.
 */
public class ResponseStatusWidget extends PopupPanel {

    /**
     * The response status.
     */
    Label responseStatus = new Label("");

    /**
     * Instantiates a new response status widget.
     */
    public ResponseStatusWidget() {
        super(true);
        this.ensureDebugId("response_status_W");
        setWidget(responseStatus);
    }

    /**
     * Show.
     *
     * @param message the message
     */
    public void show(String message) {
        int left = Window.getClientWidth() / 3;
        this.setPopupPosition(left, 0);
        super.show();
        responseStatus.setText(message);
        addStyleName("y-gwt-ResponseStatusBar");
        Timer timer = new Timer() {
            @Override
            public void run() {
                hide();
            }
        };
        //TODO externalze this
        timer.schedule(3000);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.PopupPanel#hide()
     */
    public void hide() {
        super.hide();
    }
}
