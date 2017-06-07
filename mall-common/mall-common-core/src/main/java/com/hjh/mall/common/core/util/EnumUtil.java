package com.hjh.mall.common.core.util;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengjia
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EnumUtil {
    
    private static Map<Class<? extends Enum>, Map<Object, ? extends Enum>> enumsMap = new HashMap<Class<? extends Enum>, Map<Object, ? extends Enum>>();
    
    private static Lock enumsMapLock = new ReentrantLock();
    
    public static <E extends Enum> Map<Object, E> getEnumMap(Class<E> enumClass) {
        Map<Object, E> enumMap = (Map<Object, E>) enumsMap.get(enumClass);
        if (null == enumMap) {
            enumsMapLock.lock();
            Method getValMethod = null;
            try {
                getValMethod = enumClass.getDeclaredMethod("getVal");
            } catch (NoSuchMethodException e) {
                // 没有getVal方法的按普通枚举处理
            }
            try {
                Object val = null;
                enumMap = new LinkedHashMap<Object, E>();
                E[] enums = enumClass.getEnumConstants();
                for (E e : enums) {
                    // 根据是否有getVal方法来获取值
                    if (null != getValMethod) {
                        val = getValMethod.invoke(e);
                    } else {
                        val = e.name();
                    }
                    enumMap.put(val, e);
                }
            } catch (Exception e) {
                throw new RuntimeException("inspect enum class error", e);
            } finally {
                enumsMapLock.unlock();
            }
        }
        return enumMap;
    }
    
    public static <E extends Enum<?>> E valOf(Object val, Class<E> enumClass) {
        if (null == val) {
            return null;
        }
        Map<Object, E> enumMap = getEnumMap(enumClass);
        return enumMap.get(val);
    }
}