package org.shiroko.ai.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间转换工具类（线程安全）
 */
public class TimeConvertUtils {

    // 默认时间格式（常用：年月日 时分秒）
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 仅日期格式
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * LocalDateTime 转字符串（默认格式：yyyy-MM-dd HH:mm:ss）
     * @param time 时间对象（可为null）
     * @return 格式化后的字符串（null返回null）
     */
    public static String localDateTimeToString(LocalDateTime time) {
        return localDateTimeToString(time, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * LocalDateTime 转字符串（自定义格式）
     * @param time 时间对象（可为null）
     * @param pattern 格式（如"yyyy-MM-dd"）
     * @return 格式化后的字符串（null返回null；格式非法抛异常）
     */
    public static String localDateTimeToString(LocalDateTime time, String pattern) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }

    /**
     * LocalDate 转字符串（默认格式：yyyy-MM-dd）
     */
    public static String localDateToString(LocalDate date) {
        return localDateToString(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * LocalDate 转字符串（自定义格式）
     */
    public static String localDateToString(LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    /**
     * 旧API Date 转字符串（默认格式：yyyy-MM-dd HH:mm:ss）
     * 注意：Date 含时区，转换时默认用系统时区（可根据需求指定）
     */
    public static String dateToString(Date date) {
        return dateToString(date, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 旧API Date 转字符串（自定义格式）
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        // Date -> LocalDateTime（默认系统时区）
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTimeToString(localDateTime, pattern);
    }
}