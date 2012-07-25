package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseFieldWithTextBox;

import java.math.BigDecimal;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;

public class CurrencyField extends BaseFieldWithTextBox {
    // TODO add Local info to constructor

    protected static NumberFormat fmt = NumberFormat.getCurrencyFormat();

    public CurrencyField(String labelName, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(labelName, attributeName, className, readOnly, isRequired);
    }

    public BigDecimal getCurrency() {
        BigDecimal value = null;
        if (textbox.getText() == null || textbox.getText().trim().equals("")) {
            return null;
        }
        try {
            value = new BigDecimal(textbox.getText());
        } catch (NumberFormatException e) {
            Window.alert("please enter a valid amount");
            return null;
        }
        return value;
    }

    public void setValue(BigDecimal var, boolean format) {
        if (var != null) {
            if (format) {
                textbox.setText(fmt.format(var));
            } else {
                textbox.setText(var.toString());
            }
        }
    }

    public static String format(BigDecimal value) {
        return (value == null) ? "" : fmt.format(value);
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        Integer eventValue = (int) event.getCharCode();
        // TODO check is the entered key is number or dot
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
    // TODO
    // check validation on loose focus?
}
