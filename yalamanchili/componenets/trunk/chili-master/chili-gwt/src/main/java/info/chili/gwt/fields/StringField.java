package info.chili.gwt.fields;

import info.chili.gwt.composite.BaseFieldWithTextBox;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.uibinder.client.UiConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class StringField.
 *
 * @author ayalamanchili
 */
public class StringField extends BaseFieldWithTextBox {

    Logger log = Logger.getLogger(StringField.class.getName());

    @UiConstructor
    public StringField(ConstantsWithLookup constants, String attributeName, String className, Boolean readOnly, Boolean isRequired) {
        super(constants, attributeName, className, readOnly, isRequired);
    }

    // TODO can these be moved to a top level???
    @Override
    public void onKeyPress(KeyPressEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onKeyUp(KeyUpEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onKeyDown(KeyDownEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void validate() {
        //TODO remove all for clear message?
        clearMessage();
        // TODO implement this
    }
}
