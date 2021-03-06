package info.chili.commons;

import info.chili.jpa.AbstractEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

public class SearchUtils {

    private static final Log log = LogFactory.getLog(SearchUtils.class);

    public static String getSearchQueryString(Class cls, String searchString, List<String> columns, boolean splitSearchString) {
        if (searchString.isEmpty()) {
            return null;
        }
        if (columns == null || columns.size() == 0) {
            columns = Arrays.asList(ReflectionUtils.getBeanProperties(cls, DataType.STRING));
        }
        String[] searchTextFrags = searchString.trim().split(" ");
        StringBuilder query = new StringBuilder();
        query.append("FROM ").append(cls.getCanonicalName()).append(" WHERE ");
        List<String> filters = new ArrayList<>();
        for (String cloumn : columns) {
            if (splitSearchString) {
                for (String searchFrag : searchTextFrags) {
                    filters.add(cloumn + " LIKE '%" + searchFrag.trim() + "%'");
                }
            } else {
                filters.add(cloumn + " LIKE '%" + searchString.trim() + "%'");
            }
        }
        int i = 0;
        for (String filter : filters) {
            query = query.append(filter);
            i++;
            if (i < filters.size()) {
                query = query.append(" OR ");
            }
        }
        log.info("search query String:" + query);
        return query.toString();
    }

    public static String getSearchSizeQuery(Class cls, String searchText, List<String> columns, boolean splitSearchString) {
        //TODO avoid unnecessary call
        return "SELECT COUNT(*) " + getSearchQueryString(cls, searchText, columns, splitSearchString);
    }

    @Deprecated
    public static <T> String getSearchQuery(T entity) {
        return getSearchQuery(entity, new SearchCriteria());
    }

    public static <T> javax.persistence.Query getSearchQuery(EntityManager em, T entity, SearchCriteria criteria) {
        String qryStr = getSearchQuery(entity, criteria);
        javax.persistence.Query q = em.createQuery(qryStr);
        for (Field field : ReflectionUtils.getAllFields(entity.getClass())) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Method getterMethod = ReflectionUtils.getGetterMethod(field, entity.getClass());
            if (field.getType().isAssignableFrom(Date.class) && ReflectionUtils.callGetterMethod(getterMethod, entity) != null) {
                Date startDate = (Date) ReflectionUtils.callGetterMethod(getterMethod, entity);
                Date endDate = DateUtils.getNextDay(startDate, criteria.dateRange);
                q.setParameter(field.getName() + "StartParam", startDate);
                q.setParameter(field.getName() + "EndParam", endDate);
            }
        }
        return q;
    }

    public static <T> String getSearchQuery(T entity, SearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT ").append(convertEntityAlias(entity)).append(" FROM ").append(entity.getClass().getSimpleName()).append(" ").append(convertEntityAlias(entity));
        List<String> filters = new ArrayList<>();
        List<Object> joins = new ArrayList<>();
        getEntityNestedSearchFiltersAndJoins(entity, filters, joins, criteria);
        int i = 0;
        for (Object joinEntity : joins) {
            if (i < joins.size()) {
                sb.append(" , ");
            }
            sb.append(joinEntity.getClass().getSimpleName()).append(" ").append(convertEntityAlias(joinEntity));
            i++;
        }
        sb.append(" WHERE ");
        int j = 0;
        for (String filter : filters) {
            sb.append(filter);
            j++;
            if (j < filters.size()) {
                sb.append(" AND ");
            }
        }
        log.info("search query String:" + sb.toString());
        return sb.toString();
    }

    @Deprecated
    public static <T> String getSearchSizeQuery(T entity) {
        return getSearchQuery(entity, new SearchCriteria());
    }

    public static <T> String getSearchSizeQuery(T entity, SearchCriteria criteria) {
        String str1 = "SELECT DISTINCT " + convertEntityAlias(entity) + " FROM";
        String str2 = "SELECT COUNT(DISTINCT " + convertEntityAlias(entity) + ") FROM";
        return getSearchQuery(entity, criteria).replace(str1, str2);
    }

    public static <T> Long getSearchSize(EntityManager em, T entity) {
        return getSearchSize(em, entity, new SearchCriteria());
    }

    public static <T> Long getSearchSize(EntityManager em, T entity, SearchCriteria criteria) {
        javax.persistence.Query sizeQuery = em.createQuery(SearchUtils.getSearchSizeQuery(entity, criteria));
        return (Long) sizeQuery.getSingleResult();
    }

    protected static <T> void getEntityNestedSearchFiltersAndJoins(T entity, List<String> filters, List<Object> joins, SearchCriteria criteria) {
        for (Field field : ReflectionUtils.getAllFields(entity.getClass())) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Method getterMethod = ReflectionUtils.getGetterMethod(field, entity.getClass());
            if (getterMethod != null) {
                Object value = ReflectionUtils.callGetterMethod(getterMethod, entity);
                if (value != null && !value.toString().isEmpty()) {
                    if (value instanceof String) {
                        filters.add(convertEntityAlias(entity) + "." + field.getName() + " LIKE '%" + value.toString().trim() + "%'");
                    }
                    if (value instanceof Long || value instanceof Integer || value instanceof Float) {
                        filters.add(convertEntityAlias(entity) + "." + field.getName() + " = " + value.toString().trim());
                    }
                    if (value.getClass().isEnum()) {
                        filters.add(convertEntityAlias(entity) + "." + field.getName() + " = '" + value.toString().trim() + "'");
                    }
                    if (value instanceof Date) {
                        filters.add(convertEntityAlias(entity) + "." + field.getName() + " BETWEEN :" + field.getName() + "StartParam" + " AND :" + field.getName() + "EndParam");
                    }
                    if (value instanceof List || value instanceof Set) {
                        Collection list = (Collection) value;
                        if (list.size() > 0) {
                            Object child = list.iterator().next();
                            joins.add(child);
                            filters.add(convertEntityAlias(child) + "." + getEntityPropertyClassWithParent(entity, child) + ".id=" + convertEntityAlias(entity) + ".id");
                            getEntityNestedSearchFiltersAndJoins(child, filters, joins, criteria);
                        }
                    }
                    if (value instanceof AbstractEntity) {
                        joins.add(value);
                        filters.add(getEntityPropertyClassWithChild(entity, value).toLowerCase() + "." + convertEntityAlias(value) + ".id=" + convertEntityAlias(value) + ".id");
                        getEntityNestedSearchFiltersAndJoins(value, filters, joins, criteria);
                    }
                }
            }
        }
    }

    /*
     * this would return the apprioriate entity property for sql query
     */
    protected static <T> String getEntityPropertyClassWithChild(T parent, T child) {
        for (Field f : ReflectionUtils.getAllFields(parent.getClass())) {
            if (f.getType().equals(child.getClass())) {
                return StringUtils.getStringCamelCase(parent.getClass().getSimpleName());
            }
            //support for list and sets
            if (f.getType().equals(List.class) || f.getType().equals(Set.class)) {
                ParameterizedType listType = (ParameterizedType) f.getGenericType();
                if (listType != null) {
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    if (listClass.equals(child.getClass())) {
                        return StringUtils.getStringCamelCase(parent.getClass().getSimpleName());
                    }
                }
            }

        }
        //if child does not have the parent property return parent root class
        return StringUtils.getStringCamelCase(ReflectionUtils.getRootEntityClass(parent.getClass()).getSimpleName());
    }

    protected static <T> String getEntityPropertyClassWithParent(T parent, T child) {
        for (Field f : ReflectionUtils.getAllFields(child.getClass())) {
            if (f.getType().equals(parent.getClass())) {
                return StringUtils.getStringCamelCase(parent.getClass().getSimpleName());
            }
            //support for list and sets
            if (f.getType().equals(List.class) || f.getType().equals(Set.class)) {
                ParameterizedType listType = (ParameterizedType) f.getGenericType();
                if (listType != null) {
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    if (listClass.equals(parent.getClass())) {
                        return StringUtils.getStringCamelCase(parent.getClass().getSimpleName());
                    }
                }
            }

        }
        //if child does not have the parent property return parent root class
        return StringUtils.getStringCamelCase(ReflectionUtils.getRootEntityClass(parent.getClass()).getSimpleName());
    }

    protected static <T> String convertEntityAlias(T entity) {
        return entity.getClass().getSimpleName().substring(0, 1).toLowerCase() + entity.getClass().getSimpleName().substring(1);
    }

    public static Query getLuceneQuery(String searchText, StandardAnalyzer anaylyzer, String... fields) {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_30, fields, new StandardAnalyzer(
                Version.LUCENE_30));
        org.apache.lucene.search.Query luceneQuery = null;
        try {
            luceneQuery = parser.parse(getSearchQuery(searchText, fields));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return luceneQuery;
    }

    /*
     * this is a temp method to get around.
     * http://littlesquare.com/2010/05/upgrading
     * -to-hibernate-search-3-2-0-w-seam/
     */
    public static FullTextSession getFullTextSession(EntityManager em) {
        Session session = (Session) em.getDelegate();
        return Search.getFullTextSession(session.getSessionFactory().getCurrentSession());
    }

    // TODO fix issue with empty string
    /**
     * Caused by: org.apache.lucene.queryParser.ParseException: Cannot parse '':
     * Encountered "<EOF>" at line 1, column 0. Was expecting one of: <NOT> ...
     * "+" ... "-" ... "(" ... "*" ... <QUOTED> ... <TERM> ... <PREFIXTERM> ...
     */
    protected static String getSearchQuery(String searchText, String... fields) {
        StringBuilder searchQuery = new StringBuilder();
        for (String word : splitSearchString(searchText, ' ')) {
            for (String field : fields) {
                searchQuery.append(field);
                searchQuery.append(":");
                searchQuery.append(word);
                searchQuery.append(" ");
            }
        }
        log.info("lucene search query:" + searchQuery.toString());
        return searchQuery.toString();
    }

    public static List<String> splitSearchString(String searchText, char seperator) {
        List<String> words = new ArrayList<String>();
        Scanner searchTextScanner = new Scanner(searchText);
        while (searchTextScanner.hasNext()) {
            words.add(searchTextScanner.next());
        }
        return words;
    }

    public static class SearchCriteria {

        int dateRange = 30;

        public int getDateRange() {
            return dateRange;
        }

        public void setDateRange(int dateRange) {
            this.dateRange = dateRange;
        }

    }
}
