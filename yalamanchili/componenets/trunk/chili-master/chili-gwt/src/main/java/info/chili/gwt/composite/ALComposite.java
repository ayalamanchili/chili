package info.chili.gwt.composite;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ALComposite.
 */
public abstract class ALComposite extends Composite {

    protected abstract void addListeners();

    protected abstract void configure();

    protected abstract void addWidgets();

    protected void init(Widget widget) {
        initWidget(widget);
        addListeners();
        configure();
        addWidgets();
    }
}
