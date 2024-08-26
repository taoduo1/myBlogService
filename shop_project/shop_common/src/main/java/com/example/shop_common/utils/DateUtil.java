package com.example.shop_common.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author duo.tao
 * @Description: 时间处理工具类
 * @date 2022-06-13 23:14
 */
public class DateUtil {

    /**
     * milli-seconds of a day.
     */
    public static final int    MILLISECONDS_OF_A_SECOND = 1000;
    /**
     * seconds of an hour.
     */
    public static final int    SECONDS_OF_A_MINUTE      = 60;
    /**
     * seconds of an hour.
     */
    public static final int    SECONDS_OF_AN_HOUR       = 60 * 60;
    /**
     * milli-seconds of a day.
     */
    public static final long   MILLISECONDS_OF_A_DAY    = 24 * 60 * 60 * 1000L;
    /**
     * seconds of a day.
     */
    public static final long   SECONDS_OF_A_DAY         = 24 * 60 * 60L;
    /**
     * Long Format of Date.
     */
    public static final String YYYY_MM_DD               = "yyyy-MM-dd";

    /**
     * Long Format of Time.
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Short Format of Date.
     */
    public static final String YYYYMMDD       = "yyyyMMdd";
    public static final String ZH_YYYYMMDD       = "yyyy年 MM月 dd日";
    /**
     * Short Format of Time.
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMMSSXXX = "yyyyMMddHHmmssSSS";

    public static final String HHMMSS = "HHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS_SLASH = "yyyy/MM/dd HH:mm:ss";

    /**
     * RexExp of Time.
     */
    public static final String REGEXP_YYYY_MM_DD = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {}

    /**
     * 时间范围边界最大值最小值的参数：时
     */
    private static final int HOUR_OF_DAY_MIN = 0;
    private static final int HOUR_OF_DAY_MAX = 23;
    /**
     * 时间范围边界最大值最小值的参数：分
     */
    private static final int MINU_OF_HOUR_MIN = 0;
    private static final int MINU_OF_HOUR_MAX = 59;
    /**
     * 时间范围边界最大值最小值的参数：秒
     */
    private static final int SECO_OF_MIN_MIN  = 0;
    private static final int SECO_OF_MIN_MAX  = 59;
    /**
     * 时间范围边界最大值最小值的参数：毫秒
     */
    private static final int MILLS_OF_SEC_MIN = 0;
    private static final int MILLS_OF_SEC_MAX = 999;

    /**
     * 将两个日期对象（用作时间范围）的时间范围数据补上.
     *
     * @param startDate the start date
     * @param endDate the end date
     */
    public static void dateRangeToTimeRange(Date startDate, Date endDate) {
        if (startDate != null) setTimeMin(startDate);
        if (endDate != null) setTimeMax(endDate);
    }

    /**
     * 将当前date的time部分设置为最大值，返回原date对象.
     *
     * @param date the date
     * @return the date
     */
    public static Date setTimeMax(Date date) {
        return setTime(date, HOUR_OF_DAY_MAX, MINU_OF_HOUR_MAX, SECO_OF_MIN_MAX, MILLS_OF_SEC_MAX);
    }

    /**
     * 将当前date的time部分设置为最小值，返回原date对象.
     *
     * @param date the date
     * @return the date
     */
    public static Date setTimeMin(Date date) {
        return setTime(date, HOUR_OF_DAY_MIN, MINU_OF_HOUR_MIN, SECO_OF_MIN_MIN, MILLS_OF_SEC_MIN);
    }

    /**
     * 给当前对象设置时间（用于范围判断）
     * 
     * @deprecated
     * 
     * @param date
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    @Deprecated
    private static Date setTime(Date date, int hours, int minutes, int seconds) {
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        return date;
    }

    /**
     * 给当前对象设置时间（用于范围判断），含：毫秒.
     *
     * @param date the date
     * @param hours the hours
     * @param minutes the minutes
     * @param seconds the seconds
     * @param millis the millis
     * @return the date
     */
    public static Date setTime(Date date, int hours, int minutes, int seconds, int millis) {
        setTime(date, hours, minutes, seconds);
        //
        long time = date.getTime();
        date.setTime(time - time % 1000 + millis);
        //
        return date;
    }

    public static Date getTime() {
        return new Date();
    }

    /**
     * Gets the time in format.
     *
     * @param format the format
     * @return the time in format
     */
    public static String getTimeInFormat(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * Format.
     *
     * @param date the date
     * @param format the format
     * @return the string
     */
    public static String format(Date date, String format) {
        if (date == null) return null;
        return new SimpleDateFormat(format).format(date);
    }

    public static String getTimeInLongFormat() {
        return getTimeInLongFormat(new Date());
    }

    public static String getTimeInShortFormat() {
        return getTimeInShortFormat(new Date());
    }

    /**
     * Gets the time in long format.
     *
     * @param time the time
     * @return the time in long format
     */
    public static String getTimeInLongFormat(Date time) {
        return time == null ? "" : new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(time);
    }

    /**
     * 返回时间的长字符串格式，含千分秒的数据。.
     *
     * @param time the time
     * @return String
     */
    public static String getTimeInLongFormatWithMillisSecond(Date time) {
        return time == null ? "" : new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_SSS).format(time);
    }

    /**
     * Gets the time in short format.
     *
     * @param time the time
     * @return the time in short format
     */
    public static String getTimeInShortFormat(Date time) {
        return time == null ? "" : new SimpleDateFormat(YYYYMMDDHHMMSS).format(time);
    }

    /**
     * Gets the time in short format.
     *
     * @param time the time
     * @param format the format
     * @return the time in short format
     */
    public static String getTimeInShortFormat(Date time, String format) {
        return time == null ? "" : new SimpleDateFormat(format).format(time);
    }

    /**
     * Gets the time format.
     *
     * @param time the time
     * @return the time format
     */
    public static String getTimeFormat(Date time) {
        return time == null ? "" : new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(time);
    }

    /**
     *  当前时间转换为毫秒级字符串，如 20180929082056500
     */
    public static String getTimeInMillisFormat() {
        return new SimpleDateFormat(YYYYMMDDHHMMSSXXX).format(new Date());
    }

    /**
     * Parses the time in long format.
     *
     * @param strTime the str time
     * @return the date
     */
    public static Date parseTimeInLongFormat(String strTime) {
        return StringUtils.isEmpty(strTime) ? null : parseTime(strTime, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * Parses the time in short format.
     *
     * @param strTime the str time
     * @return the date
     */
    public static Date parseTimeInShortFormat(String strTime) {
        return StringUtils.isEmpty(strTime) ? null : parseTime(strTime, YYYYMMDDHHMMSS);
    }

    /**
     * 按照format格式解析strTime。.
     *
     * @param strTime the str time
     * @param format the format
     * @return the date
     */
    public static Date parseTime(String strTime, String format) {
        return parseTime(strTime, format, null);
    }

    /**
     * 按照format格式解析strTime。.
     *
     * @param strTime the str time
     * @param format the format
     * @param defaultValue the default value
     * @return the date
     */
    public static Date parseTime(String strTime, String format, Date defaultValue) {
        try {
            return new SimpleDateFormat(format).parse(strTime);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date getDate() {
        return DateUtils.truncate(new Date(), Calendar.DATE);
    }

    public static String getDateInShortFormat() {
        return getDateInShortFormat(new Date());
    }

    /**
     * Gets the date in short format.
     *
     * @param date the date
     * @return the date in short format
     */
    public static String getDateInShortFormat(Date date) {
        return date == null ? "" : new SimpleDateFormat(YYYYMMDD).format(date);
    }

    /**
     * Gets the date in long format.
     *
     * @param date the date
     * @return the date in long format
     */
    public static String getDateInLongFormat(Date date) {
        return date == null ? "" : new SimpleDateFormat(YYYY_MM_DD).format(date);
    }

    /**
     * Parses the date in long format.
     *
     * @param strDate the str date
     * @return the date
     */
    public static Date parseDateInLongFormat(String strDate) {
        return StringUtils.isEmpty(strDate) ? null : parseDate(strDate, YYYY_MM_DD);
    }

    /**
     * Parses the date in short format.
     *
     * @param strDate the str date
     * @return the date
     */
    public static Date parseDateInShortFormat(String strDate) {
        return StringUtils.isEmpty(strDate) ? null : parseDate(strDate, YYYYMMDD);
    }

    /**
     * 按照format格式解析strDate。返回值会truncate掉时分秒数据。.
     *
     * @param strDate the str date
     * @param format the format
     * @return the date
     */
    public static Date parseDate(String strDate, String format) {
        return parseDate(strDate, format, null);
    }

    /**
     * 按照format格式解析strDate。返回值会truncate掉时分秒数据。.
     *
     * @param strDate the str date
     * @param format the format
     * @param defaultValue the default value
     * @return the date
     */
    public static Date parseDate(String strDate, String format, Date defaultValue) {
        try {
            Date date = parseTime(strDate, format);
            date = DateUtils.truncate(date, Calendar.DATE);
            return date;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * author:yaoheng.
     *
     * @param strDate the str date
     * @param days the days
     * @return String
     */
    public static String addDateWithDays(String strDate, int days) {
        if (StringUtils.isEmpty(strDate)) return "";
        Date date = parseDateInLongFormat(strDate);
        date = DateUtils.addDays(date, days);
        return getDateInLongFormat(date);
    }

    /**
     * author:yaoheng.
     *
     * @param date the date
     * @param days the days
     * @return Date
     */
    public static Date addDateWithDays(Date date, int days) {
        if (date == null) return null;
        return DateUtils.addDays(date, days);
    }

    /**
     * 算出两个日期间的天数，包含头尾，传入的日期顺序为从小到大。.
     *
     * @param strDateFrom the str date from
     * @param strDateTo the str date to
     * @return int
     */
    public static int daysBetweenTwoDate(String strDateFrom, String strDateTo) {
        // 异常判断处理
        if (StringUtils.isEmpty(strDateFrom) || StringUtils.isEmpty(strDateTo)) return 0;

        return daysBetweenTwoDate(parseDateInLongFormat(strDateFrom), parseDateInLongFormat(strDateTo));
    }

    /**
     * 算出两个日期间的天数，包含头尾，传入的日期顺序为从小到大。.
     *
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return int
     */
    public static int daysBetweenTwoDate(Date dateFrom, Date dateTo) {
        // 异常判断处理
        if (dateFrom == null || dateTo == null) return 0;

        //
        int m = (int) (dateFrom.getTime() / MILLISECONDS_OF_A_DAY);
        int n = (int) (dateTo.getTime() / MILLISECONDS_OF_A_DAY);
        return n - m + 1; //
    }

    /**
     * Days between two date by seconds.
     *
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the integer
     */
    public static Integer daysBetweenTwoDateBySeconds(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null) return null;

        long from = dateFrom.getTime();
        long to = dateTo.getTime();
        return (int) ((to - from) / MILLISECONDS_OF_A_DAY);
    }

    /**
     * Days between two date by seconds to double.
     *
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the double
     */
    public static Double daysBetweenTwoDateBySecondsToDouble(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null) return null;

        long from = dateFrom.getTime();
        long to = dateTo.getTime();
        return ((double) (to - from) / MILLISECONDS_OF_A_DAY);
    }

    /**
     * Times between two date by seconds.
     *
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the string
     * @Description: 时间差值计算，返回“xx天xx小时xx小时”或“xxdxxhxxm”格式
     * 按照国际化的形式，输出。
     * @Author duo.tao
     * @Date 2018/7/23 13:53
     */
    public static String timesBetweenTwoDateBySeconds(Date dateFrom, Date dateTo) {
        if (dateFrom == null || dateTo == null) return null;

        long from = dateFrom.getTime();
        long to = dateTo.getTime();
        long secondTotal = (to - from) / MILLISECONDS_OF_A_SECOND;
        int days = (int) (secondTotal / SECONDS_OF_A_DAY);
        int hours = (int) ((secondTotal % SECONDS_OF_A_DAY) / SECONDS_OF_AN_HOUR);
        int minutes = (int) (secondTotal % SECONDS_OF_A_DAY % SECONDS_OF_AN_HOUR / SECONDS_OF_A_MINUTE);
        String pattern = "%s天%s时%s分";
        return String.format(pattern, days, hours, minutes); // days + "天" + hours + "时" + minutes + "分";
    }

    /**
     * Gets the long date from seconds.
     *
     * @param time the time
     * @return String
     */
    public static String getLongDateFromSeconds(long time) {
        return getLongDateFromMilliSeconds(time * MILLISECONDS_OF_A_SECOND); // long 1470808850
    }

    /**
     * Gets the long date from seconds.
     *
     * @param time the time
     * @return the long date from seconds
     */
    public static String getLongDateFromSeconds(String time) {
        return DataUtil.isNullOrEmpty(time) ? "" : getLongDateFromSeconds(Long.parseLong(time)); // long 1470808850
    }

    /**
     * Gets the seconds from long date.
     *
     * @param time the time
     * @return the seconds from long date
     */
    public static String getSecondsFromLongDate(String time) {
        if (DataUtil.isNullOrEmpty(time)) return "";
        Date date = parseDate(time, YYYY_MM_DD_HH_MM_SS);
        return date == null ? "" : String.valueOf(date.getTime());
    }

    /**
     * Gets the long date from milli seconds.
     *
     * @param milliseconds the milliseconds
     * @return String
     */
    public static String getLongDateFromMilliSeconds(long milliseconds) {
        return getTimeInLongFormat(new Date(milliseconds)); // long 1470808850999
    }

    /**
     * 日期范围判断.
     *
     * @param theDate the the date
     * @param fromDate the from date
     * @param toDate the to date
     * @return boolean
     */
    public static boolean dateBetween(String theDate, String fromDate, String toDate) {
        // 异常判断处理
        if (StringUtils.isEmpty(theDate) || StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) return false;

        return fromDate.compareTo(theDate) <= 0 && theDate.compareTo(toDate) <= 0;
    }

    /**
     * 时间累加
     * author yin.chen
     *
     * @param date
     *            当前日期 格式必须为 yyyyMMddHHmmss
     * @param hour
     *            累加的时间 单位是小时
     * @return String
     */
    public static String dateAddHour(String date, String hour) {
        double addHour = hour == null ? 1 : Double.parseDouble(hour);
        Long seconds = (long) (addHour * SECONDS_OF_AN_HOUR);
        return dateAddSecond(date, seconds);
    }

    /**
     * 时间累加
     * author yin.chen
     *
     * @param date
     *            当前日期 格式必须为 yyyyMMddHHmmss
     * @param hour
     *            累加的时间 单位是小时
     * @return String
     */
    public static String dateAddHour(String date, Integer hour) {
        long addHour = hour == null ? 1 : hour;
        Long seconds = addHour * SECONDS_OF_AN_HOUR;
        return dateAddSecond(date, seconds);
    }

    /**
     * 时间累加
     * author yin.chen
     *
     * @param date
     *            当前日期 格式必须为 yyyyMMddHHmmss
     * @param second
     *            累加的时间 单位是秒
     * @return String
     */
    public static String dateAddSecond(String date, Long second) {
        try {
            return new SimpleDateFormat(YYYYMMDDHHMMSS).format(new Date((new SimpleDateFormat(YYYYMMDDHHMMSS).parse(date).getTime() + second * 1000)));
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * Convert time format long to short.
     *
     * @param strlongtime the strlongtime
     * @return the string
     */
    public static String convertTimeFormatLongToShort(String strlongtime) {
        if (StringUtils.isEmpty(strlongtime)) { return ""; }
        //
        return strlongtime.replaceAll("-|\\s|:", "");
    }

    /**
     * Convert time format short to long.
     *
     * @param strshorttime the strshorttime
     * @return the string
     */
    public static String convertTimeFormatShortToLong(String strshorttime) {
        if (StringUtils.isEmpty(strshorttime)) { return ""; }
        if (strshorttime.matches("^\\d{8}$")) {
            strshorttime += "000000";
        }
        if (strshorttime.length() != 14) {
            LOGGER.error("字符串长度有误，应该为14！{}", strshorttime);
            return strshorttime;
        }
        if (!strshorttime.matches("^\\d{14}$")) {
            LOGGER.info("时间串格式有误，必须是14位数字！{}", strshorttime);
            return strshorttime;
        }
        //
        StringBuilder sb = new StringBuilder(strshorttime);
        sb.insert(12, ":").insert(10, ":").insert(8, " ").insert(6, "-").insert(4, "-");
        return sb.toString();
    }

    /**
     * 获取最小的日期.
     *
     * @param strDates the str dates
     * @return String
     */
    public static String min(String... strDates) {
        String minDate = strDates[0];
        for (String strDate : strDates) {
            if (minDate == null) {
                minDate = strDate;
                continue;
            }
            if (strDate == null) continue;
            if (minDate.compareTo(strDate) > 0) minDate = strDate;
        }
        return minDate;
    }

    /**
     * 比较得到大的日期，如果为空则表示小。.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the date
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.compareTo(date2) > 0 ? date1 : date2;
    }

    /**
     * 获取最大的日期.
     *
     * @param strDates the str dates
     * @return String
     */
    public static String max(String... strDates) {
        String maxDate = strDates[0];
        for (String strDate : strDates) {
            if (maxDate == null) {
                maxDate = strDate;
                continue;
            }
            if (strDate == null) continue;
            if (maxDate.compareTo(strDate) < 0) maxDate = strDate;
        }
        return maxDate;
    }

    /**
     * 判断两时间的自然天数.
     *
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the integer
     */
    @SuppressWarnings("deprecation")
    public static Integer daysBetweenTwoDateBySecondsToInteger(Date dateFrom, Date dateTo) {
        Integer hours = dateFrom.getHours();
        Integer minutes = dateFrom.getMinutes();
        Integer seconds = dateFrom.getSeconds();
        dateFrom.setHours(0);
        dateFrom.setMinutes(0);
        dateFrom.setSeconds(0);
        long from = dateFrom.getTime();
        long to = dateTo.getTime();
        dateFrom.setHours(hours);
        dateFrom.setMinutes(minutes);
        dateFrom.setSeconds(seconds);
        return (int) ((to - from) / MILLISECONDS_OF_A_DAY);

    }

    /**
     * 检查时间格式是否正确.
     *
     * @param str the str
     * @param format the format
     * @return true, if is valid date
     */
    public static boolean isValidDate(String str, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取当前时间的中文年月
     * @return
     */
    public static String getCurrentYearAndMonthOfCN() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%d年%d月",calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1);
    }
}
