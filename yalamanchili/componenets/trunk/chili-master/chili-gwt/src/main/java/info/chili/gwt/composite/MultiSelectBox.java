/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.composite;

import info.chili.gwt.widgets.CHorizontalPanel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import info.chili.gwt.fields.ListBoxField;
import info.chili.gwt.utils.Alignment;
import info.chili.gwt.utils.JSONUtils;
import info.chili.gwt.utils.Utils;
//TODO extend base field

public abstract class MultiSelectBox extends ALComposite implements ClickHandler {

    private static Logger logger = Logger.getLogger(MultiSelectBox.class.getName());
    protected ConstantsWithLookup constants;
    protected String className;
    CaptionPanel captionPanel = new CaptionPanel();
    CHorizontalPanel panel = new CHorizontalPanel();
    ListBoxField availableListBox = new ListBoxField("Available", true, Alignment.VERTICAL);
    ListBoxField selectedListBox = new ListBoxField("Selected", true, Alignment.VERTICAL);
    public Button selectButton = new Button("  Add  ");
    public Button unselectButton = new Button("  Remove  ");
    Map<String, String> available;
    Set<String> selected;
    List<String> tempSelectedItems = new ArrayList<String>();

    /* holds the selected varialbes in temp */
    public MultiSelectBox() {
        init(captionPanel);
    }

    public void popuplateWidget(String title, MultiSelectObj obj) {
        captionPanel.setCaptionHTML(title);
        this.available = Utils.sortByComparator(obj.getAvailable());
        this.selected = obj.getSelected();
        int i = 0;
        for (String id : available.keySet()) {
            if (selected.contains(id)) {
                selectedListBox.getListBox().insertItem(Utils.getAttributeLabel(available.get(id), className, constants), id.toString(),
                        i);
            } else {
                availableListBox.getListBox().insertItem(Utils.getAttributeLabel(available.get(id), className, constants), id.toString(),
                        i);
            }
            i++;
        }
    }

    @Override
    public void addWidgets() {
        panel.add(availableListBox);
        panel.add(selectButton);
        panel.add(unselectButton);
        panel.add(selectedListBox);
        captionPanel.setContentWidget(panel);
    }

    @Override
    public void addListeners() {
        selectButton.addClickHandler(this);
        unselectButton.addClickHandler(this);
    }

    @Override
    public void configure() {
        availableListBox.getListBox().setVisibleItemCount(10);
        availableListBox.getListBox().setMultipleSelect(true);
        selectedListBox.getListBox().setVisibleItemCount(10);
        availableListBox.getLabel().addStyleName("y-gwt-multipleSelectWidget-availabelLabel");
        selectedListBox.getLabel().addStyleName("y-gwt-multipleSelectWidget-selectedLabel");
        panel.addStyleName("y-gwt-multipleSelectWidget");
        availableListBox
                .addStyleName("y-gwt-multipleSelectWidget-availableListBox");
        selectedListBox
                .addStyleName("y-gwt-multipleSelectWidget-selectedListBox");
        selectButton.addStyleName("y-gwt-multipleSelectWidget-selectButton");
        unselectButton
                .addStyleName("y-gwt-multipleSelectWidget-unselectButton");
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource().equals(selectButton)) {
            tempSelectedItems = new ArrayList<String>();
            for (String id : getSelectedIds(availableListBox)) {
                selectedListBox.getListBox().insertItem(Utils.getAttributeLabel(available.get(id), className, constants), id.toString(),
                        new Integer(id));
                removeSelectedItems(availableListBox);
                tempSelectedItems.add(id);
            }
            itemsSelected(getSelectedIds());
        }
        if (event.getSource().equals(unselectButton)) {
            tempSelectedItems = new ArrayList<String>();
            for (String id : getSelectedIds(selectedListBox)) {
                availableListBox.getListBox().insertItem(Utils.getAttributeLabel(available.get(id), className, constants), id.toString(),
                        new Integer(id));
                removeSelectedItems(selectedListBox);
                tempSelectedItems.add(id);
            }
            itemsUnselected(getSelectedIds());
        }
    }

    public abstract void itemsSelected(List<String> selectedIds);

    public abstract void itemsUnselected(List<String> selectedIds);

    /*
     * Returns only the temp selected items that are moved. (only the ones moved from available to selected boxF)
     */
    public List<String> getSelectedIds() {
        return tempSelectedItems;
    }

    /*
     * Returns the all the selected ids in the selected box
     */
    public List<String> getAllSelectedIds() {
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < selectedListBox.getListBox().getItemCount(); i++) {
            ids.add(selectedListBox.getListBox().getValue(i));
        }
        return ids;
    }

    private Set<String> getSelectedIds(ListBoxField listBox) {
        Set<String> ids = new HashSet<String>();
        for (int i = 0; i < listBox.getListBox().getItemCount(); i++) {
            if (listBox.getListBox().isItemSelected(i)) {
                ids.add(listBox.getListBox().getValue(i));
            }
        }
        return ids;
    }

    protected void removeSelectedItems(ListBoxField listBox) {
        for (int i = 0; i < listBox.getListBox().getItemCount(); i++) {
            if (listBox.getListBox().isItemSelected(i)) {
                listBox.getListBox().removeItem(i);
            }
        }
    }

    public void setReadOnly(boolean readOnly) {
        availableListBox.getListBox().setEnabled(!readOnly);
        selectedListBox.getListBox().setEnabled(!readOnly);
        selectButton.setEnabled(!readOnly);
        unselectButton.setEnabled(!readOnly);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ConstantsWithLookup getConstants() {
        return constants;
    }

    public void setConstants(ConstantsWithLookup constants) {
        this.constants = constants;
    }

    public static MultiSelectObj getMultiSelectBox(String response) {
        MultiSelectObj obj = new MultiSelectObj();
        JSONObject multiSelectObj = (JSONObject) JSONParser.parseLenient(response);
        JSONArray availableArray = JSONUtils.toJSONArray(multiSelectObj.get("available").isObject().get("entry"));
        for (int i = 0; i < availableArray.size(); i++) {
            JSONObject availableEntry = (JSONObject) availableArray.get(i);
            obj.addAvailable(JSONUtils.toString(availableEntry, "key"), JSONUtils.toString(availableEntry, "value"));
        }
        JSONArray selectedArray = JSONUtils.toJSONArray(multiSelectObj.get("selected"));
        for (int i = 0; i < selectedArray.size(); i++) {
            obj.addSelected(selectedArray.get(i).isString().stringValue());
        }
        return obj;
    }
}
