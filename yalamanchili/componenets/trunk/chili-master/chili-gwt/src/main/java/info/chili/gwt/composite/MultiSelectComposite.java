/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.composite;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import info.chili.gwt.utils.JSONUtils;
import info.chili.gwt.widgets.ResponseStatusWidget;
import java.util.List;

/**
 *
 * @author ayalamanchili
 */
//TODO extend base field
public abstract class MultiSelectComposite extends Composite {

    protected String parentId;
    protected String name;
    protected FlowPanel panel = new FlowPanel();
    protected MultiSelectBox multiSelectBox = new MultiSelectBox() {
        @Override
        public void itemsSelected(List<String> selectedIds) {
            MultiSelectComposite.this.itemsSelected(selectedIds);
        }

        @Override
        public void itemsUnselected(List<String> selectedIds) {
            MultiSelectComposite.this.itemsUnselected(selectedIds);
        }
    };
//TODO extend base field

    public MultiSelectComposite(ConstantsWithLookup constants, String name, String parentId, Boolean readOnly, Boolean isRequired) {
        this.name = name;
        this.parentId = parentId;
        initWidget(panel);
        panel.add(multiSelectBox);
        multiSelectBox.setReadOnly(readOnly);
        multiSelectBox.setConstants(constants);
        loadData();
    }

    protected abstract void itemsSelected(List<String> selectedIds);

    protected abstract void itemsUnselected(List<String> selectedIds);

    protected abstract void loadData();

    protected abstract String getMultiSelectUrl();

    public MultiSelectBox getMultiSelectBox() {
        return multiSelectBox;
    }

    protected void handleErrorResponse(Throwable err) {
        //TODO enhance to show generic error messages
        if (!err.getMessage().isEmpty() && err.getMessage().contains("Error")) {
            try {
                JSONValue errors = JSONParser.parseLenient(err.getMessage());
                processValidationErrors(errors);
            } catch (Exception e) {
                new ResponseStatusWidget().show("Call Failed");
            }
        } else {
            new ResponseStatusWidget().show("Call Failed");
        }
    }

    protected void processValidationErrors(JSONValue errorsObj) {
        JSONArray errorsArray = JSONUtils.toJSONArray(errorsObj.isObject().get("Error"));
        String genericErrorMessage = null;
        for (int i = 0; i < errorsArray.size(); i++) {
            JSONObject err = (JSONObject) errorsArray.get(i);
            genericErrorMessage = new String();
            genericErrorMessage = genericErrorMessage.concat("Error:");
            genericErrorMessage = genericErrorMessage.concat(err.get("source").isString().stringValue() + ":" + err.get("description").isString().stringValue());
        }
        if (genericErrorMessage != null) {
            new ResponseStatusWidget().show(genericErrorMessage);
        }
    }
}
