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
        listBox.addItem(var);
    }
    
    public void selectValue(String value) {
        logger.info("finding:" + value);
        for (int i = 0; i < listBox.getItemCount(); i++) {
            logger.info(listBox.getItemText(i));
            if (listBox.getItemText(i) != null && listBox.getItemText(i).equalsIgnoreCase(value)) {
                listBox.setSelectedIndex(i);
            }
        }
    }
    
    public String getValue() {
        if (listBox.getSelectedIndex() > 0) {
            return listBox.getItemText(listBox.getSelectedIndex());
        } else {
            return null;
        }
    }
    
    @Override
    protected void configureAddMainWidget() {
        listBox.insertItem("SELECT", 0);
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
