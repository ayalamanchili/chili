/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.commons;

import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ayalamanchili
 */
public class DateUtilsTest {

    @Test
    public void testLessThanOneYears() {
        Calendar today = Calendar.getInstance();
        today.set(2015, Calendar.DECEMBER, 9);
        Calendar start = Calendar.getInstance();
        start.set(2015, Calendar.JANUARY, 10);
        assertTrue(today.getTime().before(DateUtils.getNextYear(start.getTime(), 1)));
    }

    @Test
    public void testBetweenTwoAndFiveYears() {
        Calendar today = Calendar.getInstance();
        today.set(2015, Calendar.JANUARY, 9);
        Calendar start = Calendar.getInstance();
        start.set(2014, Calendar.JANUARY, 8);
        assertTrue(today.getTime().after(DateUtils.getNextYear(start.getTime(), 1)) && today.getTime().before(DateUtils.getNextYear(start.getTime(), 5)));
    }

    @Test
    public void testBetweenFiveAndTenYears() {
        Calendar start = Calendar.getInstance();
        start.set(2008, Calendar.JANUARY, 9);
        Calendar today = Calendar.getInstance();
        today.set(2014, Calendar.JANUARY, 8);
        assertTrue(today.getTime().after(DateUtils.getNextYear(start.getTime(), 5)) && today.getTime().before(DateUtils.getNextYear(start.getTime(), 10)));
    }

    @Test
    public void testMoreThanTenYears() {
        Calendar start = Calendar.getInstance();
        start.set(2004, Calendar.JANUARY, 7);
        Calendar today = Calendar.getInstance();
        today.set(2014, Calendar.JANUARY, 8);
        assertTrue(today.getTime().after(DateUtils.getNextYear(start.getTime(), 10)));
    }
}
