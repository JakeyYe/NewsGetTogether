package com.example.mrye.newsgettogether.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Mr.Ye on 2016/12/5.
 */

public class GetIdUtil {

    public static String getId(int page) {
        String nowString = new SimpleDateFormat("dd").format(new Date());
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);// 当前的年份
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);// 前一个月的月份，月份是从0索引开始的，要获取当前月则要加一
        Calendar c = new GregorianCalendar(nowYear, nowMonth, 0);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 返回当前月的总天数。
        int now = Integer.parseInt(nowString);
        int x = now - page;
        if (x >= 1) {

            if (x >= 10) {
                return new SimpleDateFormat("yyyyMM").format(new Date()) + String.valueOf(x);
            } else {
                return new SimpleDateFormat("yyyyMM").format(new Date()) + "0" + String.valueOf(x);
            }

        } else {
            return String.valueOf(nowYear) + String.valueOf(nowMonth) + String.valueOf(days + x);
        }
    }
}
