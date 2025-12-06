package com.ryan.novachat.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * 时间的工具类
 * @Author Ryan
 * @Date 2025/12/6 17:07
 */
public final class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    private DateUtil() {
    }

    public static String getDate2String(String format, LocalDateTime date) {
        if (date != null) {
            if (StringUtils.isBlank(format)) {
                format = YYYY_MM_DD_HH_MM_SS;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }
}
