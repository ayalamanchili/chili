package info.chili.gwt.fields;

import info.chili.gwt.utils.Alignment;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
//TODO depreciate this and merge with DropDownField 
public class ListBoxField extends Composite {

    /**
     * The panel.
     */
    CellPanel panel;
    /**
     * The label.
     */
    Label label = new Label();
    /**
     * The listbox.
     */
    ListBox listbox = new ListBox();

    @UiConstructor
    public ListBoxField(String labelName) {
        panel = new VerticalPanel();
        initWidget(panel);
        label.setText(labelName);
        label.ensureDebugId(labelName + "_LB");
        configure();
        addWidgets();
    }

    public ListBoxField(String labelName, Alignment alignment) {
        switch (alignment) {
            case HORIZONTAL:
                panel = new HorizontalPanel();
                break;
            case VERTICAL:
                panel = new VerticalPanel();
                break;
        }
        initWidget(panel);
        label.setText(labelName);
        configure();
        addWidgets();
    }

    protected void configure() {
    }

    protected void addWidgets() {
        panel.add(label);
        panel.add(listbox);
    }

    public Long getValue() {
        return new Long(listbox.getValue(listbox.getSelectedIndex()));
    }

    public void addValue(Long value, String item) {
        listbox.addItem(item, value.toString());
    }

    public void addChangeHandler(ChangeHandler changeHandler) {
        listbox.addChangeHandler(changeHandler);
    }

    public Label getLabel() {
        return label;
    }

    public ListBox getListBox() {
        return listbox;
    }
}
