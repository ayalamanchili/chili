/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
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
public class TextAreaField extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler, FocusHandler, ClickHandler {

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

    public TextAreaField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean required, boolean defaultText, Alignment alignment) {
        this(constants, attributeName, className, readOnly, required, alignment);

    }

    @Deprecated
    public TextAreaField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean required) {
        this(constants, attributeName, className, readOnly, required, Alignment.HORIZONTAL);
    }

    public void setBackgroundText() {
        textArea.setText(moreInfoText);
        textArea.addStyleName(backgroundTextStyle);
    }

    public void hidePrompt() {
        textArea.setText(null);
        textArea.removeStyleName(backgroundTextStyle);
    }

    @Override
    public void onFocus(FocusEvent event) {
        textArea.setCursorPos(0);
    }

    @Override
    public void onClick(ClickEvent event) {
        if (moreInfoText.equals(textArea.getText())) {
            hidePrompt();
        }
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
        textArea.addFocusHandler(this);
        textArea.addClickHandler(this);
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
        if (textArea.getValue() != null && !textArea.getValue().equals(moreInfoText)) {
            return textArea.getValue();
        } else {
            return null;
        }
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if (moreInfoText.equals(textArea.getText())
                && !(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB)) {
            hidePrompt();
        }
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
