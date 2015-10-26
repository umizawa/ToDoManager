package com.example.naoya.todomanager;

import java.util.Calendar;
import java.util.Date;

public class dataConverter {

    public static String getDateString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getDateString(calendar);
    }

    public static String getDateString(Calendar calendar){
        return String.valueOf(calendar.get(Calendar.YEAR)) + "年" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "月" +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "日");
    }

    public static String getTimeString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getTimeString(calendar);
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
        return getDateTimeString(calendar);
    }

    public static int getImportanceInteger(String string){
        int importance = 0;
        switch (string){
            case "低":
                importance = 0;
                break;
            case "中":
                importance = 1;
                break;
            case "高":
                importance = 2;
                break;
            default:
                importance = 0;
                break;
        }
        return importance;
    }
}
