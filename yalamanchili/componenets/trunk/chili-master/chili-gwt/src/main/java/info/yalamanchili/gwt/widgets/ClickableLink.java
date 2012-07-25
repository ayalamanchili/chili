package info.yalamanchili.gwt.widgets;

import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickableLink.
 */
public class ClickableLink extends Label {

    public ClickableLink(String name) {
        this.setText(name);
        this.setTitle(name);
        this.addStyleName("y-gwt-ClickableLink");
        this.ensureDebugId(name + "CL");
    }
}
