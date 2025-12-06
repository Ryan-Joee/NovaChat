package com.ryan.novachat.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * ID生成器
 * @Author Ryan
 * @Date 2025/12/6 17:28
 */
public class IDGenerateUtil {

    /**
     * 生成带前缀的UUID
     * @param prefix
     * @return
     */
    public static String createId(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix.concat("_").concat(uuid());
        }
        return uuid();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
