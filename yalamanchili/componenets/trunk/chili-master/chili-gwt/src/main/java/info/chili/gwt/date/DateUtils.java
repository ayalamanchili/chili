package info.chili.gwt.date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import info.chili.gwt.config.ChiliClientConfig;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";
    private static final String DEFAULT_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ssZZZ";

    public static String toDateString(Date date) {
        DateTimeFormat formatter = DateTimeFormat.getFormat(DEFAULT_FORMAT);
        if (ChiliClientConfig.instance().getTimeZone() != null) {
            return formatter.format(date, ChiliClientConfig.instance().getTimeZone());
        } else {
            return formatter.format(date);
        }
    }

    public static Date toDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        DateTimeFormat formatter;
        if (dateString.length() == 29) {
            formatter = DateTimeFormat.getFormat(DEFAULT_FORMAT);
        } else if (dateString.length() == 25) {
            formatter = DateTimeFormat.getFormat(DEFAULT_FORMAT2);
        } else {
            throw new RuntimeException(dateString + " : is not supported date string format vaid ones are 1.yyyy-MM-dd'T'HH:mm:ss.SSSZZZ and 2.yyyy-MM-dd'T'HH:mm:ssZZZ:");
        }
        return formatter.parse(dateString);
    }

    public static String getFormatedDate(String dateString, PredefinedFormat format) {
        if (dateString == null || "".equals(dateString)) {
            return null;
        }
        if (ChiliClientConfig.instance().getTimeZone() != null) {
            return DateTimeFormat.getFormat(format).format(toDate(dateString), ChiliClientConfig.instance().getTimeZone());
        } else {
            return DateTimeFormat.getFormat(format).format(toDate(dateString));
        }
    }
}
