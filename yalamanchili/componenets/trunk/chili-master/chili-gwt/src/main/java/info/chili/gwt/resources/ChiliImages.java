/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author ayalamanchili
 */
public interface ChiliImages extends ClientBundle {

    public static final ChiliImages INSTANCE = GWT.create(ChiliImages.class);

    @ClientBundle.Source("images/date_picker_icon_16_16.png")
    ImageResource datePickerIcon();

    @ClientBundle.Source("images/logo.png")
    ImageResource logo();

    @ClientBundle.Source("images/info-icon.png")
    ImageResource fieldInfoIcon();
}
