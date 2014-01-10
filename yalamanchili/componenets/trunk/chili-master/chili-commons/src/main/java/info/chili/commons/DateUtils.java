package info.chili.commons;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public final static Date getNextYear(Date now, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, i);
        return calendar.getTime();
    }

    public final static Date getNextMonth(Date now, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, i);
        return calendar.getTime();
    }

    public final static Date getNextWeek(Date now, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, i * 7);
        return calendar.getTime();
    }

    public final static Date getNextDay(Date now, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, i);
        return calendar.getTime();
    }
    private final static long MS_DAY_LONG = 1000 * 60 * 60 * 24;

    public final static long getBetweenDay(Date beginTime, Date endTime) {
        return (endTime.getTime() - beginTime.getTime()) / MS_DAY_LONG;
    }

    public static Date parse(String input) throws java.text.ParseException {
        // NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        // things a bit. Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        // this is zero time so we need to add that TZ indicator for
        if (input.endsWith("Z")) {
            input = input.substring(0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;
            String s0 = input.substring(0, input.length() - inset);
            String s1 = input.substring(input.length() - inset, input.length());
            input = s0 + "GMT" + s1;
        }
        return df.parse(input);

    }

    public static Date parse(String dateString, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getLastDayOfMonth(int month, int year) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, 1);
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /* for JSON to string conversion */

    public static String toString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        df.setTimeZone(tz);
        String output = df.format(date);
        int inset0 = 9;
        int inset1 = 6;
        String s0 = output.substring(0, output.length() - inset0);
        String s1 = output.substring(output.length() - inset1, output.length());
        String result = s0 + s1;
        result = result.replaceAll("UTC", "+00:00");
        return result;

    }

    public static boolean IsLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return IsLeapYear(cal.getTime());
    }

    public static Date getFirstDayOfCurrentYear() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.MONTH, 0);
        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        return calendarStart.getTime();
    }
    
    public static Date getLastDayCurrentOfYear() {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.MONTH, 11);
        calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
        return calendarEnd.getTime();
    }

    public static Integer getDaysCompletedInYear(Date date) {
        return null;
    }

    public static Integer getDaysRemainingInYear(Date date) {
        return null;
    }

    /**
     *
     * @param totalChuck eg: 40 hours
     * @param totalPortaion eg: 365 Days
     * @param proratedPortion eg: 175 Days
     * @return eg: 19.1 hours
     */
    public static BigDecimal getProratedHours(BigDecimal totalChuck, BigDecimal totalPortaion, BigDecimal proratedPortion) {
        return totalChuck.divide(totalPortaion).multiply(proratedPortion);
    }

    public static boolean isLeapYear(int year) {
        if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                if ((year % 400) == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
