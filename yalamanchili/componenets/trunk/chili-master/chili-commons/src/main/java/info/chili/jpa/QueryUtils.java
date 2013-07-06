/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.jpa;

import info.chili.commons.CollectionsUtils;
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

    public static Map<String, String> getEntityStringMapByParams(EntityManager em, Class<?> clazz, Integer start, Integer limit, String... columns) {
        String query = getListBoxResultsQueryString(clazz.getCanonicalName(), columns);
        Map<String, String> values = new HashMap<String, String>();
        Query getListBoxValuesQuery = em.createQuery(query);
        getListBoxValuesQuery.setFirstResult(start);
        getListBoxValuesQuery.setMaxResults(limit);
        for (Object obj : getListBoxValuesQuery.getResultList()) {
            Object[] obs = (Object[]) obj;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < columns.length; i++) {
                sb.append(obs[i].toString());
                sb.append(" ");
            }
            values.put(obs[0].toString(), sb.toString());
        }
        return CollectionsUtils.sortByComparator(values);
    }

    public static Map<String, String> getEntityStringMapByParams(EntityManager em, String query, Integer start, Integer limit, String... columns) {
        Map<String, String> values = new HashMap<String, String>();
        Query getListBoxValuesQuery = em.createQuery(query);
        getListBoxValuesQuery.setFirstResult(start);
        getListBoxValuesQuery.setMaxResults(limit);
        for (Object obj : getListBoxValuesQuery.getResultList()) {
            Object[] obs = (Object[]) obj;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < columns.length; i++) {
                sb.append(obs[i].toString());
                sb.append(" ");
            }
            values.put(obs[0].toString(), sb.toString());
        }
        return CollectionsUtils.sortByComparator(values);
    }

    public static String getSuggestionsQueryForName(String attributeName, Class<?> entityCls) {
        String query = "SELECT " + attributeName + " FROM " + entityCls.getCanonicalName();
        return query;
    }

    /**
     * pass the columns names starting with primary key
     *
     */
    public static String getListBoxResultsQueryString(String className, String... columns) {
        String query = "SELECT ";
        for (String column : columns) {
            query = query.concat(column + ",");
        }
        query = query.substring(0, query.length() - 1);
        query = query.concat(" FROM " + className);
        return query;
    }
}
