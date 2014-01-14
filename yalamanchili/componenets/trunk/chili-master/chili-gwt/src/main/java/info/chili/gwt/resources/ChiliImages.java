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

    @ClientBundle.Source("images/field-error-icon.png")
    ImageResource fieldErrorIcon();

    @Source("images/default-image.gif")
    @ImageResource.ImageOptions(height = 50, width = 50)
    ImageResource defaultImage_50_50();

    @Source("images/default-image.gif")
    ImageResource defaultImage();
    /*
     * View Icons
     */

    @Source("images/quick_view_icon_16_16.png")
    ImageResource quickViewIcon_16_16();

    @Source("images/info_icon_16_16.png")
    ImageResource infoIcon_16_16();

    @Source("images/view_icon_16_16.png")
    ImageResource viewIcon_16_16();

    /*
     * Update Edit Icons
     */
    @Source("images/update_icon_16_16.png")
    ImageResource updateIcon_16_16();
    /*
     * Delete Icons
     */

    @Source("images/delete_icon_16_16.png")
    ImageResource deleteIcon_16_16();
}
