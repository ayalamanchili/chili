/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

/**
 *
 * @author ayalamanchili
 */
public class StringUtils {

    public static String getStringCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }
}
