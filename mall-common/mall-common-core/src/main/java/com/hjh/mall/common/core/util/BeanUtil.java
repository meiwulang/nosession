package com.hjh.mall.common.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.ReflectionUtil;

public class BeanUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeanUtil.class);

	private static Field findField(Object bean, String fieldName) {
		if (null == fieldName) {
			return null;
		}
		try {
			Class<?> clazz = bean.getClass();
			do {
				Field[] fields = clazz.getDeclaredFields();
				int len = fields.length;
				for (int i = 0; i < len; i++) {
					Field field = fields[i];
					if (fieldName.equals(field.getName())) {
						return field;
					}
				}
			} while (null != (clazz = clazz.getSuperclass()));
		} catch (Exception e) {
			// 不打印bean，万一有敏感数据
			LOGGER.error("find field failed, beanClass=" + bean.getClass()
					+ ", fieldName=" + fieldName, e);
		}
		return null;
	}

	public static Object getFieldValue(Object bean, String fieldName) {
		Field field = findField(bean, fieldName);
		if (null != field) {
			try {
				ReflectionUtil.makeAccessible(field);
				return field.get(bean);
			} catch (Exception e) {
				// 不打印bean，万一有敏感数据
				LOGGER.error("get field value failed, beanClass="
						+ bean.getClass() + ", fieldName=" + fieldName, e);
			}
		}
		return null;
	}

	public static void setFieldValue(Object bean, String fieldName,
			Object fieldValue) {
		Field field = findField(bean, fieldName);
		if (null != field) {
			try {
				ReflectionUtil.makeAccessible(field);
				field.set(bean, fieldValue);
			} catch (Exception e) {
				// 不打印bean，万一有敏感数据
				LOGGER.error("get field value failed, beanClass="
						+ bean.getClass() + ", fieldName=" + fieldName, e);
			}
		}
	}

	public static Object getProperty(Object bean, String propertyName,
			boolean tryField) {
		try {
			PropertyDescriptor propertyDescriptor = findPropertyDescriptor(bean,
					propertyName);
			if (null == propertyDescriptor) {
				if (tryField) {
					return getFieldValue(bean, propertyName);
				}
			} else {
				return propertyDescriptor.getReadMethod().invoke(bean);
			}
		} catch (Exception e) {
			// 不打印bean，万一有敏感数据
			LOGGER.error("read property failed, beanClass=" + bean.getClass()
					+ ", propertyName=" + propertyName, e);
		}
		return null;
	}

	public static void setProperty(Object bean, String propertyName,
			Object propertyValue, boolean tryField) {
		try {
			PropertyDescriptor propertyDescriptor = findPropertyDescriptor(bean,
					propertyName);
			if (null == propertyDescriptor) {
				if (tryField) {
					setFieldValue(bean, propertyName, propertyValue);
				}
			} else {
				propertyDescriptor.getWriteMethod().invoke(bean, propertyValue);
			}
		} catch (Exception e) {
			// 不打印bean，万一有敏感数据
			LOGGER.error("set property failed, beanClass=" + bean.getClass()
					+ ", propertyName=" + propertyName, e);
		}
	}

	public static Object getProperty(Object bean, String propertyName) {
		return getProperty(bean, propertyName, false);
	}

	public static void setProperty(Object bean, String propertyName,
			Object propertyValue) {
		setProperty(bean, propertyName, propertyValue, false);
	}

	private static PropertyDescriptor findPropertyDescriptor(Object bean,
			String propertyName) {
		try {
			if (null == bean || StringUtils.isBlank(propertyName)) {
				return null;
			}
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (propertyName.equals(propertyDescriptor.getName())) {
					return propertyDescriptor;
				}
			}
		} catch (Exception e) {
			// 不打印bean，万一有敏感数据
			LOGGER.error("find bean property failed, beanClass="
					+ bean.getClass() + ", propertyName=" + propertyName, e);
		}
		// 找不到对应的属性则返回null
		return null;
	}
	/**
	 * 
	 * @date 2016年7月15日
	 * @Description
	 *              <p>
	 *              两个实体具有相同属性时,用于字段内容复制 sourceObj: 目标类 target: 目标类对象
	 *              （注意：源类和目标类对象若有继承父类，父类字段不做处理）
	 *              </p>
	 * @throws Exception
	 */
	public static void reflectionAttr(Object sourceObj, Object targetObj)
			throws Exception {
		Class<?> clazz1 = Class.forName(sourceObj.getClass().getName());
		Class<?> clazz2 = Class.forName(targetObj.getClass().getName());
		// 获取两个实体类的所有属性
		Field[] fields1 = clazz1.getDeclaredFields();
		Field[] fields2 = clazz2.getDeclaredFields();
		BeanUtil cr = new BeanUtil();
		// 遍历class1Bean，获取逐个属性值，然后遍历class2Bean查找是否有相同的属性，如有相同则赋值
		for (Field f1 : fields1) {
			if (f1.getName().equals("serialVersionUID"))
				continue;
			Object value = cr.invokeGetMethod(sourceObj, f1.getName(), null);
			for (Field f2 : fields2) {
				if (f1.getName().equals(f2.getName())) {
					Object[] obj = new Object[1];
					obj[0] = value;
					cr.invokeSetMethod(targetObj, f2.getName(), obj);
				}
			}
		}

	}
	private Object invokeGetMethod(Object clazz, String fieldName,
			Object[] args) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			method = Class.forName(clazz.getClass().getName())
					.getDeclaredMethod("get" + methodName);
			return method.invoke(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	private Object invokeSetMethod(Object clazz, String fieldName,
			Object[] args) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			Class<?>[] parameterTypes = new Class[1];
			Class<?> c = Class.forName(clazz.getClass().getName());
			Field field = c.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			method = c.getDeclaredMethod("set" + methodName, parameterTypes);
			return method.invoke(clazz, args);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
