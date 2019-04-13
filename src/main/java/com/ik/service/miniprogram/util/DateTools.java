package com.ik.service.miniprogram.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ik.crm.commons.util.DateUtils;

/**
 * @author Yinkl
 */
public class DateTools {

    /**
     * 1天的毫秒数
     */
    public static final long oneDay = 1000 * 3600 * 24;

    /**
     * 默认不拨打时间段
     */
    public static final String SILENCE_TIME = "00:00:00~00:00:00";

    /**
     * @return yyyy-MM-dd
     */
    public static String getSystemDate() {
        return formatToDate(new Date());
    }

    /**
     * @return HH:mm:ss
     */
    public static String getSystemTime() {
        return formatToTime(new Date());
    }

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getSystemDateTime() {
        return formatToDateTime(new Date());
    }

    /**
     * @param date
     * @return yyyy-MM-dd
     */
    public static String formatToDate(Date date) {
        if (date != null) {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            return ymd.format(date);
        }
        return null;
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatToDateTime(Date date) {
        if (date != null) {
            SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return ymdhms.format(date);
        }
        return null;
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatToDateTime(long date) {
        SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ymdhms.format(date);
    }

    /**
     * @param date
     * @return HH:mm:ss
     */
    public static String formatToTime(Date date) {
        if (date != null) {
            SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
            return hms.format(date);
        }
        return null;
    }

    /**
     * 判断是不是常规的日期类型
     *
     * @param date
     * @return
     */
    public static boolean isStringDate(String date) {
        if (date != null) {
            try {
                date = date.replace("/", "-");
                if (date.length() >= 6 && date.length() <= 10) {
                    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                    ymd.parse(date);
                    return true;
                }
            } catch (Exception e) {
            }

            try {
                date = date.replace("/", "-");
                if (date.length() >= 12 && date.length() <= 19) {
                    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    ymdhms.parse(date);
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }


    /**
     * 根据字符串长度自动转换成日期
     *
     * @param date
     * @return Date
     */
    public static Date parseToDate(String date) {
        if (date != null) {
            try {
                date = date.trim();
                if (date.length() == 6) {
                    return new SimpleDateFormat("yyyyMM").parse(date);
                } else if (date.length() == 7) {
                    SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
                    return ym.parse(date);
                } else if (date.length() == 8) {
                    if (date.contains(":")) {
                        return new SimpleDateFormat("HH:mm:ss").parse(date);
                    }
                    return new SimpleDateFormat("yyyyMMdd").parse(date);
                } else if (date.length() == 10) {
                    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
                    return ymd.parse(date);
                } else if (date.length() == 14) {
                    return new SimpleDateFormat("yyyyMMddHHmmss").parse(date);
                } else if (date.length() == 15) {
                    return new SimpleDateFormat("yyyyMMdd HHmmss").parse(date);
                } else if (date.length() == 19) {
                    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return ymdhms.parse(date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 得到一个日期的年份
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static String getDateYear(String strDate) throws ParseException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(ymd.parse(strDate));
        return String.valueOf(date.get(Calendar.YEAR));
    }

    /**
     * 得到一个日期的月份
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static String getDateMonth(String strDate) throws ParseException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(ymd.parse(strDate));
        return String.valueOf(date.get(Calendar.MONTH) + 1);
    }

    /**
     * 得到一个日期的天数
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static String getDateDay(String strDate) throws ParseException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(ymd.parse(strDate));
        return String.valueOf(date.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 计算两个日期之间的差的月数。 </br> 月付时：1月31号第一期，</br> 则第二期是2月28号或者29号。 </br>
     * 这个需要判断，不能直接加一个月，否则会变成3月2号的</br>
     *
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static int getDiffMonth(Date beginDate, Date endDate) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        int beginYear = calendar.get(Calendar.YEAR);
        int beginMonth = calendar.get(Calendar.MONTH);
        calendar.setTime(endDate);
        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH);
        int difMonth = (endYear - beginYear) * 12 + (endMonth - beginMonth);
        return difMonth;
    }

    /**
     * 返回两时间间隔天数
     *
     * @param startDate 开始时间字符串 2013-03-05
     * @param endDate   结束时间字符串 2013-03-07
     * @return 2
     */
    public static long getDateDiff(String startDate, String endDate) {
        try {
            Date sd = parseToDate(startDate);
            Date ed = parseToDate(endDate);
            return getDateDiff(sd, ed);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 返回两时间间隔天数
     *
     * @param startDate 开始时间 2013-03-05 23:59:59
     * @param endDate   结束时间 2013-03-07 00:00:01
     * @return 2
     */
    public static long getDateDiff(Date startDate, Date endDate) {
        try {
            long start = parseToDate(formatToDate(startDate)).getTime();
            long end = parseToDate(formatToDate(endDate)).getTime();
            return (end - start) / 86400000;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取一个日期所在的年份的第一天
     *
     * @param date 2013-03-05
     * @return 2013-01-01
     */
    public static String getYearFirstDay(String date) {
        try {
            String year = date.substring(0, 4);
            return year + "-01-01";
        } catch (Exception e) {
            return date;
        }
    }

    /**
     * 获取一个日期所在的月份的第一天
     *
     * @param date 2013-03-05
     * @return 2013-03-01
     */
    public static String getMonthFirstDay(String date) {
        try {
            SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
            String year = ym.format(parseToDate(date));
            return year + "-01";
        } catch (Exception e) {
            return date;
        }
    }

    /**
     * 根据前台传递参数获取一个时间段
     *
     * @param type
     * @return
     */
    public static Map getBetweenTimeStr(String type) {
        Map<String, String> hashMap = new HashMap<>();
        if (type == null) {
            return hashMap;
        }
        long begin = 0, end = 0;
        switch (type) {
            case "today":
                begin = getTimesmorning(0);
                end = getTimesnight(0);
                break;
            case "yesterday":
                // 往前推一天
                begin = getTimesmorning(-1);
                end = getTimesnight(-1);
                break;
            case "this_week":
                begin = getTimesWeekmorning(0);
                end = getTimesWeeknight(0);
                break;
            case "last_week":
                begin = getTimesWeekmorning(-1);
                end = getTimesWeeknight(-1);
                break;
            case "this_month":
                begin = getTimesMonthmorning(0);
                end = getTimesMonthnight(0);
                break;
            case "last_month":
                begin = getTimesMonthmorning(-1);
                end = getTimesMonthnight(-1);
                break;
            default:
                if (type.contains(",")) {
                    String[] timeAry = type.split(",");
                    if (timeAry.length == 2) {
                        begin = DateTools.parseToDate(timeAry[0]).getTime();
                        end = DateTools.parseToDate(timeAry[1]).getTime() + 24 * 60 * 60 * 1000;
                    }
                }
                break;

        }
        if (begin != 0 && end != 0) {
            hashMap.put("start_time", formatToDateTime(begin));
            hashMap.put("end_time", formatToDateTime(end));
        }
        return hashMap;
    }


    /**
     * 获得当天0点时间
     *
     * @param dayLater 延迟几天，提前请用负数
     * @return
     */
    public static long getTimesmorning(int dayLater) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() + dayLater * oneDay;
    }


    /**
     * 获得当天24点时间
     *
     * @param dayLater 延迟几天，提前请用负数
     *                 -1000:往前调1s
     * @return
     */
    public static long getTimesnight(int dayLater) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime() + dayLater * oneDay - 1000;
    }


    /**
     * 获得本周一0点时间
     *
     * @param weekLater 延迟几周，提前请用负数
     * @return
     */
    public static long getTimesWeekmorning(int weekLater) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime().getTime() + weekLater * oneDay * 7;
    }


    /**
     * 获得本周日24点时间
     *
     * @param weekLater 延迟几周，提前请用负数
     *                  -1000:往前调1s
     * @return
     */
    public static long getTimesWeeknight(int weekLater) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(getTimesWeekmorning(weekLater)));
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime().getTime() - 1000;
    }

    /**
     * 获得本月第一天0点时间
     *
     * @param monthLater 延迟几月，提前请用负数
     * @return
     */
    public static long getTimesMonthmorning(int monthLater) {
        Calendar cal = Calendar.getInstance();
        System.out.println("" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONDAY) + cal.get(Calendar.DAY_OF_MONTH));
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY) + monthLater, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime().getTime();
    }


    /**
     * 获得本月最后一天24点时间
     *
     * @param monthLater 延迟几月，提前请用负数
     *                   -1000 :往前调1s
     * @return
     */
    public static long getTimesMonthnight(int monthLater) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY) + monthLater, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime().getTime() - 1000;
    }

    public static void parseCallTime(Map<String, Object> params, Integer phoneTime) {
        if (phoneTime == null) {
            return;
        }
        Integer start = null;
        Integer end = null;
        switch (phoneTime) {
            case 0:
                start = 1;
                end = 15;
                break;
            case 1:
                start = 16;
                end = 30;
                break;
            case 2:
                start = 31;
                end = 45;
                break;
            case 3:
                start = 46;
                end = 60;
                break;
            case 4:
                start = 61;
                end = 120;
                break;
            case 5:
                start = 121;
                break;
            // 未接通的时候还会包含一种未拨打的情况将通话时间设置为null
            case 6:
                start = 0;
                end = 0;
                break;
            case 7:
                start = 1;
                break;
            case 8:
                params.put("isNotCall", true);
                break;
            default:
                return;
        }
        params.put("call_time_begin", start);
        params.put("call_time_end", end);
    }

    /**
     * 根据日期生成对应的corn
     *
     * @param date
     * @return
     */
    public static String getCornByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        String corn = "" + second + " " + minute + " " + hour + " " + day + " " + month + " ? " + year;
        return corn;
    }

    /**
     * 返回调整之后的时间
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Date addTime(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取某个时间在1970年的对应时分秒份long值
     *
     * @return
     */
    public static Date getTimeMil(Date date) {
        try {
            String dateStr = formatToDateTime(date).substring(11);
            return new SimpleDateFormat("HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取两个日期间的所有日期
     *
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getBetweenDates(Date start, Date end) {

        List<Date> list = new ArrayList<>();

        Date date = start;
        Calendar cd = Calendar.getInstance();
        while (date.getTime() <= end.getTime()) {
            list.add(date);
            cd.setTime(date);
            // 增加一天 放入集合
            cd.add(Calendar.DATE, 1);
            date = cd.getTime();
        }

        return list;
    }

    /**
     * 获取日期的年/月/日
     *
     * @param date
     */
    public static int parseDateToYearMonthDay(Date date, int calendarType) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendarType == Calendar.MONTH) {
            return calendar.get(calendarType) + 1;
        } else {
            return calendar.get(calendarType);
        }
    }


    /**
     * 根据年月日 + 时分秒 获取date
     * @param date
     * @param time
     * @return
     */
    public static Date getDateByDateAndTime(String date, String time) {

        return parseToDate(date + " " + time);
    }

    /**
     * 获取较大日期
     * @param d1
     * @param d2
     * @return
     */
    public static Date getMaxDate(Date d1, Date d2) {
        if (d1.compareTo(d2) > 0) {
            return d1;
        }
        return d2;
    }

    /**
     * 获取较小日期
     * @param d1
     * @param d2
     * @return
     */
    public static Date getMinDate(Date d1, Date d2) {
        if (d1.compareTo(d2) > 0) {
            return d2;
        }
        return d1;
    }

    public static Date getUseAbleTime(Date time) {
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        Date useAbleTime = DateUtils.parseDate(sd.format(time), "HH:mm:ss");
        return useAbleTime;
    }

}

