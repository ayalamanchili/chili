package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseField;
import info.yalamanchili.gwt.widgets.RichTextToolBar;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.RichTextArea;

public class RichTextField extends BaseField {
	RichTextArea area = new RichTextArea();
	RichTextToolBar bar = new RichTextToolBar(area);

	@UiConstructor
	public RichTextField(String labelName, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
		super(labelName, attributeName, className, readOnly, isRequired);
		configureAddMainWidget();
		setReadOnly(readOnly);
	}

	@Override
	protected void configureAddMainWidget() {
            area.ensureDebugId(className + "_" + attributeName + "_TB");
		area.addStyleName("y-gwt-RichTextEditor");
		bar.addStyleName("y-gwt-RichTexttoolBar");
		panel.insert(bar, 1);
		fieldPanel.insert(area, 0);
	}

	public String getValue() {
		return area.getText();
	}

	public void setValue(String value) {
		area.setText(value);
	}

	public void setHtml(String html) {
		area.setHTML(html);
	}

	public String getHtml() {
		return area.getHTML();
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	public void setReadOnly(Boolean readOnly) {
		area.setEnabled(readOnly);
	}
}
