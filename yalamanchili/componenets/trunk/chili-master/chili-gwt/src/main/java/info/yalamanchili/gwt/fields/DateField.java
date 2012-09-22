package info.yalamanchili.gwt.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import info.yalamanchili.gwt.composite.BaseField;
import info.yalamanchili.gwt.utils.Utils;

import java.util.Date;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

import com.smartgwt.client.widgets.DateChooser;
import com.smartgwt.client.widgets.events.DataChangedEvent;
import com.smartgwt.client.widgets.events.DataChangedHandler;
import info.yalamanchili.gwt.date.DateUtils;
import info.yalamanchili.gwt.resources.ChiliImages;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class DateField.
 */
public class DateField extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler, ClickHandler {

    private static Logger logger = Logger.getLogger(DateField.class.getName());
    final TextBox dateField = new TextBox();
    final DateChooser datePicker = new DateChooser();
    Image datePickerIcon = new Image();
    PopupPanel popupPanel = new PopupPanel(true);
    protected Date date = null;

    public DateChooser getDatePicker() {
        return datePicker;
    }

    @UiConstructor
    public DateField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
        configureAddMainWidget();
        setReadOnly(readOnly);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date != null) {
            dateField.setText(Utils.getShortDate(date));
            datePicker.setData(date);
        }
    }

    public void setValue(String dateString) {
        //need to set the datepicker date to support udpate
        datePicker.setData(DateUtils.toDate(dateString));
        String formatedDate = DateUtils.getFormatedDate(dateString, DateTimeFormat.PredefinedFormat.DATE_MEDIUM);
        dateField.setText(formatedDate);
    }

    public void setReadOnly(Boolean readOnly) {
        dateField.setReadOnly(readOnly);
        if (readOnly) {
            dateField.setEnabled(false);
        }
    }

    @Override
    protected void configureAddMainWidget() {
        dateField.ensureDebugId(className + "_" + attributeName + "_TB");
        dateField.addKeyPressHandler(this);
        datePicker.addDataChangedHandler(new DataChangedHandler() {
            @Override
            public void onDataChanged(DataChangedEvent event) {
                date = datePicker.getData();
                String dateString = DateTimeFormat.getFormat("d MMMM yyyy").format(date);
                dateField.setText(dateString);
            }
        });
        fieldPanel.insert(dateField, 0);
        datePickerIcon.addClickHandler(this);
        datePickerIcon.setResource(ChiliImages.INSTANCE.datePickerIcon());
        fieldPanel.insert(datePickerIcon, 1);
        //popup for date picker
        popupPanel.add(datePicker);
    }

    public void onKeyPress(KeyPressEvent event) {
    }

    public void onKeyUp(KeyUpEvent event) {
        // TODO Auto-generated method stub
    }

    public void onKeyDown(KeyDownEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void validate() {
       clearMessage();
    }

    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource().equals(datePickerIcon)) {
            popupPanel.setPopupPosition(datePickerIcon.getAbsoluteLeft() + datePickerIcon.getOffsetWidth(),
                    datePickerIcon.getAbsoluteTop() + datePickerIcon.getOffsetHeight());
            popupPanel.show();
        }
    }
}
