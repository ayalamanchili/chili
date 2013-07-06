package info.chili.gwt.utils;

import java.util.Date;
import java.util.MissingResourceException;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

    public static String getShortDate(Date date) {
        if (date == null) {
            return "";
        }
        return DateTimeFormat.getShortDateFormat().format(date);
    }

    public static String getClassSimpleName(String name) {
        return (name.substring(name.lastIndexOf(".") + 1));
    }

    // TODO rename to toString
    public static String entityToString(Object entity) {
        if (entity == null) {
            return "";
        } else {
            return entity.toString();
        }
    }

    /**
     * used to get UI display value from constants for the give attribute in a
     * class
     */
    public static String getAttributeLabel(String attribute, String classCanonicalName, ConstantsWithLookup constants) {
        if (constants == null) {
            return attribute;
        }
        String key = classCanonicalName + "_" + attribute;
        key = key.replace(".", "_");
        try {
            return constants.getString(key);
        } catch (MissingResourceException e) {
            return attribute;
        }
    }

    public static String getMoreInfoLabel(String attribute, String classCanonicalName, ConstantsWithLookup constants) {
        if (constants == null) {
            return attribute;
        }
        String key = classCanonicalName + "_" + attribute + "_info";
        try {
            return constants.getString(key);
        } catch (MissingResourceException e) {
            return attribute;
        }
    }

    /**
     * used to get UI display value from constants for the key
     */
    public static String getKeyValue(String id, ConstantsWithLookup constants) {
        if (constants == null) {
            return id;
        }
        String value = "";
        try {
            value = constants.getString(id);
        } catch (MissingResourceException e) {
            value = id;
        }
        return value;
    }

    public static String getExceptionInfo(Throwable e) {
        String result = "";
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        result += e.toString() + "\n";
        for (int i = 0; i < stackTraceElements.length; i++) {
            result += "    at " + stackTraceElements[i] + "\n";
        }
        e = e.getCause();
        if (e != null) {
            result += "Caused by: ";
        }
        return result;
    }

    public static String getStringCamelCase(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    public static String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("_"));
    }

    public static Map sortByComparator(Map unsortMap) {

        List list = new LinkedList(unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue().toString().toLowerCase())
                        .compareTo(((Map.Entry) (o2)).getValue().toString().toLowerCase());
            }
        });

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
