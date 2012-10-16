package info.yalamanchili.gwt.utils;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<Integer, String> convertKeyValuePairs(String jsonString) {
        JSONObject listObject = (JSONObject) JSONParser.parseLenient(jsonString);
        if (listObject.get("entry") != null) {
            JSONArray entities = JSONUtils.toJSONArray(listObject.get("entry"));
            return convertJSONArrayToKayValueMap(entities);
        }
        return null;
    }

    protected static Map<Integer, String> convertJSONArrayToKayValueMap(JSONArray entities) {
        Map<Integer, String> values = new HashMap<Integer, String>();
        for (int i = 1; i <= entities.size(); i++) {
            JSONObject entity = (JSONObject) entities.get(i - 1);
            Integer key = Integer.valueOf(JSONUtils.toString(entity, "id"));
            String value = JSONUtils.toString(entity, "value");
            values.put(key, value);
        }
        return values;
    }
}
