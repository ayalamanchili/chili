/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.utils;

import com.google.gwt.user.client.Window;
import java.util.logging.Logger;

/**
 *
 * @author ayalamanchili
 */
public class BrowserUtils {

    private static Logger logger = Logger.getLogger(BrowserUtils.class.getName());

    /**
     * Gets the name of the used browser.
     *
     */
    public static native String getBrowserName() /*-{
     return $wnd.navigator.userAgent.toLowerCase();
     }-*/;

    /**
     * Returns true if the current browser is Chrome.
     */
    public static boolean isChromeBrowser() {
        return getBrowserName().toLowerCase().contains("chrome");
    }

    /**
     * Returns true if the current browser is Firefox.
     */
    public static boolean isFirefoxBrowser() {
        return getBrowserName().toLowerCase().contains("firefox");
    }

    /**
     * Returns true if the current browser is IE (Internet Explorer).
     */
    public static boolean isIEBrowser() {
        Window.alert("browser:" + getBrowserName());
        return getBrowserName().toLowerCase().contains("msie");
    }
}
