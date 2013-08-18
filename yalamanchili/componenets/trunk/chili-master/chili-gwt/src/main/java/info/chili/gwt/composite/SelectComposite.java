/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
package info.chili.gwt.composite;

import info.chili.gwt.listeners.GenericListener;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.ListBox;
import info.chili.gwt.composite.BaseField;
import info.chili.gwt.utils.JSONUtils;
import info.chili.gwt.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class SelectComposite extends BaseField implements ClickHandler, ChangeHandler {

    private static Logger logger = Logger.getLogger(SelectComposite.class.getName());
    protected List<GenericListener> listeners = new ArrayList<GenericListener>();
    protected ListBox listBox = new ListBox();
    protected Map<Integer, String> values;
    protected Map<Integer, JSONObject> entityMap = new HashMap<Integer, JSONObject>();
    protected JSONObject selectedObject = null;

    public SelectComposite(ConstantsWithLookup constants, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, info.chili.gwt.utils.Utils.getStringCamelCase(className), className, readOnly, isRequired);
        configureAddMainWidget();
        setReadOnly(readOnly);
        fetchDropDownData();
    }

    @Override
    protected void configureAddMainWidget() {
        initListBox();
        listBox.addChangeHandler(this);
        listBox.ensureDebugId(className + "_" + attributeName + "_LB");
        fieldPanel.insert(listBox, 0);
        listBox.addBlurHandler(this);
    }

    public void setReadOnly(Boolean readOnly) {
        listBox.setEnabled(!readOnly);
    }

    protected abstract void fetchDropDownData();

    protected void processData(String listString) {
        initListBox();
        if (listString != null && !listString.isEmpty()) {
            JSONObject listObject = JSONParser.parseLenient(listString).isObject();
            if (listObject != null) {
                if (listObject.get("entry") != null) {
                    JSONArray entities = JSONUtils.toJSONArray(listObject.get("entry"));
                    values = populateValues(entities);
                    for (int i = 1; i <= entities.size(); i++) {
                        JSONObject entity = (JSONObject) entities.get(i - 1);
                        Integer key = Integer.valueOf(JSONUtils.toString(entity, "id"));
                        entityMap.put(key, entity);
                    }
                    // TODO see option to populate the drop down here by taking in the
                    // attr names
                    populateDropDown(values);
                }
            }
        }
    }

    protected Map<Integer, String> populateValues(JSONArray entities) {
        Map<Integer, String> values = new HashMap<Integer, String>();
        for (int i = 1; i <= entities.size(); i++) {
            JSONObject entity = (JSONObject) entities.get(i - 1);
            Integer key = Integer.valueOf(JSONUtils.toString(entity, "id"));
            String value = JSONUtils.toString(entity, "value");
            values.put(key, value);
        }
        return values;
    }

    public void setSelectedValue(JSONObject entity) {
        this.selectedObject = entity;
        if (getSelectedObject() == null) {
            populateSelectedValue();
        }
    }

    public void addListner(GenericListener listner) {
        this.listeners.add(listner);
    }

    public void populateSelectedValue() {
        for (int i = 0; i < listBox.getItemCount(); i++) {
            //TODO make primary key static
            if (listBox.getItemText(i) != null && listBox.getValue(i).equalsIgnoreCase(JSONUtils.toString(selectedObject, "id"))) {
                listBox.setSelectedIndex(i);
            }
        }
    }

    protected void populateDropDown(Map<Integer, String> values) {
        //TODO avoid this sorting on client side
        values=Utils.sortByComparator(values);
        int i = 1;
        for (Integer key : values.keySet()) {
            listBox.insertItem(values.get(key), key.toString(), i);
            i++;
        }
        if (selectedObject != null) {
            populateSelectedValue();
            //To support update panel drop downs
            onChange(null);
        }
    }

    protected void initListBox() {
        listBox.clear();
        listBox.insertItem("SELECT", 0);
    }

    @Override
    public void onChange(ChangeEvent arg0) {
        for (GenericListener listner : listeners) {
            listner.fireEvent();
        }
    }

    @Override
    public void onClick(ClickEvent arg0) {
        // TODO Auto-generated method stub
    }

    public ListBox getListBox() {
        return listBox;
    }

    public JSONObject getSelectedObject() {
        if (listBox.getSelectedIndex() > 0) {
            Integer entityId = Integer.valueOf(listBox.getValue(listBox.getSelectedIndex()));
            return entityMap.get(entityId);
        } else {
            return null;
        }
    }

    public String getSelectedObjectId() {
        return JSONUtils.toString(getSelectedObject(), "id");
    }

    protected abstract String getDropDownURL(Integer start, Integer limit, String... columns);

    protected String generateDropdownUrl(String url, Integer start, Integer limit, String... columns) {
        url = url + "/" + start.toString() + "/" + limit.toString() + "?";
        for (String column : columns) {
            url = url.concat("column=" + column + "&");
        }
        return url;
    }
}
