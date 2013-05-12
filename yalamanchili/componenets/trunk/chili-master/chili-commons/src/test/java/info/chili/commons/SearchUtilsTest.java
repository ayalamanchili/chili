package info.chili.commons;

import info.chili.beans.EntityChildOne;
import info.chili.beans.EntitySuper;
import info.chili.commons.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class SearchUtilsTest {

    private static final Log log = LogFactory.getLog(SearchUtilsTest.class);

    @Before
    public void init() {
    }

    @Test
    public void testGetSearchQueryString() {
        Entity entity = new Entity();
        entity.setStringField("searchentityString");
        entity.setSuperString("entitysearchsuper string");
        String expectedResult = "FROM info.chili.commons.entity.Entity WHERE stringField LIKE '%searchentityString%' AND superString LIKE '%entitysearchsuper string%'";
        assertEquals(expectedResult, SearchUtils.getSearchQueryString(entity));
    }

    @Test
    public void testGetEntityNestedSearchFilters() {
        EntitySuper e = new EntitySuper();
        EntityChildOne child = new EntityChildOne();
        child.setChildOneName("childname");
        e.getChildrenOne().add(child);
        System.out.println(SearchUtils.getNestedSearchQuery(e));
    }

    @After
    public void destroy() {
    }
}
