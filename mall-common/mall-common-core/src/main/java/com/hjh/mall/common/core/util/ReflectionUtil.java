package com.hjh.mall.common.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtil {
    
    private static Set<Class<?>> primitiveClasses = new HashSet<Class<?>>();
    
    static {
        initPrimitiveClasses();
    }
    
    private static void initPrimitiveClasses() {
        primitiveClasses.add(boolean.class);
        primitiveClasses.add(Boolean.class);
        
        primitiveClasses.add(char.class);
        primitiveClasses.add(Character.class);
        
        primitiveClasses.add(byte.class);
        primitiveClasses.add(Byte.class);
        
        primitiveClasses.add(short.class);
        primitiveClasses.add(Short.class);
        
        primitiveClasses.add(int.class);
        primitiveClasses.add(Integer.class);
        
        primitiveClasses.add(long.class);
        primitiveClasses.add(Long.class);
        
        primitiveClasses.add(float.class);
        primitiveClasses.add(Float.class);
        
        primitiveClasses.add(double.class);
        primitiveClasses.add(Double.class);
        
        primitiveClasses.add(BigInteger.class);
        primitiveClasses.add(BigDecimal.class);
        
        primitiveClasses.add(String.class);
        primitiveClasses.add(java.util.Date.class);
        primitiveClasses.add(java.sql.Date.class);
        primitiveClasses.add(java.sql.Time.class);
        primitiveClasses.add(java.sql.Timestamp.class);
    }
    
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass()
                .getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }
    
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass()
                .getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
    
    public static boolean isPrimitive(Class<?> clazz) {
        return primitiveClasses.contains(clazz);
    }
    
    public static boolean isPrimitiveOrObject(Class<?> clazz) {
        return primitiveClasses.contains(clazz) || Object.class.equals(clazz);
    }
    
    /**
     * 在该类及其父类上查找指定注解，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> A findAnnotationOnClassAndSuper(Class<?> clazz, Class<A> annClass) {
        A ann = null;
        // 检查自身类及所有的父类
        Class<?> checkClass = clazz;
        do {
            ann = checkClass.getAnnotation(annClass);
            if (null != ann) {
                return ann;
            }
        } while (null != (checkClass = checkClass.getSuperclass()));
        return null;
    }
    
    /**
     * 在该类所有的接口上查找该注解，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> A findAnnotationOnInterfaces(Class<?> clazz, Class<A> annClass) {
        A ann = null;
        // 检查所有的接口
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> itf : interfaces) {
            ann = itf.getAnnotation(annClass);
            if (null != ann) {
                return ann;
            }
        }
        return null;
    }
    
    /**
     * 在该类及其父类上查找该注解，找不到再查找所有的接口上，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> A findAnnotationOnSuperClassOrInterfaces(Class<?> clazz, Class<A> annClass) {
        // 先检查自身类及所有的父类
        A ann = findAnnotationOnClassAndSuper(clazz, annClass);
        if (null != ann) {
            return ann;
        }
        // 再检查所有的接口
        return findAnnotationOnInterfaces(clazz, annClass);
    }
    
    /**
     * 在该类及其父类上查找有指定注解的类，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> Class<?> findClassWithAnnotation(Class<?> clazz, Class<A> annClass) {
        A ann = null;
        // 检查自身类及所有的父类
        Class<?> checkClass = clazz;
        do {
            ann = checkClass.getAnnotation(annClass);
            if (null != ann) {
                return checkClass;
            }
        } while (null != (checkClass = checkClass.getSuperclass()));
        return null;
    }
    
    /**
     * 在该类所有的接口上查找有指定注解的接口，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> Class<?> findInterfaceWithAnnotaion(Class<?> clazz, Class<A> annClass) {
        A ann = null;
        // 检查所有的接口
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> itf : interfaces) {
            ann = itf.getAnnotation(annClass);
            if (null != ann) {
                return itf;
            }
        }
        return null;
    }
    
    /**
     * 在该类及其父类及所有的接口上查找有指定注解的类，找到一个即停止
     * @param clazz
     * @param annClass
     * @return
     */
    public static <A extends Annotation> Class<?> findClassOrInterfaceWithAnnotaion(Class<?> clazz,
            Class<A> annClass) {
        // 先检查自身类及所有的父类
        Class<?> annotatedClass = findClassWithAnnotation(clazz, annClass);
        if (null != annotatedClass) {
            return annotatedClass;
        }
        // 再检查所有的接口
        return findInterfaceWithAnnotaion(clazz, annClass);
    }
    
    public static Method getSameSignatureMethodOfObject(Object object, Method method) {
        String methodName = method.getName();
        Class<?>[] paramTypes = method.getParameterTypes();
        try {
            return object.getClass().getMethod(methodName, paramTypes);
        } catch (Exception e) {
            throw new RuntimeException("getMethodSameSignature error", e);
        }
    }
    
}
