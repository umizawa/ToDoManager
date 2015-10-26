package com.example.naoya.todomanager;

import java.util.Calendar;
import java.util.Date;

public class dateConverter {
    public static String getDateString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.valueOf(year) + "年" +
                String.valueOf(month) + "月" +
                String.valueOf(day) + "日";
    }
    public static String getDateString(Calendar calendar){
        return String.valueOf(calendar.get(Calendar.HOUR)) + "年" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "月" +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "日");
    }

    public static String getTimeString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.valueOf(hour) + "時" +
                String.valueOf(minute) + "分";
    }

    public static String getTimeString(Calendar calendar){
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "時" +
                String.valueOf(calendar.get(Calendar.MINUTE)) + "分";
    }

    public static String getDateTimeString(Calendar calendar){
        return String.valueOf(calendar.get(Calendar.YEAR)) + "年" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "月" +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "日" + " " +
                String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "時" +
                String.valueOf(calendar.get(Calendar.MINUTE)) + "分";
    }

    public static String getDateTimeString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR)) + "年" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "月" +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "日" + " " +
                String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "時" +
                String.valueOf(calendar.get(Calendar.MINUTE)) + "分";
    }
}
