/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.yalamanchili.gwt.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import info.yalamanchili.gwt.utils.Utils;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ayalamanchili
 */
public class SuggestBox extends Composite {

    /**
     * The panel.
     */
    FlowPanel panel = new FlowPanel();
    /**
     * The label.
     */
    Label label = new Label();
    /**
     * The data.
     */
    MultiWordSuggestOracle data = new MultiWordSuggestOracle();
    /**
     * The box.
     */
    com.google.gwt.user.client.ui.SuggestBox box = new com.google.gwt.user.client.ui.SuggestBox(data);

    /**
     * Instantiates a new aL suggest box.
     *
     * @param name the name
     */
    public SuggestBox(String name) {
        initWidget(panel);
        label.setText(name);
        panel.add(label);
        box.setTitle(name);
        panel.add(box);
    }
    
    public SuggestBox(String name, List<String> inputs) {
        initWidget(panel);
        label.setText(name);
        panel.add(label);
        box.setTitle(name);
        panel.add(box);
        loadData(inputs);
    }

    /**
     * Load data.
     *
     * @param inputs the inputs
     */
    public void loadData(Collection<String> inputs) {
        data.addAll(inputs);
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getValue() {
        return box.getText();
    }

}
