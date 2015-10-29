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
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "月" +
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
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "月" +
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
        int importance;
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

    public static String getImportanceString(int importance){
        String string;
        switch (importance){
            case 0:
                string = "低";
                break;
            case 1:
                string = "中";
                break;
            case 2:
                string = "高";
                break;
            default:
                string = "低";
                break;
        }
        return string;
    }

    public static String getRepeatFlagString(boolean repeatFlag){
        String string;
        if (repeatFlag){
            string = "する";
        } else {
            string = "しない";
        }
        return string;
    }
}
