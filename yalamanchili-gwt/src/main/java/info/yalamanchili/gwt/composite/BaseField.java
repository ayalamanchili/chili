package info.yalamanchili.gwt.composite;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public abstract class BaseField extends Composite {

	public BaseField(String labelName, Boolean readOnly, Boolean required) {
		this.readOnly = readOnly;
		this.required = required;
		if (required) {
			label.setHTML(labelName + "<em>*</em>");
			label.addStyleName("tfRequired");
		} else {
			label.setHTML(labelName);
		}
		configure();
		addWidgets();
		initWidget(panel);
	}

	protected void configure() {
		label.addStyleName("tfFieldHeader");
		message.addStyleName("tfErrorMessage");
	}

	protected void addWidgets() {
		panel.add(label);
		fieldPanel.add(message);
		panel.add(fieldPanel);
	}

	/* used to add main widget to fieldPanel and add style class info */
	protected abstract void configureAddMainWidget();

	protected FlowPanel panel = new FlowPanel();

	protected HorizontalPanel fieldPanel = new HorizontalPanel();

	protected HTML label = new HTML();

	protected HTML message = new HTML();

	protected Boolean isValid = false;

	protected Boolean readOnly = false;

	protected Boolean required = false;

	public FlowPanel getPanel() {
		return panel;
	}

	public HTML getLabel() {
		return label;
	}

	public void setMessage(String text) {
		message.setHTML(text);
	}

	public void clearMessage() {
		message.setHTML("");
	}

	public Boolean getValid() {
		return isValid;
	}

	public void setValid(Boolean valid) {
		this.isValid = valid;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

}