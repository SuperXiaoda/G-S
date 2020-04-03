package com.juda.gs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @description: 时间工具
 * @author: CodingDa
 * @date : 2019/12/6 14:34
 */
public class TimeUtil {
    // 标准时间格式
    private static SimpleDateFormat DATE_FORMAT_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private static SimpleDateFormat MMdd = new SimpleDateFormat("MM-dd", Locale.getDefault());

    /**
     * Description: 获取日期 yyyy-MM-dd
     * author: CodingHornet
     * Date: 2017/11/23 15:07
     */
    public static String getDate(Date date) {
        return yyyyMMdd.format(date);
    }

    /**
     * Description: 获取时间 MM-dd HH:mm
     * author: CodingHornet
     * Date: 2017/3/15 14:53
     */
    public static String getDate() {
        SimpleDateFormat formatBuilder = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
        return formatBuilder.format(new Date());
    }

    /**
     * Description: 获取时间(yyyy-MM-dd HH:mm:ss)
     * author: CodingHornet
     * Date: 2017/3/15 14:54
     */
    public static String getTime() {
        return DATE_FORMAT_yyyyMMddHHmmss.format(new Date());
    }

    /**
     * Description: 获取时间(yyyy-MM-dd HH:mm:ss)
     * author: CodingHornet
     * Date: 2017/3/15 14:54
     */
    public static String getTime(Date date) {
        return DATE_FORMAT_yyyyMMddHHmmss.format(date);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str HH:mm
     */
    public static String DateToHourString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str MM-dd HH:mm
     */
    public static String DateToMonthString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 获取昨天时间范围
     */
    public static String getLastDayRang() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        String sdf = yyyyMMdd.format(date);

        return sdf + " 00:00~23:59";
    }

    /**
     * 获取近七天时间范围
     */
    public static String getSevenDayRang() {
        Calendar sevenDay = Calendar.getInstance();
        sevenDay.add(Calendar.DATE, -7);
        Date sevenDate = sevenDay.getTime();
        String sevenStr = MMdd.format(sevenDate);

        Calendar lastDay = Calendar.getInstance();
        lastDay.add(Calendar.DATE, -1);
        Date lastDate = lastDay.getTime();
        String lastStr = MMdd.format(lastDate);


        return sevenStr + " 00:00 ~ " + lastStr + " 23:59";
    }

    /**
     * 获取近三十天时间范围
     *
     * @return str MM-dd HH:mm
     */
    public static String getThirtyDayRang() {
        Calendar thirtyDay = Calendar.getInstance();
        thirtyDay.add(Calendar.DATE, -30);
        Date thirtyDate = thirtyDay.getTime();
        String thirtyStr = MMdd.format(thirtyDate);

        Calendar lastDay = Calendar.getInstance();
        lastDay.add(Calendar.DATE, -1);
        Date lastDate = lastDay.getTime();
        String lastStr = MMdd.format(lastDate);


        return thirtyStr + " 00:00 ~ " + lastStr + " 23:59";
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        return DATE_FORMAT_yyyyMMddHHmmss.format(new Date(Long.valueOf(seconds)));
    }


}
