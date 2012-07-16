package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseField;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.ListBox;

// TODO: Auto-generated Javadoc
/**
 * The Class EnumField.
 */
public class EnumField extends BaseField {

    Logger logger = Logger.getLogger(EnumField.class.getName());
    protected final ListBox listBox = new ListBox();

    public EnumField(String labelName, String attributeName, String className, Boolean readOnly, Boolean isRequired,
            String[] values) {
        super(labelName, attributeName, className, readOnly, isRequired);
        configureAddMainWidget();
        for (String value : values) {
            listBox.addItem(value.toString(), value.toString());
        }
        setReadOnly(readOnly);
    }

    public void setValue(String var) {
        listBox.clear();
        listBox.addItem(var, var);
    }

    public String getValue() {
        // TODO check if index is > 0 else null
        return listBox.getItemText(listBox.getSelectedIndex());
    }

    @Override
    protected void configureAddMainWidget() {
        listBox.ensureDebugId(className + "_" + attributeName + "_LB");
        fieldPanel.insert(listBox, 0);
    }

    public void setReadOnly(Boolean readOnly) {
        listBox.setEnabled(!readOnly);
    }

    @Override
    public void validate() {
        // TODO Auto-generated method stub
    }
}
