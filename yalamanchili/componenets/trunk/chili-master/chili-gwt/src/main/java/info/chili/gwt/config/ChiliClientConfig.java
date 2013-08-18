/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.config;

import com.google.gwt.core.client.GWT;

/**
 *
 * @author anuyalamanchili
 */
public class ChiliClientConfig {

    public static ClientConfig config = GWT.create(ClientConfig.class);

    public static ClientConfig instance() {
        if (config == null) {
            config = GWT.create(ClientConfig.class);
        }
        return config;
    }
}
