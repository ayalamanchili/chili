/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.config;

import com.google.gwt.i18n.client.TimeZone;

/**
 *
 * @author ayalamanchili
 */
public interface ClientConfig {

    public String getFileUploadUrl();

    public String getFileDownloadUrl();

    public TimeZone getTimeZone();

}
