/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.beans;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ayalamanchili
 */
public class BeanMapperTest {

    @Test
    public void testMerge() {
        Entity source = new Entity();
        Entity target = new Entity();

        source.setBooleanField(true);
        source.setIntegerField(100);
        source.setStringField("sporce");

        target.setBooleanField(false);
        target.setIntegerField(200);
        target.setStringField("targer");
        BeanMapper.merge(source, target);
        assertTrue(target.getBooleanField());
    }
}
