package info.chili.commons;

import info.chili.commons.entity.Entity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        String expectedResult="FROM info.chili.commons.entity.Entity WHERE stringField LIKE '%searchentityString%' AND superString LIKE '%entitysearchsuper string%'";
        assertEquals(expectedResult,SearchUtils.getSearchQueryString(entity));
    }

    @After
    public void destroy() {
    }
}
