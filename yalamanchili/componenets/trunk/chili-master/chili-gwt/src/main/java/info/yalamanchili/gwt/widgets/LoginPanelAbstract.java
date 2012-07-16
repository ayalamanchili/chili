package info.yalamanchili.gwt.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public abstract class LoginPanelAbstract extends PopupPanel {
	Label loginPrompt = new Label();

	Grid loginGrid = new Grid(3, 2);

	Label usernameL = new Label("Username");
	protected TextBox usernameTb = new TextBox();

	Label passwordL = new Label("Password");
	protected PasswordTextBox passwordTb = new PasswordTextBox();

	Button loginB = new Button("Login");

	public LoginPanelAbstract() {
		super(true);
		addWidgets();
		configure();
		setWidget(loginGrid);
	}

	protected void configure() {
		loginGrid.setBorderWidth(2);
		loginB.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				loginClicked();
			}

		});
		loginGrid.addStyleName("y-gwt-LoginPanel");
	}

	public void show() {
		super.show();
	}

	protected void addWidgets() {
		loginGrid.setWidget(0, 0, usernameL);
		loginGrid.setWidget(0, 1, usernameTb);
		loginGrid.setWidget(1, 0, passwordL);
		loginGrid.setWidget(1, 1, passwordTb);
		loginGrid.setWidget(2, 1, loginB);
	}

	protected abstract void loginClicked();

	protected abstract void setAutoLogout();
}
