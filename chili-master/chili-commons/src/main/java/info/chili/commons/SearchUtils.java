package info.chili.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public static String getSearchQueryString(Class cls, String searchString) {
        if (searchString.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        query.append("FROM " + cls.getCanonicalName() + " WHERE ");
        String[] fields = ReflectionUtils.getBeanProperties(cls, DataType.STRING);
        List<String> filters = new ArrayList<String>();
        for (String field : fields) {
            filters.add(field + " LIKE '%" + searchString.trim() + "%'");
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

    public static String getSearchSizeQuery(String query) {
        return "SELECT COUNT(*) " + query;
    }

    public static <T> String getSearchQuery(T entity) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(convertEntityAlias(entity)).append(" FROM ").append(entity.getClass().getSimpleName()).append(" ").append(convertEntityAlias(entity));
        List<String> filters = new ArrayList<String>();
        List<Object> joins = new ArrayList<Object>();
        getEntityNestedSearchFiltersAndJoins(entity, filters, joins);
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

    public static <T> String getSearchSizeQuery(T entity, String qry) {
        String str1 = "SELECT " + convertEntityAlias(entity) + " FROM";
        String str2 = "SELECT COUNT(" + convertEntityAlias(entity) + ") FROM";
        return qry.replace(str1, str2);
    }

    protected static <T> void getEntityNestedSearchFiltersAndJoins(T entity, List<String> filters, List<Object> joins) {
        for (Field field : ReflectionUtils.getAllFields(entity.getClass())) {
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
                    if (value instanceof List || value instanceof Set) {
                        ArrayList list = (ArrayList) value;
                        if (list.size() > 0) {
                            Object child = list.get(0);
                            joins.add(child);
                            filters.add(convertEntityAlias(child) + "." + StringUtils.getStringCamelCase(ReflectionUtils.getRootEntityClass(entity.getClass()).getSimpleName()) + ".id=" + convertEntityAlias(entity) + ".id");
                            getEntityNestedSearchFiltersAndJoins(child, filters, joins);
                        }
                    }
                }
            }
        }
    }

    protected static <T> String convertEntityAlias(T entity) {
        return entity.getClass().getSimpleName().toLowerCase() + "_";
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
}