/**
 * System Soft Technolgies Copyright (C) 2013 ayalamanchili@sstech.mobi
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.composite;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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
}
