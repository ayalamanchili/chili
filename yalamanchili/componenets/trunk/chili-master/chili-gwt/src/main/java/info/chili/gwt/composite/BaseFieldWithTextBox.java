package info.chili.gwt.composite;

import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.TextBox;

//TODO move commons stuff to base field
public abstract class BaseFieldWithTextBox extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {

    protected TextBox textbox = new TextBox();

    public TextBox getTextbox() {
        return textbox;
    }

    public BaseFieldWithTextBox(ConstantsWithLookup constants,
            String attributeName, String className, Boolean readOnly,
            Boolean required) {
        super(constants, attributeName, className, readOnly, required);
        configureAddMainWidget();
        textbox.ensureDebugId(className + "_" + attributeName + "_TB");
        setReadOnly(readOnly);
    }

    protected void configureAddMainWidget() {
        textbox.addStyleName("tfTextBox");
        fieldPanel.insert(textbox, 0);
        addListeners();
    }

    protected void addListeners() {
        textbox.addKeyPressHandler(this);
        textbox.addKeyUpHandler(this);
        textbox.addKeyDownHandler(this);
        textbox.addBlurHandler(this);
    }

    public void setReadOnly(Boolean readlOnly) {
        textbox.setReadOnly(readOnly);
        if (readOnly) {
            textbox.setEnabled(false);
        }
    }

    public void validate() {
    }

    public void setValue(String value) {
        textbox.setText(value);
    }

    public String getValue() {
        //TODO use getValue() insted of getText() since getText return blaml stirng if nothing is entered
        if (textbox.getValue() != null) {
            return textbox.getValue();
        } else {
            return null;
        }
    }
}
