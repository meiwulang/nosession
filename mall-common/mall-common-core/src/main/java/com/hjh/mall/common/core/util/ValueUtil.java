package com.hjh.mall.common.core.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.util.IOUtil;
import com.hjh.mall.common.core.util.ValueUtil;

public class ValueUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ValueUtil.class);
    
    public static String getString(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        return String.valueOf(object);
    }
    
    public static String getString(Object object) {
        return getString(object, "");
    }
    
    public static int getInt(Object object, int defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static int getInt(Object object) {
        return getInt(object, 0);
    }
    
    public static Integer getIntObj(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static Integer getIntObj(Object object) {
        return getIntObj(object, 0);
    }
    
    public static long getLong(Object object, long defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static long getLong(Object object) {
        return getLong(object, 0);
    }
    
    public static Long getLongObj(Object object, Long defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static Long getLongObj(Object object) {
        return getLongObj(object, 0L);
    }
    
    public static double getDouble(Object object, double defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static double getDouble(Object object) {
        return getDouble(object, 0);
    }
    
    public static Double getDoubleObj(Object object, Double defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static Double getDoubleObj(Object object) {
        return getDouble(object, 0);
    }
    
    public static float getFloat(Object object, float defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }
        try {
            return Float.parseFloat(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static float getFloat(Object object) {
        return getFloat(object, 0);
    }
    
    public static Float getFloatObj(Object object, Float defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }
        try {
            return Float.parseFloat(String.valueOf(object));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static Float getFloatObj(Object object) {
        return getFloat(object, 0);
    }
    
    public static boolean getBoolean(Object object, boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return Boolean.parseBoolean(String.valueOf(object));
    }
    
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }
    
    public static Boolean getBooleanObj(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        return Boolean.parseBoolean(String.valueOf(object));
    }
    
    public static Boolean getBooleanObj(Object object) {
        return getBooleanObj(object, null);
    }
    
    public static boolean isEmpty(Object o) {
        if (null == o) {
            return true;
        } else {
            if (o instanceof String) {
                return ((String) o).isEmpty();
            } else if (o instanceof Collection) {
                return ((Collection<?>) o).isEmpty();
            } else if (o instanceof Map) {
                return ((Map<?, ?>) o).isEmpty();
            } else if (o.getClass().isArray()) {
                return 0 == Array.getLength(o);
            } else if (o instanceof Iterator) {
                return !((Iterator<?>) o).hasNext();
            } else if (o instanceof Enumeration) {
                return !((Enumeration<?>) o).hasMoreElements();
            }
            return false;
        }
    }
    
    public static int countTrue(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 1 : 0;
        }
        return c;
    }
    
    public static int countFalse(boolean... conditions) {
        int c = 0;
        for (boolean condition : conditions) {
            c += condition ? 0 : 1;
        }
        return c;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> getMap(Map<String, Object> map, String key) {
        try {
            Object value = map.get(key);
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            } else if (value instanceof List) {
                List list = (List) value;
                if (!list.isEmpty()) {
                    Object element = list.get(0);
                    if (element instanceof Map) {
                        return (Map<String, Object>) element;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("getMap error", e);
        }
        return null;
    }
    
    public static boolean equals(Object o1, Object o2) {
        return equals(o1, o2, false);
    }
    
    public static boolean equals(Object o1, Object o2, boolean compareLiterally) {
        if (o1.equals(o2)) {
            return true;
        }
        if (null == o1 || null == o2) {
            return false;
        }
        if (compareLiterally) {
            return String.valueOf(o1).equals(String.valueOf(o2));
        } else {
            Class<?> clazz1 = o1.getClass();
            Class<?> clazz2 = o2.getClass();
            if (clazz1.isAssignableFrom(clazz2)) {
                return o1.equals(o2);
            } else if (clazz2.isAssignableFrom(clazz1)) {
                return o2.equals(o1);
            }
        }
        return false;
    }
    
    public static Properties loadProperties(String filePath) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        } catch (Exception e) {
            LOGGER.error("load properties failed, file: " + filePath, e);
            return null;
        } finally {
            IOUtil.close(fis);
        }
    }
    
    public static Properties loadProperties(InputStream is, boolean close) {
        try {
            Properties prop = new Properties();
            prop.load(is);
            return prop;
        } catch (Exception e) {
            LOGGER.error("load properties failed", e);
            return null;
        } finally {
            if (close) {
                IOUtil.close(is);
            }
        }
    }
    
    public static void addRequired(Map<String, Object> map, String key, Object value) {
        if (null == value) {
            throw new IllegalArgumentException(key + " is required");
        }
        map.put(key, value);
    }
    
    public static void addNotBlank(Map<String, Object> map, String key, String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(key + " must be not blank");
        }
        map.put(key, value);
    }
    
    public static void addNotEmpty(Map<String, Object> map, String key, String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException(key + " must be not empty");
        }
        map.put(key, value);
    }
    
    public static boolean addOptional(Map<String, Object> map, String key, Object value) {
        if (null == value) {
            return false;
        }
        map.put(key, value);
        return true;
    }
    
}
