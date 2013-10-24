package info.chili.commons;

import info.chili.jpa.AbstractEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.Type;

public class ReflectionUtils {

    public static LinkedHashMap<String, DataType> getEntityFieldsInfo(
            Class<?> clazz, boolean ignoreEncryptedFields) {
        LinkedHashMap<String, DataType> dataFields = new LinkedHashMap<String, DataType>();
        for (Field field : getAllFields(clazz)) {
            if (!DataType.DEFAULT.equals(getDataType(field))) {
                if (ignoreEncryptedFields && isEncryptedField(field)) {
                    continue;
                }
                dataFields.put(field.getName(), getDataType(field));
            }
        }
        return dataFields;
    }

    public static boolean isEncryptedField(Field field) {
        Type type = field.getAnnotation(Type.class);
        //TODO why is netbeans generating a warning ?
        if ((type != null && type.type().contains("encrypted") || field.getName().contains("Hash"))) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, Object> getFieldsDataFromEntity(Object t,
            Class<?> clazz, boolean ignoreEncryptedFields) {
        Map<String, Object> attributeValues = new HashMap<String, Object>();
        LinkedHashMap<String, DataType> fields = getEntityFieldsInfo(clazz, ignoreEncryptedFields);
        try {
            for (String fieldName : fields.keySet()) {

                for (Method method : t.getClass().getMethods()) {
                    if (method.getName().compareToIgnoreCase("get" + fieldName) == 0) {
                        if (fields.get(fieldName).equals(DataType.STRING)) {
                            String result = (String) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.INTEGER)) {
                            Integer result = (Integer) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.LONG)) {
                            Long result = (Long) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.FLOAT)) {
                            Float result = (Float) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.DATE)) {
                            Date result = (Date) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.BOOLEAN)) {
                            Boolean result = (Boolean) method.invoke(t);
                            attributeValues.put(fieldName, result);
                        }
                        if (fields.get(fieldName).equals(DataType.ENUM)) {
                            Object value = method.invoke(t);
                            if (value != null) {
                                String result = method.invoke(t).toString();
                                attributeValues.put(fieldName, result);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attributeValues;
    }

    @Deprecated
    public static Map<String, Object> getFieldsDataFromEntity(Object t,
            Class<?> clazz) {
        return getFieldsDataFromEntity(t, clazz, false);
    }

    public static DataType getDataType(Field field) {
        Class<?> clazz = field.getType();
        if (clazz.equals(Integer.class)) {
            return DataType.INTEGER;
        }
        if (clazz.equals(String.class)) {
            return DataType.STRING;
        }
        if (clazz.equals(Long.class)) {
            return DataType.LONG;
        }
        if (clazz.equals(Boolean.class)) {
            return DataType.BOOLEAN;
        }
        if (clazz.equals(Date.class)) {
            return DataType.DATE;
        }
        if (clazz.equals(Float.class)) {
            return DataType.FLOAT;
        }
        if (clazz.equals(Double.class)) {
            return DataType.DOUBLE;
        }
        if (clazz.equals(BigInteger.class)) {
            return DataType.BIG_INTEGER;
        }
        if (clazz.equals(BigDecimal.class)) {
            return DataType.BIG_DECIMAL;
        }
        if (clazz.isEnum()) {
            return DataType.ENUM;
        }
        return DataType.DEFAULT;
    }

    public static Enum<?>[] getEnumValues(Class<?> entity, String attributeName) {
        Enum<?>[] var = null;
        Field field = getField(entity, attributeName);
        for (Method m : field.getType().getDeclaredMethods()) {
            if (m.getName().equals("values")) {
                try {
                    var = (Enum<?>[]) m.invoke(entity, new Object[]{});
                } catch (Exception e1) {
                    throw new RuntimeException(
                            "error gettng enum values on class:"
                            + entity.getCanonicalName()
                            + ":field name:" + attributeName);
                }
            }
        }

        return var;
    }

    public static Object getEnumValue(Class<?> entity, String attributeName,
            String value) {
        Object var = null;
        Field field = getField(entity, attributeName);
        if (field != null) {
            for (Method m : field.getType().getDeclaredMethods()) {
                if (m.getName().equals("valueOf")) {
                    try {
                        var = (Enum<?>) m.invoke(entity, value);
                    } catch (Exception e1) {
                        throw new RuntimeException(
                                "error gettng enum values on class:"
                                + entity.getCanonicalName()
                                + ":field name:" + attributeName);
                    }
                }
            }
        }
        return var;
    }

    public static String toString(Object entity) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> attributeValues = getFieldsDataFromEntity(entity,
                entity.getClass());
        for (String attribute : attributeValues.keySet()) {
            if (attributeValues.get(attribute) != null) {
                sb.append(attributeValues.get(attribute).toString());
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public static List<String> getBeanProperties(Class<?> bean) {
        List<String> properties = new ArrayList<String>();
        for (Field field : getAllFields(bean)) {
            properties.add(field.getName());
        }
        return properties;
    }

    public static String[] getBeanProperties(Class<?> bean, DataType... types) {
        List<String> properties = new ArrayList<String>();
        for (Field field : getAllFields(bean)) {
            for (DataType dataType : types) {
                if (dataType.equals(getDataType(field))) {
                    properties.add(field.getName());
                }
            }
        }
        return properties.toArray(new String[0]);
    }

    public static List<Field> getAllFields(Class<?> entity) {
        List<Field> fields = new ArrayList<Field>();
        do {
            //TODO try calling getFields[] rather than getDeclaredFields();
            fields.addAll(Arrays.asList(entity.getDeclaredFields()));
            entity = entity.getSuperclass();

        } while (!entity.equals(java.lang.Object.class));
        return fields;
    }

    public static Class getRootEntityClass(Class cls) {
        while (cls.getSuperclass() != null && !cls.getSuperclass().equals(AbstractEntity.class)) {
            cls = cls.getSuperclass();
        }
        return cls;
    }

    public static List<Method> getAllMethods(Class<?> entity) {
        List<Method> methods = new ArrayList<Method>();
        do {
            methods.addAll(Arrays.asList(entity.getDeclaredMethods()));
            entity = entity.getSuperclass();

        } while (!entity.equals(java.lang.Object.class));
        return methods;
    }

    public static Object callGetter(Object entity, String attributeName) {
        return callGetterMethod(
                getGetterMethod(getField(entity.getClass(), attributeName),
                        entity.getClass()), entity);
    }

    public static Field getField(Class<?> clazz, String attributeName) {
        for (Field field : getAllFields(clazz)) {
            if (field.getName().compareToIgnoreCase(attributeName) == 0) {
                return field;
            }
        }
        return null;
    }

    public static Method getGetterMethod(Field field, Class<?> clazz) {
        for (Method getterMethod : getAllMethods(clazz)) {
            if (getterMethod.getName()
                    .equalsIgnoreCase("get" + field.getName())) {
                return getterMethod;
            }
        }
        return null;
    }

    public static Object callGetterMethod(Method getterMethod, Object source) {
        Object result = null;
        if (getterMethod != null) {
            try {
                result = getterMethod.invoke(source);
            } catch (Exception e) {
                if (getterMethod == null || source == null) {
                    throw new RuntimeException(
                            "Failed to invoke getter method Method or source is null");
                } else {
                    throw new RuntimeException(
                            "Failed to invoke getter method on field:"
                            + getterMethod.getName() + "on:"
                            + source.getClass().toString(), e);
                }
            }
        }
        return result;
    }

    public static Object callGetterMethod(Object source, Field field) {
        Method getterMethod = getGetterMethod(field, source.getClass());
        Object result = null;
        if (getterMethod != null) {
            try {
                result = getterMethod.invoke(source);
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to invoke getter method on field:"
                        + field.getName(), e);
            }
        }
        return result;
    }

    public static void callSetter(Object entity, String attributeName,
            Object value) {
        callSetterMethod(
                getSetterMethod(getField(entity.getClass(), attributeName),
                        entity.getClass()), entity, value);
    }

    public static Method getSetterMethod(Field field, Class<?> clazz) {
        for (Method getterMethod : getAllMethods(clazz)) {
            if (getterMethod.getName()
                    .equalsIgnoreCase("set" + field.getName())) {
                return getterMethod;
            }
        }
        return null;
    }

    public static void callSetterMethod(Method setterMethod, Object source,
            Object value) {
        try {
            setterMethod.invoke(source, value);
        } catch (Exception e) {
            if (setterMethod == null || source == null) {
                throw new RuntimeException(
                        "Failed to invoke setter method Method or source is null");
            } else {
                throw new RuntimeException("Failed to invoke setter method"
                        + setterMethod.getName() + "on:"
                        + source.getClass().getName(), e);
            }
        }
    }

    public static void callSetterMethod(Object source, Field field, Object value) {
        Method setterMethod = getSetterMethod(field, source.getClass());
        try {
            setterMethod.invoke(source, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke setter method on"
                    + field.getName(), e);
        }
    }

    public static List<Method> getGetterMethods(Class<?> clazz) {
        List<Method> list = new ArrayList<Method>();
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            if (method.getName().startsWith("get")
                    && !"getClass".equals(method.getName())) {
                list.add(method);
            }
        }
        return list;
    }
}
