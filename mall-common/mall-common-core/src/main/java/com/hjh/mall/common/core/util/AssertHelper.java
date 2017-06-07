package com.hjh.mall.common.core.util;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.common.core.util.ValueUtil;

public abstract class AssertHelper {
    
    public static void isNotNull(Object value, String message) {
        if (null == value) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotEmpty(String value, String message) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isNotEmpty(Object value, String message) {
        if (ValueUtil.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isFalse(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }
    
    public static void isEquals(Object o1, Object o2, String message) {
        if (!ValueUtil.equals(o1, o2)) {
            throw new IllegalArgumentException(message);
        }
    }
    
}
