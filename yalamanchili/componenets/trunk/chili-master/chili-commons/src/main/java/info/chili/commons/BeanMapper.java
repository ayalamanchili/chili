package info.chili.commons;

import info.yalamanchili.commons.DataType;
import info.yalamanchili.commons.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanMapper {

    public static Object clone(Object source) {
        Object target;
        try {
            target = source.getClass().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create new Instance", e);
        }
        if (source == null) {
            return null;
        }
        for (Field field : ReflectionUtils.getAllFields(source.getClass())) {
            if (!DataType.DEFAULT.equals(ReflectionUtils.getDataType(field))
                    && !DataType.ENUM.equals(ReflectionUtils.getDataType(field))) {
                cloneField(source, target, field);
            }
        }
        return target;
    }

    /* create a new instance and copies the promitive datatypes data */
    public static Object clone(Object source, Class<?> clazz) {
        Object target;
        try {
            target = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not create new Instance", e);
        }
        if (source == null) {
            return null;
        }
        for (Field field : ReflectionUtils.getAllFields(source.getClass())) {
            if (!DataType.DEFAULT.equals(ReflectionUtils.getDataType(field))
                    && !DataType.ENUM.equals(ReflectionUtils.getDataType(field))) {
                cloneField(source, target, field);
            }
        }
        return target;
    }

    /* merges the source to target by only copyinng the promitive datatypes */
    public static Object merge(Object source, Object target) {
        for (Field field : ReflectionUtils.getAllFields(source.getClass())) {
            if (!DataType.DEFAULT.equals(ReflectionUtils.getDataType(field))) {
                mergeField(source, target, field);
            }
        }
        return target;
    }

    public static void mergeField(Object source, Object target, Field field) {
        Method getterMethod = ReflectionUtils.getGetterMethod(field, source.getClass());
        if (getterMethod != null) {
            Object sourceVal = ReflectionUtils.callGetterMethod(getterMethod, source);
            Method setterMethod = ReflectionUtils.getSetterMethod(field, target.getClass());
            // TODO what to do if setter method is null
            if (setterMethod != null) {
                ReflectionUtils.callSetterMethod(setterMethod, target, sourceVal);
            }
        }
    }

    public static void cloneField(Object source, Object target, Field field) {
        Object value = null;
        Method getterMethod = ReflectionUtils.getGetterMethod(field, source.getClass());
        if (getterMethod != null) {
            value = ReflectionUtils.callGetterMethod(getterMethod, source);
        }
        Method setterMethod = ReflectionUtils.getSetterMethod(field, source.getClass());
        if (setterMethod != null) {
            ReflectionUtils.callSetterMethod(setterMethod, target, value);
        }
    }
}
