/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.fields;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.TextArea;
import info.chili.gwt.composite.BaseField;
import info.chili.gwt.utils.Alignment;

/**
 *
 * @author ayalamanchili
 */
public class TextAreaField extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {

    protected TextArea textArea = new TextArea();

    public TextArea getTextbox() {
        return textArea;
    }

    public TextAreaField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean required, Alignment alignment) {
        super(constants, attributeName, className, readOnly, required, alignment);
        configureAddMainWidget();
        textArea.ensureDebugId(className + "_" + attributeName + "_TB");
        setReadOnly(readOnly);
    }

    @Deprecated
    public TextAreaField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean required) {
        super(constants, attributeName, className, readOnly, required);
        configureAddMainWidget();
        textArea.ensureDebugId(className + "_" + attributeName + "_TB");
        setReadOnly(readOnly);
    }

    protected void configureAddMainWidget() {
        textArea.addStyleName("tfTextArea");
        fieldPanel.insert(textArea, 0);
        addListeners();
    }

    protected void addListeners() {
        textArea.addKeyPressHandler(this);
        textArea.addKeyUpHandler(this);
        textArea.addKeyDownHandler(this);
        textArea.addBlurHandler(this);
    }

    public void setReadOnly(Boolean readlOnly) {
        textArea.setReadOnly(readOnly);
        if (readOnly) {
            textArea.setEnabled(false);
        }
    }

    public void setVisibleLines(int lines) {
        textArea.setVisibleLines(lines);
    }

    public void validate() {
    }

    public void setValue(String value) {
        textArea.setText(value);
    }

    public String getValue() {
        //TODO use getValue() insted of getText() since getText return blaml stirng if nothing is entered
        if (textArea.getValue() != null) {
            return textArea.getValue();
        } else {
            return null;
        }
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
    }

    @Override
    public void onKeyDown(KeyDownEvent event) {
    }

    @Override
    public void focus(boolean focus) {
        textArea.setFocus(true);
    }
}
