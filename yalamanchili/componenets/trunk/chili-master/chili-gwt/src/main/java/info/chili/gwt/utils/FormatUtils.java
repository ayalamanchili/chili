package info.chili.gwt.utils;

import com.google.gwt.i18n.client.NumberFormat;
import java.math.BigDecimal;

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

    public static String formarCurrency(String amount) {
        BigDecimal amt;
        try {
            amt = new BigDecimal(amount);
        } catch (Exception e) {
            return "";
        }
        return formatCurrency(amt);
    }

    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) {
            return "";
        }
        NumberFormat fmt = NumberFormat.getCurrencyFormat();
        return fmt.format(amount);
    }

}
