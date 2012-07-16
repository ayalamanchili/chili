package info.yalamanchili.gwt.fields;

import info.yalamanchili.gwt.composite.BaseField;
import info.yalamanchili.gwt.utils.Utils;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class DateField.
 */
public class DateField extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {

    final TextBox dateField = new TextBox();
    final Button dateButton = new Button("Date");
    final DatePicker datePicker = new DatePicker();

    public DatePicker getDatePicker() {
        return datePicker;
    }

    @UiConstructor
    public DateField(String labelName, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(labelName, attributeName, className, readOnly, isRequired);
        configureAddMainWidget();
        setReadOnly(readOnly);
    }

    public Date getDate() {
        Date dt = null;
        if (datePicker.getValue() == null) {
            return null;
        }
        try {
            dt = datePicker.getValue();
        } catch (Exception e) {
            Window.alert("enter valid date");
        }
        return dt;
    }

    public void setDate(Date date) {
        if (date != null) {
            dateField.setText(Utils.getShortDate(date));
            datePicker.setValue(date);
        }
    }

    public void setValue(String date) {
        dateField.setText(date);
    }

    public void setReadOnly(Boolean readOnly) {
        dateField.setReadOnly(readOnly);
    }

    @Override
    protected void configureAddMainWidget() {
        dateField.ensureDebugId(className + "_" + attributeName + "_TB");
        dateButton.ensureDebugId(className + "_" + attributeName + "_B");
        datePicker.addValueChangeHandler(new ValueChangeHandler() {
            public void onValueChange(ValueChangeEvent event) {
                Date date = (Date) event.getValue();
                String dateString = DateTimeFormat.getFormat("d MMMM yyyy").format(date);
                dateField.setText(dateString);
            }
        });

        ClickHandler dateButtonHandler = new ClickHandler() {
            public void onClick(ClickEvent event) {
                PopupPanel popupPanel = new PopupPanel(true);
                popupPanel.add(datePicker);
                popupPanel.setPopupPosition(dateButton.getAbsoluteLeft() + dateButton.getOffsetWidth(),
                        dateButton.getAbsoluteTop() + dateButton.getOffsetHeight());
                popupPanel.show();
            }
        };
        dateButton.addClickHandler(dateButtonHandler);
        fieldPanel.insert(dateField, 0);
        fieldPanel.insert(dateButton, 1);
        // fieldPanel.insert(datePicker, 2);

    }

    public void onKeyPress(KeyPressEvent event) {
        // TODO Auto-generated method stub
    }

    public void onKeyUp(KeyUpEvent event) {
        // TODO Auto-generated method stub
    }

    public void onKeyDown(KeyDownEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void validate() {
        // TODO Auto-generated method stub
    }
}
