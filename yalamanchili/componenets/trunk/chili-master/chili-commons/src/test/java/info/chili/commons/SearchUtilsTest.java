package info.chili.commons;

import info.chili.beans.EntityChildOne;
import info.chili.beans.EntitySuper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchUtilsTest {

    private static final Log log = LogFactory.getLog(SearchUtilsTest.class);

    @Before
    public void init() {
    }

    @Test
    public void testGetEntityNestedSearchFilters() {
        EntitySuper e = new EntitySuper();
        e.setSuperString("testStr");
        EntityChildOne child = new EntityChildOne();
        child.setChildOneName("childname");
        e.getChildrenOne().add(child);
        String expectedQuery = SearchUtils.getSearchQuery(e);
        System.out.println(expectedQuery);
        System.out.println(SearchUtils.getSearchSizeQuery(e));
    }

    @After
    public void destroy() {
    }
}
