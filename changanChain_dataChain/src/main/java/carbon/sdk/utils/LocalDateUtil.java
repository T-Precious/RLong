package carbon.sdk.utils;


import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2024/7/9 14:29
 * @Version 1.0
 **/
public class LocalDateUtil {
    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "yyyyMMdd";
    public static final String DATE_FORMAT_3 = "yyyy/MM/dd";
    public static final String DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_2 = "yyyyMMddHHmmss";
    public static final String DATE_TIME_FORMAT_3 = "yyyy/MM/dd HH:mm:ss";
    public static final String TIME_FORMAT_1 = "HH:mm:ss";

    public LocalDateUtil() {
    }

    public static String nowDate() {
        return nowDate("yyyy-MM-dd");
    }

    public static String nowDateTime() {
        return nowDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String nowDate(String dateFormateStr) {
        return DateTimeFormatter.ofPattern(dateFormateStr).format(LocalDateTime.now());
    }

    public static String formartDate(String dateStr, String oldFormart, String newFormart) {
        return !StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(oldFormart) && !StringUtils.isEmpty(newFormart) ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(oldFormart)).format(DateTimeFormatter.ofPattern(newFormart)) : "";
    }

    public static LocalDate strToDate(String dateStr, String dateFormateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormateStr));
    }

    public static LocalDate strToDate(String dateStr) {
        return strToDate(dateStr, "yyyy-MM-dd");
    }

    public static LocalDateTime strToDateTime(String dateStr, String dateFormateStr) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(dateFormateStr));
    }

    public static LocalDateTime strToDateTime(String dateStr) {
        return strToDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToStr(LocalDate date, String dateFormateStr) {
        return date.format(DateTimeFormatter.ofPattern(dateFormateStr));
    }

    public static String dateToStr(LocalDate date) {
        return dateToStr(date, "yyyy-MM-dd");
    }

    public static String dateTimeToStr(LocalDateTime date, String dateFormateStr) {
        return date.format(DateTimeFormatter.ofPattern(dateFormateStr));
    }

    public static String dateTimeToStr(LocalDateTime date) {
        return dateTimeToStr(date, "yyyy-MM-dd HH:mm:ss");
    }
}
