/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.widgets;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import info.chili.gwt.composite.BaseField;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yalamanchili
 */
public class SuggestBox extends BaseField implements KeyPressHandler, KeyUpHandler, KeyDownHandler {
    
    MultiWordSuggestOracle data = new MultiWordSuggestOracle();
    com.google.gwt.user.client.ui.SuggestBox box = new com.google.gwt.user.client.ui.SuggestBox(data);
    protected Map<String, String> map;
    
    public SuggestBox(ConstantsWithLookup constants,
            String attributeName, String className, Boolean readOnly,
            Boolean required) {
        super(constants, attributeName, className, readOnly, required);
        configureAddMainWidget();
        box.ensureDebugId(className + "_" + attributeName + "_TB");
        setReadOnly(readOnly);
    }
    
    protected void configureAddMainWidget() {
        box.addStyleName("tfTextBox");
        fieldPanel.insert(box, 0);
        addListeners();
    }
    
    protected void addListeners() {
        box.addKeyPressHandler(this);
        box.addKeyUpHandler(this);
    }
    
    public void setReadOnly(Boolean readlOnly) {
        if (readOnly) {
            box.setEnabled(false);
        }
    }
    
    public void validate() {
    }
    
    public void setValue(String value) {
        box.setText(value);
    }
    
    public void loadData(Collection<String> inputs) {
        data.addAll(inputs);
    }
    
    public void loadData(Map<String, String> inputs) {
        if (this.map == null) {
            map = new HashMap<String, String>();
        }
        this.map = inputs;
        data.addAll(inputs.values());
    }
    
    public String getValue() {
        if (box.getText() != null) {
            return box.getText();
        } else {
            return null;
        }
    }
    
    public void clearText() {
        box.setText("");
    }

    public String getKey() {
        for (String key : map.keySet()) {
            if (map.get(key).equalsIgnoreCase(getValue())) {
                return key;
            }
        }
        return null;
    }
    
    public com.google.gwt.user.client.ui.SuggestBox getSuggestBox() {
        return box;
    }
    
    @Override
    public void onKeyPress(KeyPressEvent event) {
    }
    
    @Override
    public void onKeyUp(KeyUpEvent event) {
    }
    
    @Override
    public void onKeyDown(KeyDownEvent event) {
    }
}
