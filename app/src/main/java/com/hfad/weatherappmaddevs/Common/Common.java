package com.hfad.weatherappmaddevs.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
//    public static boolean forecast_by_city=false;
//    public static String cityName=null;
    public static final String API_ID ="b23a5affdcb913db5545a9406398a451";
    public static Location current_location=null;
    public static final double PRESSURE = 0.75006375541921;
    public static boolean isCitySelected=false;
    public static String cityName=null;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }
}
