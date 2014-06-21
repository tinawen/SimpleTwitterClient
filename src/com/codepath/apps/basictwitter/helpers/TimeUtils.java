package com.codepath.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tina on 6/20/14.
 */
public class TimeUtils {
    private final static long ONE_SECOND = 1000;
    private final static long SECONDS = 60;

    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long MINUTES = 60;

    private final static long ONE_HOUR = ONE_MINUTE * 60;
    private final static long HOURS = 24;

    private final static long ONE_DAY = ONE_HOUR * 24;
    private final static long ONE_WEEK = ONE_DAY * 7;
    private final static long ONE_YEAR = ONE_DAY * 365;

    private TimeUtils() {
    }

    private static String durationBasedOnUnit(long duration, long unit, String unitPlaceHolder) {
        if (duration >= unit) {
            return duration / unit + unitPlaceHolder;
        }
        return null;
    }
    public static String millisToLongDHMS(long duration) {
        String res = durationBasedOnUnit(duration, ONE_YEAR, "y");
        if (res != null) {
            return res;
        }
        res = durationBasedOnUnit(duration, ONE_WEEK, "w");
        if (res != null) {
            return res;
        }
        res = durationBasedOnUnit(duration, ONE_DAY, "d");
        if (res != null) {
            return res;
        }
        res = durationBasedOnUnit(duration, ONE_HOUR, "h");
        if (res != null) {
            return res;
        }
        res = durationBasedOnUnit(duration, ONE_MINUTE, "m");
        if (res != null) {
            return res;
        }
        return duration / ONE_SECOND + "s";
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.getDefault());
        sf.setLenient(true);

        String dateString = null;
        try {
            Long dateMillis = sf.parse(rawJsonDate).getTime();
            Long nowDateMillis = new Long((new Date()).getTime());
            dateString = millisToLongDHMS(Math.max(0, nowDateMillis - dateMillis));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

}
