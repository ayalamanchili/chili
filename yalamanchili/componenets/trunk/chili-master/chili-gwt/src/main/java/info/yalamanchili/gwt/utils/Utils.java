package info.yalamanchili.gwt.utils;

import java.util.Date;
import java.util.MissingResourceException;

import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.i18n.client.DateTimeFormat;

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
		if (constants == null)
			return attribute;
		String key = classCanonicalName + "_" + attribute;
		key = key.replace(".", "_");
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
		if (constants == null)
			return id;
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
}
