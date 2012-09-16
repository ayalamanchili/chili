package info.yalamanchili.gwt.utils;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;

public class JSONUtils {

    public static String toString(JSONValue entity, String property) {
        if (entity != null && entity.isObject() != null && entity.isObject().get(property) != null) {
            return entity.isObject().get(property).isString().stringValue();
        } else {
            return "";
        }
    }

    public static JSONArray toJSONArray(JSONValue jsonValue) {
        if (null == jsonValue) {
            return new JSONArray();
        }
        JSONArray array = jsonValue.isArray();
        if (array == null) {
            array = new JSONArray();
            array.set(0, jsonValue);
        }
        return array;
    }
}
