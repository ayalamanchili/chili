package info.chili.gwt.fields;

import info.chili.gwt.composite.BaseFieldWithTextBox;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import info.chili.gwt.utils.Alignment;

public class FloatField extends BaseFieldWithTextBox {

    public FloatField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired, Alignment alignment) {
        super(constants, attributeName, className, readOnly, isRequired, alignment);
    }

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
    public void onKeyPress(KeyPressEvent event) {
        super.onKeyPress(event);
        allowDigitsOnly(event);
    }
}
