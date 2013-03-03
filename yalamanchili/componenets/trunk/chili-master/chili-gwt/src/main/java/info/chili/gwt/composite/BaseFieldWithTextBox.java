package info.chili.gwt.composite;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.TextBox;
import info.chili.gwt.listeners.KeyPressListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//TODO move commons stuff to base field
public abstract class BaseFieldWithTextBox extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {

    private Logger logger = Logger.getLogger(BaseFieldWithTextBox.class.getName());
    protected List<KeyPressListener> enterKeyPressedListeners = new ArrayList<KeyPressListener>();
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

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
            enterKeyPressed(event);
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
    }

    @Override
    public void onKeyDown(KeyDownEvent arg0) {
    }

    public void addEnterKeyPressesListener(KeyPressListener listner) {
        this.enterKeyPressedListeners.add(listner);
    }

    protected void allowDigitsOnly(KeyPressEvent event) {
        logger.info("dddd"+event.getNativeEvent().getKeyCode());
        event.getNativeEvent().getKeyCode();
        //This is for support for firefox for special characters like enter,tab,etc...
        if (event.getUnicodeCharCode() == 0) {
            return;
        }
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER || event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB || event.getNativeEvent().getKeyCode() == 46) {
            return;
        }
        if (!Character.isDigit(event.getCharCode())) {
            setMessage("invalid value");
            textbox.cancelKey();
        } else {
            clearMessage();
        }
    }

    protected void enterKeyPressed(KeyPressEvent event) {
        for (KeyPressListener listener : enterKeyPressedListeners) {
            listener.keyPressed(event);
        }
    }
}
