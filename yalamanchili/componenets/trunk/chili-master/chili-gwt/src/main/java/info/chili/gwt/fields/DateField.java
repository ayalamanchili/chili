package info.chili.gwt.fields;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import info.chili.gwt.composite.BaseField;

import java.util.Date;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;

import info.chili.gwt.date.DateUtils;
import info.chili.gwt.utils.Alignment;
import java.util.logging.Logger;
import org.zenika.widget.client.datePicker.DatePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class DateField.
 */
public class DateField extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler, ClickHandler {

    private static Logger logger = Logger.getLogger(DateField.class.getName());
    final DatePicker datePicker = new DatePicker();

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public DateField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired, Alignment alignment) {
        super(constants, attributeName, className, readOnly, isRequired, alignment);
        configureAddMainWidget();
        setReadOnly(readOnly);
        datePicker.addBlurHandler(this);
    }

    @UiConstructor
    @Deprecated
    public DateField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
        configureAddMainWidget();
        setReadOnly(readOnly);
        datePicker.addBlurHandler(this);
    }

    public Date getDate() {
        return getDateHours12(datePicker.getSelectedDate());
    }

    public void setDate(Date date) {
        if (date != null) {
            datePicker.setSelectedDate(getDateHours12(date));
        }
    }
    //BUG RELATED to 
    //http://stackoverflow.com/questions/13266876/java-date-saved-as-the-day-before
    //http://yalamanchili.info:8080/jira/browse/SSTO-605

    protected Date getDateHours12(Date date) {
        if (date == null) {
            return null;
        }
        final long hours12 = 12L * 60L * 60L * 1000L;
        return new Date(date.getTime() + hours12);
    }

    public void setValue(String dateString) {
        //need to set the datepicker date to support udpate
        datePicker.setSelectedDate(DateUtils.toDateTZ(dateString));
    }

    @Override
    public void setReadOnly(Boolean readOnly) {
        datePicker.setReadOnly(readOnly);
        datePicker.setEnabled(!readOnly);
    }

    @Override
    protected void configureAddMainWidget() {
        datePicker.ensureDebugId(className + "_" + attributeName + "_TB");
        fieldPanel.insert(datePicker, 0);
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
    }

    @Override
    public void focus(boolean focus) {
        datePicker.setFocus(true);
    }
}
