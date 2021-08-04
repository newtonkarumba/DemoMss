package com.systech.mss.seurity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static LocalDateTime getLocalDateTime(long timestamp) {
        if (timestamp == 0) return LocalDateTime.now();
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                .getDefault().toZoneId());
    }

    public static Date from(String pattern, String fromDate) {
        try {
            return new SimpleDateFormat(pattern).parse(fromDate);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String date(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
    }

    public static String shortDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, uuuu"));
    }

    public static String shortDateOfLocalDate(LocalDate localDate) {
        if (localDate == null) return "";
        return localDate.format(DateTimeFormatter.ofPattern("MMM dd, uuuu"));
    }

    public static String shortDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    public static String shortDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
        try {
            Date date1 = simpleDateFormat.parse(date);
            return simpleDateFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String shortDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getTimestamp() {
        return ZonedDateTime                 // Represent a moment as perceived in the wall-clock time used by the people of a particular region ( a time zone).
                .now(                            // Capture the current moment.
                        ZoneId.of("Africa/Tunis")  // Specify the time zone using proper Continent/Region name. Never use 3-4 character pseudo-zones such as PDT, EST, IST.
                )                                // Returns a `ZonedDateTime` object.
                .format(                         // Generate a `String` object containing text representing the value of our date-time object.
                        DateTimeFormatter.ofPattern("uuuuMMddHHmmss")
                );
    }

    public static long getMillitime() {
        return System.currentTimeMillis();
    }

    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception ignored) {
        }
        return "";
    }

    public static String formatDate(String date, String pattern) {
        try {
            Date date1 = new Date(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date1);
        } catch (Exception ignored) {
        }
        return "";
    }

    public static String formatDate(long timeStamp, String pattern) {
        try {
            Timestamp ts = new Timestamp(timeStamp);
            Date date = new Date(ts.getTime());
            return DateUtils.formatDate(date, pattern);
        } catch (Exception ignored) {
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(getTimestamp());
    }

}