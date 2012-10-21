/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.widgets;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author ayalamanchili
 */
public class ClickableImage extends Image {

    public ClickableImage(String name, ImageResource imageResource) {
        super(imageResource);
        super.setTitle(name);
        this.addStyleName("y-gwt-ClickableImage");
    }
}
