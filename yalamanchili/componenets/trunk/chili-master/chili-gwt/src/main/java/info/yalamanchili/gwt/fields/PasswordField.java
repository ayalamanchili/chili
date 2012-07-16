package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseField;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class PasswordField extends BaseField {

	protected PasswordTextBox password = new PasswordTextBox();

	@UiConstructor
	public PasswordField(String labelName, String attributeName, String className) {
		super(labelName, attributeName, className, false, false);
		fieldPanel.insert(password, 1);
                password.ensureDebugId(className + "_" + attributeName + "_PTB");
	}

	public String getPassword() {
		return password.getText();
	}

	@Override
	protected void configureAddMainWidget() {
		// TODO Auto-generated method stub

	}

	public void setReadOnly(Boolean readOnly) {
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
