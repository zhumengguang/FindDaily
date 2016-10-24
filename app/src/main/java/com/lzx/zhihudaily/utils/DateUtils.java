package com.lzx.zhihudaily.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lzx on 2016/10/21.
 * 功能：
 */

public class DateUtils {

    /**
     * 获取指定年份月份中指定某天是星期几
     */
    public static String getDayOfweek(String date) {
        String result = "";
        try {
            String day = date.substring(6, 8);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            switch (dayOfWeek) {
                case 1:
                    result = "星期日";
                    break;
                case 2:
                    result = "星期一";
                    break;
                case 3:
                    result = "星期二";
                    break;
                case 4:
                    result = "星期三";
                    break;
                case 5:
                    result = "星期四";
                    break;
                case 6:
                    result = "星期五";
                    break;
                case 7:
                    result = "星期六";
                    break;
                default:
                    result = "";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatDate(String date) {
        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static String getTime(long date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        Long time = new Long(date);
        String d = format.format(time);
        return d;
    }
}
