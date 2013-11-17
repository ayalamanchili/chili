package info.chili.gwt.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anuyalamanchili
 */
public class FormatUtils {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return "";
        } else if (phoneNumber.length() != 10) {
            return phoneNumber;
        }
        return phoneNumber.replaceFirst("^(\\d{3})(\\d{3})(\\d{4})$", "$1-$2-$3");
    }
}
