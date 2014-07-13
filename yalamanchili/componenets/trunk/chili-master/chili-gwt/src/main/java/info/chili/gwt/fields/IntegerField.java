package info.chili.gwt.fields;

import info.chili.gwt.composite.BaseFieldWithTextBox;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import info.chili.gwt.utils.Alignment;
import java.util.logging.Logger;

public class IntegerField extends BaseFieldWithTextBox {

    private Logger logger = Logger.getLogger(IntegerField.class.getName());

    public IntegerField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired, Alignment alignment) {
        super(constants, attributeName, className, readOnly, isRequired, alignment);
    }

    @UiConstructor
    @Deprecated
    public IntegerField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
    }

    public void setInteger(Integer number) {
        if (number != null) {
            textbox.setText(number.toString());
        }
    }

    public Integer getInteger() {
        Integer number = null;
        if (textbox.getText() == null || textbox.getText().trim().equals("")) {
            return null;
        }
        try {
            number = new Integer(textbox.getText());
        } catch (NumberFormatException nfe) {
            // TODO need to go to error page
            Window.alert("please enter a valid number");
            return null;
        }
        return number;
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        super.onKeyPress(event);
        allowDigitsOnly(event);
    }
}
