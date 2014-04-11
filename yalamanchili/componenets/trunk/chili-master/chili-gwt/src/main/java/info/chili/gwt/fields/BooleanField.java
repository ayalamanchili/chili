package info.chili.gwt.fields;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import info.chili.gwt.composite.BaseField;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.CheckBox;
import info.chili.gwt.utils.Alignment;

public class BooleanField extends BaseField {

    protected CheckBox box = new CheckBox();

    public CheckBox getBox() {
        return box;
    }

    public BooleanField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired, Alignment alignment) {
        super(constants, attributeName, className, readOnly, isRequired, alignment);
        //TODO move this calls to super
        configureAddMainWidget();
        setReadOnly(readOnly);
    }

    @UiConstructor
    public BooleanField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
        // box.setText(text);
        configureAddMainWidget();
        setReadOnly(readOnly);
    }

    public void setValue(Boolean value) {
        if (value == null) {
            box.setValue(false);
        } else {
            box.setValue(value);
        }
    }

    public Boolean getValue() {
        if (box.getValue()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void configureAddMainWidget() {
        fieldPanel.insert(box, 0);
        box.ensureDebugId(className + "_" + attributeName + "_CB");
    }

    @Override
    public void validate() {
        clearMessage();
    }

    public void setReadOnly(Boolean readlOnly) {
        box.setEnabled(!readlOnly);
    }

    @Override
    public void focus(boolean focus) {

    }
}
