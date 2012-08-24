/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.yalamanchili.gwt.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author ayalamanchili
 */
public interface ChiliImages extends ClientBundle {

    public static final ChiliImages INSTANCE = GWT.create(ChiliImages.class);

    @ClientBundle.Source("images/date-picker-icon.gif")
    ImageResource datePickerIcon();

    @ClientBundle.Source("images/logo.gif")
    ImageResource logo();

    @ClientBundle.Source("images/info-icon.png")
    ImageResource fieldInfoIcon();
}
