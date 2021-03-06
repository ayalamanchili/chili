/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
package info.chili.gwt.crud;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import java.util.logging.Logger;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Button;
import info.chili.gwt.fields.TextAreaField;
import info.chili.gwt.utils.Utils;
import java.util.Map;

public abstract class UpdateComposite extends CRUDComposite implements ClickHandler {

    Logger logger = Logger.getLogger(UpdateComposite.class.getName());
    public Button update = new Button("Update");
    public Button cancel = new Button("Cancel");

    public void initUpdateComposite(JSONObject entity, String className, final ConstantsWithLookup constants) {
        this.entity = entity;
        init(className, false, constants);
        entityCaptionPanel.addStyleName("y-gwt-UpdateEntityCaptionPanel");
        entityFieldsPanel.addStyleName("y-gwt-UpdateEntityDisplayWidget");
        basePanel.addStyleName("y-gwt-UpdateBasePanel");
        entityActionsPanel.add(update);
        update.addClickHandler(this);
        update.addStyleName("y-gwt-updateB");
        configureCancel();
        populateFieldsFromEntity(entity);
    }


    protected void initUpdateComposite(String id, String className, final ConstantsWithLookup constants) {
        this.entityId = id;
        init(className, false, constants);
        entityCaptionPanel.addStyleName("y-gwt-UpdateEntityCaptionPanel");
        entityFieldsPanel.addStyleName("y-gwt-UpdateEntityDisplayWidget");
        basePanel.addStyleName("y-gwt-UpdateBasePanel");
        entityActionsPanel.add(update);
        update.addClickHandler(this);
        configureCancel();
        loadEntity(entityId);
    }

    @Override
    public void onClick(ClickEvent event) {
        entity = populateEntityFromFields();
        if (processClientSideValidations(entity)) {
            if (event.getSource() == update) {
                updateButtonClicked();
                disableSubmitButtons();
            }
        }
    }

    protected void configureCancel() {
        if (enableCancel()) {
//            entityCaptionPanel.addStyleName("y-gwt-CancelEntityCaptionPanel");
//            entityFieldsPanel.addStyleName("y-gwt-CancelEntityDisplayWidget");
//            basePanel.addStyleName("y-gwt-CancelBasePanel");
            entityActionsPanel.add(cancel);
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onCancelClicked();
                }
            });
        }
    }

    protected boolean enableCancel() {
        return false;
    }

    protected void onCancelClicked() {
    }

    public JSONObject getPopulatedEntity() {
        return populateEntityFromFields();
    }

    @Override
    protected void enterKeyPressed() {
        onClick(null);
    }

    @Override
    protected void enableSubmitButtons() {
        update.setEnabled(true);
    }

    @Override
    protected void disableSubmitButtons() {
        update.setEnabled(false);
    }

    protected void setButtonText(String key) {
        update.setText(Utils.getKeyValue(key, constants));
    }

    protected abstract JSONObject populateEntityFromFields();

    protected abstract void updateButtonClicked();

    public abstract void populateFieldsFromEntity(JSONObject entity);

    /**
     * override this method to handle any client side validation before calling
     * the server
     */
    protected boolean processClientSideValidations(JSONObject entity) {
        return true;
    }

    protected abstract void postUpdateSuccess(String result);

    public void loadEntity(String entityId) {
        throw new UnsupportedOperationException();
    }

    public void formatTextAreaFields() {
        for (Map.Entry entry : fields.entrySet()) {
            if (entry.getValue() instanceof TextAreaField) {
                TextAreaField textAreaField = (TextAreaField) entry.getValue();
                textAreaField.getTextbox().setCharacterWidth(75);
                textAreaField.getTextbox().setVisibleLines(6);
            }
        }
    }
}
