/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author ayalamanchili
 */
public class QueryUtilsTest {

    @Test
    public void testToString() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("3", "BBB");
        map.put("4", "bbb");
        map.put("2", "CCC");
        map.put("1", "ccc");
        map.put("5", "AAA");
        map.put("6", "aaa");
        System.out.println(CollectionsUtils.sortByComparator(map));
    }
}
