/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.utils;

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
}
