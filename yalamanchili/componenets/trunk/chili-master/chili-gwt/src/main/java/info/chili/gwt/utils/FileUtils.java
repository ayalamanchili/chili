/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.utils;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import info.chili.gwt.config.ChiliClientConfig;

/**
 *
 * @author anuyalamanchili
 */
public class FileUtils {

    public static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }

    public static boolean isImage(String filename) {
        String[] validImagesExtesions = {"JPG", "JPEG", "GIF", "PNG"};
        for (String ext : validImagesExtesions) {
            if (ext.equalsIgnoreCase(getFileExtension(filename))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDocument(String filename) {
        String[] validImagesExtesions = {"PDF", "DOC", "DOCX", "TXT", "RTF"};
        for (String ext : validImagesExtesions) {
            if (ext.equalsIgnoreCase(getFileExtension(filename))) {
                return true;
            }
        }
        return false;
    }

    public static String getFileNameFromUrl(String url) {
        int beginIndex = url.indexOf("entityId_");
        int endIndex = url.lastIndexOf("&");
        if (beginIndex > 0 && endIndex > 0) {
            return url.substring(beginIndex + "entityId_".length(), endIndex);
        }
        //means file not valid or present
        return "file";
    }

    public static void openFile(JSONObject entity, String url) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
        builder.setHeader("Content-Type", "application/json");

        try {
            builder.sendRequest(entity.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(com.google.gwt.http.client.Request request, com.google.gwt.http.client.Response response) {
                    if (200 == response.getStatusCode()) {
                        Window.open(ChiliClientConfig.instance().getFileDownloadUrl() + response.getText(), "_blank", "");
                    } else {
                        Window.alert("error downloading file");
                    }
                }

                @Override
                public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
                    Window.alert(exception.getLocalizedMessage());
                }
            });
        } catch (RequestException e) {
            Window.alert(e.getLocalizedMessage());
        }
    }
}
