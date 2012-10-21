package info.chili.gwt.fields;

import info.chili.gwt.composite.BaseFieldWithTextBox;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;

public class FloatField extends BaseFieldWithTextBox {

    @UiConstructor
    public FloatField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
    }

    public Float getFloat() {
        Float value = null;
        if (textbox.getText() == null || textbox.getText().trim().equals("")) {
            return null;
        }
        try {
            value = new Float(textbox.getText());
        } catch (NumberFormatException e) {
            Window.alert("please enter a valid decimal amount");
            return null;
        }
        return value;
    }

    public void setFloat(Float var) {
        if (var != null) {
            textbox.setText(var.toString());
        }
    }

    @Override
    // TODO add logic to accept backspace as valid
    public void onKeyPress(KeyPressEvent event) {
        Integer eventValue = (int) event.getCharCode();
        if (!(Character.isDigit(event.getCharCode()) || eventValue.equals(new Integer(46)))) {
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
