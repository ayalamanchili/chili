package info.yalamanchili.gwt.composite;

import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.TextBox;

//TODO move commons stuff to base field
public abstract class BaseFieldWithTextBox extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {

    protected TextBox textbox = new TextBox();

    public TextBox getTextbox() {
        return textbox;
    }

    public BaseFieldWithTextBox(String labelName, String attributeName, String className, Boolean readOnly,
            Boolean required) {
        super(labelName, attributeName, className, readOnly, required);
        configureAddMainWidget();
        textbox.setTitle(labelName);
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
    }

    public void validate() {
    }

    public void setValue(String value) {
        textbox.setText(value);
    }

    public String getValue() {
        if (textbox.getText() != null && !textbox.getText().trim().isEmpty()) {
            return textbox.getText();
        } else {
            return null;
        }
    }
}
