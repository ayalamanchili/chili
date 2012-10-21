package info.chili.gwt.fields;

import info.chili.gwt.composite.BaseFieldWithTextBox;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class LongField.
 */
public class LongField extends BaseFieldWithTextBox {

    private Logger logger = Logger.getLogger(LongField.class.getName());

    @UiConstructor
    public LongField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
    }

    public Long getLong() {
        Long value = null;
        if (textbox.getText() == null || textbox.getText().trim().equals("")) {
            return null;
        }
        try {
            value = new Long(textbox.getText());
        } catch (NumberFormatException e) {
            Window.alert("please enter a valid number");
            return null;
        }
        return value;
    }

    public void setLong(Long number) {
        if (number != null) {
            textbox.setText(number.toString());
        }
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        //TODO move to super class
        //This is for support for firefox for special characters like enter,tab,etc...
        if (event.getUnicodeCharCode() == 0) {
            return;
        }
        if (!Character.isDigit(event.getCharCode())) {
            setMessage("invalid value");
            textbox.cancelKey();
        } else {
            clearMessage();
        }

    }

    @Override
    public void onKeyUp(KeyUpEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onKeyDown(KeyDownEvent arg0) {
        // TODO Auto-generated method stub
    }
}
