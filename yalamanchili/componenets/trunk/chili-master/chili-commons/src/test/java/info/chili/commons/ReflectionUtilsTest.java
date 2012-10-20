package info.chili.commons;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import info.chili.commons.entity.Entity;
import info.chili.commons.entity.EntityChildTwo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReflectionUtilsTest {

    private static final Log log = LogFactory.getLog(ReflectionUtilsTest.class);

    @Before
    public void init() {
    }

    @Test
    public void testToString() {
        Entity entity = new Entity();
        entity.setIntegerField(new Integer(10));
        entity.setStringField("test");
        entity.setLongField(new Long(10));
        entity.setFloatField(new Float(10.99));
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        entity.setDateField(date);
        System.out.println(ReflectionUtils.toString(entity));
    }

    @Test
    public void testGetGetterMethod() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        Field integerField = null;
        Field StringField = null;
        Field floatField = null;
        Field longField = null;
        Field dateField = null;
        try {
            integerField = Entity.class.getDeclaredField("integerField");
            StringField = Entity.class.getDeclaredField("stringField");
            floatField = Entity.class.getDeclaredField("floatField");
            longField = Entity.class.getDeclaredField("longField");
            dateField = Entity.class.getDeclaredField("dateField");
        } catch (Exception e) {
            fail("cannot find one or more field in the object specified");
        }
        Method integerMethod = ReflectionUtils.getGetterMethod(integerField,
                Entity.class);
        assertTrue(integerMethod.getReturnType()
                .isAssignableFrom(Integer.class));
    }

    @Test
    public void testCallGetterMethod() {
        Entity entity = new Entity();
        entity.setIntegerField(new Integer(10));
        entity.setStringField("test");
        entity.setLongField(new Long(10));
        entity.setFloatField(new Float(10.99));
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        entity.setDateField(date);

        Field integerField = null;
        Field StringField = null;
        Field floatField = null;
        Field longField = null;
        Field dateField = null;
        try {
            integerField = entity.getClass().getDeclaredField("integerField");
            StringField = entity.getClass().getDeclaredField("stringField");
            floatField = entity.getClass().getDeclaredField("floatField");
            longField = entity.getClass().getDeclaredField("longField");
            dateField = entity.getClass().getDeclaredField("dateField");
        } catch (Exception e) {
            fail("cannot find one or more field in the object specified");
        }
        assertTrue(new Integer(10).equals(ReflectionUtils.callGetterMethod(
                entity, integerField)));
        assertTrue("test".equals(ReflectionUtils.callGetterMethod(entity,
                StringField)));
        assertTrue(new Float(10.99).equals(ReflectionUtils.callGetterMethod(
                entity, floatField)));
        assertTrue(new Long(10).equals(ReflectionUtils.callGetterMethod(entity,
                longField)));
        assertTrue(date.equals(ReflectionUtils.callGetterMethod(entity,
                dateField)));

    }

    @Test
    public void testGetSetterMethod() {
        Field integerField = null;
        Field StringField = null;
        Field floatField = null;
        Field longField = null;
        Field dateField = null;
        try {
            integerField = Entity.class.getDeclaredField("integerField");
            StringField = Entity.class.getDeclaredField("stringField");
            floatField = Entity.class.getDeclaredField("floatField");
            longField = Entity.class.getDeclaredField("longField");
            dateField = Entity.class.getDeclaredField("dateField");
        } catch (Exception e) {
            fail("cannot find one or more field in the object specified");
        }
        Method integerMethod = ReflectionUtils.getSetterMethod(integerField,
                Entity.class);
        assertTrue(integerMethod.getParameterTypes()[0]
                .isAssignableFrom(Integer.class));
    }

    @Test
    public void testCallSetterMethod() {
        Entity entity = new Entity();
        Field integerField = null;
        Field StringField = null;
        Field floatField = null;
        Field longField = null;
        Field dateField = null;
        try {
            integerField = entity.getClass().getDeclaredField("integerField");
            StringField = entity.getClass().getDeclaredField("stringField");
            floatField = entity.getClass().getDeclaredField("floatField");
            longField = entity.getClass().getDeclaredField("longField");
            dateField = entity.getClass().getDeclaredField("dateField");
        } catch (Exception e) {
            fail("cannot find one or more field in the object specified");
        }
        ReflectionUtils.callSetterMethod(entity, integerField, new Integer(10));
        ReflectionUtils.callSetterMethod(entity, StringField, "test");
        ReflectionUtils.callSetterMethod(entity, floatField, new Float(10.99));
        ReflectionUtils.callSetterMethod(entity, longField, new Long(10));
        ReflectionUtils.callSetterMethod(entity, dateField, new Date());

        assertTrue(entity.getIntegerField().equals(new Integer(10)));
        assertTrue(entity.getStringField().equals("test"));
        assertTrue(entity.getFloatField().equals(new Float(10.99)));
        assertTrue(entity.getLongField().equals(new Long(10)));
        assertTrue(entity.getDateField().toString()
                .equals(new Date().toString()));
    }

    @Test
    public void testGetAllFields() {
        ReflectionUtils.getAllFields(Entity.class);
    }

    @Test
    public void testGetBeanProperties() {
        for (String a : ReflectionUtils.getBeanProperties(Entity.class,
                DataType.STRING)) {
            System.out.println(a);
        }
    }

    @Test
    public void testCallSetterGetter() {
        Class clazz;
        try {
            clazz = Class.forName(EntityChildTwo.class.getName());
            Object instance = clazz.newInstance();
            ReflectionUtils.callSetter(instance, "childTwoName", "testname");
            System.out.println(ReflectionUtils.callGetter(instance, "childTwoName"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @After
    public void destroy() {
    }
}
