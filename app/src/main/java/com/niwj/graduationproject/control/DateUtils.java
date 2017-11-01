package com.niwj.graduationproject.control;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prince70 on 2017/10/31.
 */

public class DateUtils {

    private static final java.lang.String TAG = "DateUtils";

    public static final String EN_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String EN_SHORT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认模版显示日期
     *
     * @param pattern
     * @return
     */
    public static String textForNow(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 自定义模版显示日期
     *
     * @param pattern
     * @return
     */
    public static String textForNow(String pattern, Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 今天前dayCount天
     *
     * @param dayCount
     * @return
     */
    public static String textBeforeNow(int dayCount) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(d.getTime() - dayCount * 24 * 60 * 60 * 1000));
    }

    /**
     * day前dayCount天
     *
     * @param dayCount
     * @return
     */
    public static String textBeforeDay(String day, int dayCount) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(stringToDate("yyyy-MM-dd", day).getTime() - dayCount * 24 * 60 * 60 * 1000));
    }

    /**
     * 获取当前时间的一年前的今天
     *
     * @return
     */
    public static String textLastYearOfDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        return year;
    }

    /**
     * 判断是不是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 自定义模版显示日期
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String textForDate(String pattern, String date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format = null;
        try {
            format = dateFormat.format(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 根据字符串转换为日期
     *
     * @param pattern
     * @param stringDate
     * @return
     */
    public static Date stringToDate(String pattern, String stringDate) {

        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            Log.e(TAG, "日期转换出错 pattern : " + pattern + " stringDate: " + stringDate);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据字符串转换为日期（数据库日期 java.sql.Date）
     *
     * @param pattern
     * @param stringDate
     * @return
     */
    public static java.sql.Date stringToDateDB(String pattern, String stringDate) {

        java.sql.Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            date = (java.sql.Date) dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date转为String，格式为yyyy-MM-dd
     * 2016-11-11
     */
    public static String textForDateAll(Date date) {
        String sdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(date);
        return sdate;
    }


    /**
     * 将Date转为String，格式为yyyy-MM-dd
     * 2016-11-11
     * @param date 如果为空，返回当前时间的格式化日期
     */
    public static String textForDate(Date date) {
        if (date == null) date = new Date();
        String sdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(date);
        String cTime = sdate.substring(0, 10);
        return cTime;
    }

    /**
     * 将Date转为String，格式为HH:mm
     * 2016-11-11
     */
    public static String textForDateForHHmm(Date date) {
        String sdate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        String cTime = sdate.substring(11, 16);
        return cTime;
    }

    /**
     * 将Date转为String，格式为1月10日
     * 2016-11-11
     */
    public static String textForDateForMMdd(Date date) {
        String sdate = (new SimpleDateFormat("yyyy日MM月dd日 HH:mm:ss")).format(date);
        String cTime = sdate.substring(5, 11);
        return cTime;
    }


    /**
     * 根据模版和日期字符串转换为Calendar
     *
     * @param pattern
     * @param stringDate
     * @return
     */
    public static Calendar stringToCalendar(String pattern, String stringDate) {

        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 根据Date转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp getTimestampDate(Date date) {
        return new Timestamp(date.getTime());
    }


    /**
     * 获取当前的日期，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @author LiuQing
     */
    public static String textForNow() {
        String time = textForNow("yyyy-MM-dd HH:mm:ss");
        String cTime = time.substring(0, 19);
        return cTime;
    }

    /**
     * 2016-06-29
     */
    public static String textForNow2() {
        String time = textForNow("yyyy-MM-dd");
        String cTime = time.substring(0, 10);
        return cTime;
    }

    /**
     * 2016/06/29
     */
    public static String textNow() {
        String time = textForNow("yyyy/MM/dd");
        String cTime = time.substring(0, 10);
        return cTime;
    }

    /**
     * 根据日期和日期类型，返回Date
     *
     * @param dateStr
     * @return Timestamp
     */
    public static Date parseStringDate(String dateType, String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据日期和日期类型，返回Timestamp
     *
     * @param dateStr
     * @return Timestamp
     */
    public static Timestamp parseStrDate(String dateType, String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Timestamp date = null;
        try {
            date = getTimestampDate(dateFormat.parse(dateStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据年份返回日期，Date
     *
     * @param year
     * @return
     */
    public static Date calendarAdd(int year) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /**
     * 根据月份返回日期，Date
     *
     * @param month
     * @return
     */
    public static Date calendarAddMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 根据具体日期date返回日期，Date
     *
     * @param
     * @return
     */
    public static Date calendarAddDate(int date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, date);
        return cal.getTime();
    }

    /**
     * 根据月份month 返回日期，Date
     *
     * @param month
     * @return
     */
    public static Date calendarAdd30Date(int month) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, month * 30);
        return cal.getTime();
    }

    /**
     * 根据年份year 返回日期，Date
     *
     * @param year
     * @return 年份
     */
    public static Date calendarAdd365Date(int year) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, year * 365);
        return cal.getTime();
    }

    /**
     * 2011-1-1(format:2011-1-1) to 78378347
     */
    public static Long dateToTime(String date, String format) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.getTime();

    }

    /**
     * 获取某个月的最大日期
     */
    public static int getMonthMaxDay(int year, int month) {
        int[] maxDays = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))   //是闰年，2月为29天
            maxDays[2]++;
        return maxDays[month];
    }

    /**
     * 获取指定日期的星期
     */
    public static String getWeek(int year, int month, int day) {
        String[] weekStr = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return "星期" + weekStr[week - 1];
    }

    /**
     * 日期差,参数是日期，和现在对比
     */
    public static int daysCount(Date date) {
        Date fDate = DateUtils.stringToDate("yyyy-MM-dd", textForNow("yyyy-MM-dd", date));
        Date oDate = DateUtils.stringToDate("yyyy-MM-dd", textForNow("yyyy-MM-dd", new Date()));
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1 + 1;
    }

    /**
     * 日期差，参数是字符串，和现在对比
     * 模板和日期格式对不上
     * yyyy年MM月dd日
     */
    public static int daysCount(String date) {
        Date fDate = DateUtils.stringToDate("yyyy年MM月dd日", date);
        Date oDate = DateUtils.stringToDate("yyyy年MM月dd日", textForNow("yyyy年MM月dd日", new Date()));
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        Log.e("cha", day2 - day1 + 1 + "");
        return day2 - day1 + 1;
    }

    /**
     * 日期差，参数是字符串
     * 格式是yyyy-MM-dd
     */
    public static int daysCountByString(String date){
        Date fDate = DateUtils.stringToDate("yyyy-MM-dd", date);
        Date oDate = DateUtils.stringToDate("yyyy-MM-dd", DateUtils.textForNow("yyyy-MM-dd", new Date()));
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        Log.e("cha", day2 - day1 + 1 + "");
        return day2 - day1 + 1;
    }

    /**
     * 两日期相差天数
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 日期差，返回天数
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try {
            cal.setTime(smdate);
            time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            time2 = cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 两日期对比
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
                return -1;
            } else {
                //相等
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}

