package com.newsoft.super_note.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

    @SuppressLint("SetTextI18n")
    public static void setTimetv(TextView textView, String time) {
        try {
            long time_total = 0;
            time_total = getTimeNow() - getTimeToString(time);
//        Log.e("getTimeToString",""+getTimeToString(time));
            String set_times = "";

            if (time_total < 3600) {
                // phút:giây
                set_times = secondsToString((int) time_total);
                String minute[] = set_times.split(":");

                if (minute[0].equals("00"))
                    textView.setText("vừa xong");
                else
                    textView.setText(Integer.valueOf(minute[0]) + " phút trước");
            } else if (time_total >= 3600 && time_total < 86400) {
                // giờ:phút
                set_times = secondsToString((int) (time_total) / 60);
                String hours[] = set_times.split(":");

                textView.setText(Integer.valueOf(hours[0]) + " giờ trước");
            } else if (time_total >= 86400) {
//                Log.e("time",""+time);
//            Log.e("convertDateUtcToStringtime",convertDateUtcToString(time));

                String day[] = convertDateUtcToString(time).substring(6, 16).split("/");

                if (time_total < 172800)  // 1 ngày
                    textView.setText("hôm qua");
                else if (time_total >= 172800)
                    // trên 2 ngày, ngày trong tuần
                    textView.setText("Thứ " + getDay(time) + ", " + Integer.valueOf(day[0]) + " Thg " + +Integer.valueOf(day[1]));
//            else if (time_total >= 604800)
//                // trên 1 tuần, ngày trong tháng
//                textView.setText(Integer.valueOf(day[0]) + " Thg "+ +Integer.valueOf(day[1]));
                else if (time_total >= 2592000)
                    // trên 1 năm, ngày trong năm
                    textView.setText(Integer.valueOf(day[0]) + " Thg" + +Integer.valueOf(day[1]) + ", " + day[2]);

//            Log.e("textView",""+day[0] +" "+ day[1]);
//            Log.e("năm",""+Integer.valueOf(day[0]) + " Thg "+ +Integer.valueOf(day[1])+", "+day[2]);
//            Log.e("tuần",""+Integer.valueOf(day[0]) + " Thg "+ +Integer.valueOf(day[1]));
            }
//        else if (time_total >= 604800){
//            // ngày trong tháng
//            String day [] = convertDateUtcToString(time).substring(7,11).split("/");
//
//            textView.setText(Integer.valueOf(day[0]) + " Thg "+ +Integer.valueOf(day[1]));
//        }else if (time_total >= 2592000){
//            // tháng
//        }
        } catch (Exception e) {

        }
    }

    // lấy thứ trong tuần
    public static String getDay(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateToString(time));
        return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static long getTimeToString(String st_time) {
        // lấy thời gian theo string dạng long
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.toLocalizedPattern();     // lấy giờ theo vị trí
        try {
            Date time = df.parse(st_time);
            return (time.getTime() / 1000);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long getTimeToStringHours(String st_time) {
        // lấy thời gian theo string dạng long
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.toLocalizedPattern();     // lấy giờ theo vị trí
        try {
            Date time = df.parse(st_time);
            return (time.getTime() / 1000);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static long getTimeNow() {
        // lây thời gian hiện tại dạng long
        Date date = new Date(System.currentTimeMillis());
        return (date.getTime() / 1000);
    }

    public static String secondsToString(int seconds) {
        // convert giờ:phút theo int
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    // lấy thời gian utc string sang date string
    public static String convertDateUtcToString(String Date) {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        String converted_date = "";
        try {
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            utcFormat.toLocalizedPattern();
            Date date = utcFormat.parse(Date);

            DateFormat currentTFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            currentTFormat.setTimeZone(TimeZone.getTimeZone(tz.getID()));

            converted_date = currentTFormat.format(date);
        } catch (Exception e) {
            Log.e("convertDateUtcToString", "error" + e.getMessage());
        }

        return converted_date;
    }

    //        // lấy thời gian từ utc string sang date
    public static Date getDateToString(String time) {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        String converted_date = "";
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        utcFormat.toLocalizedPattern();
        try {
            return utcFormat.parse(time);
        } catch (ParseException e) {
            Log.e("getDateToString", "error" + e.getMessage());
            return null;
        }
    }
}
