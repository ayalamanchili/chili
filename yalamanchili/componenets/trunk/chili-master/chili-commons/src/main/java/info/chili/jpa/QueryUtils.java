/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ayalamanchili
 */
public class QueryUtils {

    public static <T> T findEntity(EntityManager em, Class<?> entityCls, String paramName, String paramValue) {
        Query query = em.createQuery("from " + entityCls.getCanonicalName() + " where " + paramName + "='" + paramValue
                + "'", entityCls);
        if (query.getResultList().size() > 0) {
            return (T) query.getResultList().get(0);
        } else {
            return null;
        }
    }

    public static Map<Long, String> getListBoxValues(Class<?> clazz, String[] columns, EntityManager em) {
        String query = getListBoxResultsQueryString(clazz.getCanonicalName(), columns);
        Map<Long, String> values = new HashMap<Long, String>();
        Query getListBoxValues = em.createQuery(query);
        for (Object obj : getListBoxValues.getResultList()) {
            Object[] obs = (Object[]) obj;
            values.put((Long) obs[0], (String) obs[1]);
        }
        return values;
    }

    public static String getSuggestionsQueryForName(String attributeName, Class<?> entityCls) {
        String query = "SELECT " + attributeName + " FROM " + entityCls.getCanonicalName();
        return query;
    }

    public static String getListBoxResultsQueryString(String className, String[] columns) {
        String query = "SELECT id,";
        for (String column : columns) {
            query = query.concat(column + ",");
        }
        query = query.substring(0, query.length() - 1);
        query = query.concat(" FROM " + className);
        return query;
    }
}
