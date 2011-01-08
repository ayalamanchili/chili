package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseFieldWithTextBox;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.user.client.Window;

// TODO: Auto-generated Javadoc
/**
 * The Class IntegerField.
 */
public class IntegerField extends BaseFieldWithTextBox {

	public IntegerField(String text, Boolean readOnly, Boolean isRequired) {
		super(text, readOnly, isRequired);
	}

	public void setInteger(Integer number) {
		if (number != null)
			textbox.setText(number.toString());
	}

	public Integer getInteger() {
		Integer number = null;
		if (textbox.getText() == null || textbox.getText().trim().equals(""))
			return null;
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