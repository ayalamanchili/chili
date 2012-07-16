package info.yalamanchili.gwt.widgets;

import info.yalamanchili.gwt.fields.PasswordField;
import info.yalamanchili.gwt.fields.StringField;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public abstract class CreateUserAbstract extends PopupPanel implements ClickHandler {
	FlowPanel panel = new FlowPanel();
	protected StringField firstnameTF = new StringField("First Name", "", "", false, false);
	protected StringField lastnameTF = new StringField("Last Name", "", "", false, false);
	// TODO rename to emailTF
	protected StringField usernameTF = new StringField("Email", "", "", false, true);
	protected PasswordField passwordTF = new PasswordField("Password", "", "");
	protected Button create = new Button("Create User");

	public CreateUserAbstract() {
		setWidget(panel);
		panel.add(firstnameTF);
		panel.add(lastnameTF);
		panel.add(usernameTF);
		panel.add(passwordTF);
		panel.add(create);
		create.addClickHandler(this);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(create)) {
			createUserClicked();
		}
	}

	public void show() {
		super.show();
	}

	public abstract void createUserClicked();
}
