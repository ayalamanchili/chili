/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ayalamanchili
 */
public class CollectionsUtils {

    /**
     * sorts the input hashmap values (string) aplhabetically used with query
     * utils to sort the return list alphabetically
     *
     * @param unsortMap
     * @return
     */
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
