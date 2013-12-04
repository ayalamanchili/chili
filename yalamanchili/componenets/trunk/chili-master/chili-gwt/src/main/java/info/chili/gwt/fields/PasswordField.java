package info.chili.gwt.fields;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import info.chili.gwt.composite.BaseField;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.PasswordTextBox;
import info.chili.gwt.utils.Alignment;

public class PasswordField extends BaseField {

    protected PasswordTextBox password = new PasswordTextBox();

    public PasswordField(ConstantsWithLookup constants, String attributeName, String className, boolean required, Alignment alignment) {
        super(constants, attributeName, className, false, required, alignment);
        fieldPanel.insert(password, 0);
        password.ensureDebugId(className + "_" + attributeName + "_PTB");
    }

    @UiConstructor
    public PasswordField(ConstantsWithLookup constants, String attributeName, String className, boolean required) {
        super(constants, attributeName, className, false, required);
        fieldPanel.insert(password, 0);
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
        clearMessage();
    }
}
