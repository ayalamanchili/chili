package info.chili.gwt.fields;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import info.chili.gwt.composite.BaseField;

import com.google.gwt.uibinder.client.UiConstructor;
import gr.open.client.TinyMCE;

public class RichTextField extends BaseField {

    TinyMCE editor = new TinyMCE();

    @UiConstructor
    public RichTextField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
        configureAddMainWidget();
        setReadOnly(readOnly);
    }

    @Override
    protected void configureAddMainWidget() {
        editor.ensureDebugId(className + "_" + attributeName + "_TB");
        editor.addStyleName("y-gwt-RichTextEditor");
//        bar.addStyleName("y-gwt-RichTexttoolBar");
        fieldPanel.insert(editor, 0);
    }

    public String getValue() {
        return editor.getText();
    }

    public void setValue(String value) {
        editor.setText(value);
    }

    public void setHtml(String html) {
        editor.setText(html);
    }

    public String getHtml() {
        return editor.getText();
    }

    @Override
    public void validate() {
        clearMessage();
    }

    public void setReadOnly(Boolean readOnly) {
        editor.setVisible(!readOnly);
    }
}
