package info.chili.gwt.widgets;

import info.chili.gwt.fields.PasswordField;
import info.chili.gwt.fields.StringField;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public abstract class CreateUserAbstract extends PopupPanel implements ClickHandler {

    FlowPanel panel = new FlowPanel();
    //TODO fix constructors
    protected StringField firstnameTF = new StringField(null, "", "", false, false);
    protected StringField lastnameTF = new StringField(null, "", "", false, false);
    // TODO rename to emailTF
    protected StringField usernameTF = new StringField(null, "", "", false, true);
    protected PasswordField passwordTF = new PasswordField(null, "", "", true);
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
