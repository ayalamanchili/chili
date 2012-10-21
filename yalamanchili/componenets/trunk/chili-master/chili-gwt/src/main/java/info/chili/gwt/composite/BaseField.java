package info.chili.gwt.composite;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import info.chili.gwt.resources.ChiliImages;
import info.chili.gwt.utils.Utils;
import info.chili.gwt.widgets.ClickableImage;

public abstract class BaseField extends Composite implements BlurHandler {

    public BaseField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean required) {
        this.readOnly = readOnly;
        this.required = required;
        this.attributeName = attributeName;
        this.className = className;
        if (required) {
            label.setHTML(Utils.getAttributeLabel(attributeName, className, constants) + "<em>*</em>");
            label.addStyleName("tfRequired");
        } else {
            label.setHTML(Utils.getAttributeLabel(attributeName, className, constants));
        }
        configure();
        addWidgets();
        initWidget(panel);
        //TODO
        setMoreInfoDescription(Utils.getMoreInfoLabel(attributeName, className, constants));
    }

    protected void configure() {
        label.addStyleName("tfFieldHeader");
        label.ensureDebugId(className + "_" + attributeName + "_L");
        message.addStyleName("tfErrorMessage");
        message.ensureDebugId(className + "_" + attributeName + "_L");
    }

    protected void addWidgets() {
        panel.add(label);
        fieldPanel.add(fieldInfoIcon);
        fieldPanel.add(message);
        panel.add(fieldPanel);
    }

    /* used to add main widget to fieldPanel and add style class info */
    protected abstract void configureAddMainWidget();

    /**
     * called for validate on blur
     */
    protected abstract void validate();
    protected FlowPanel panel = new FlowPanel();
    //TODO change to flow panel with horizontal allignment
    protected HorizontalPanel fieldPanel = new HorizontalPanel();
    protected HTML label = new HTML();
    protected ClickableImage fieldInfoIcon = new ClickableImage("learn more", ChiliImages.INSTANCE.fieldInfoIcon());
    protected HTML message = new HTML();
    protected Boolean isValid = false;
    protected Boolean readOnly = false;
    protected Boolean required = false;
    // this is actual bean/entity attribute name can be used to validation
    // purposes
    public String attributeName = null;
    public String className = null;

    public FlowPanel getPanel() {
        return panel;
    }

    public HTML getLabel() {
        return label;
    }

    public void setLabelText(String name) {
        label.setHTML(name);
    }

    public void setMessage(String text) {
        message.setHTML(text);
    }

    public void clearMessage() {
        message.setHTML("");
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        this.isValid = valid;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public abstract void setReadOnly(Boolean readOnly);

    protected void setMoreInfoDescription(String moreInfo) {
        fieldInfoIcon.setTitle(moreInfo);
    }

    @Override
    public void onBlur(BlurEvent event) {
        validate();
    }
}
