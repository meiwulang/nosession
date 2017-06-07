package com.hjh.mall.common.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;

public class ValidateUtil {
    
    private static Validator validator;
    
    static {
        init();
    }
    
    private static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    public static <T> void validOrThrowException(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new HJHBCSErrInfoException(BasicErrorCodes.PARAM_ERROR, buildMessageFromViolations(violations));
        }
    }
    
    public static <T> Set<ConstraintViolation<T>> validOrReturnViolations(T bean) {
        return validator.validate(bean);
    }
    
    public static <T> String validOrReturnViolationsMessage(T bean) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            return buildMessageFromViolations(violations);
        }
        return null;
    }
    
    private static <T> String buildMessageFromViolations(Set<ConstraintViolation<T>> violations) {
        // !violations.isEmpty()
        StringBuilder sBuilder = new StringBuilder(violations.size() * 32);
        for (ConstraintViolation<T> violation : violations) {
            sBuilder.append('[').append(violation.getPropertyPath()).append(']').append(violation.getMessage())
                    .append(',');
        }
        sBuilder.deleteCharAt(sBuilder.length() - 1);
        return sBuilder.toString();
    }
    
    public static <T> List<Map<String, Object>> validOrReturnViolationsStructure(T bean) {
    	
    	Method[] methods = bean.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("get")) {
				if (method.getReturnType().getName().equals("java.util.List")) {

					try {
						List list = (List) method.invoke(bean);

						if (list != null && list.size() > 0) {

							for (Object object : list) {
								Set<ConstraintViolation<Object>> violations = validator.validate(object);
								if (!violations.isEmpty()) {
									return buildStructureFromViolations(violations);
								}
							}
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
    	
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            return buildStructureFromViolations(violations);
        }
        return null;
    }
    
    private static <T> List<Map<String, Object>> buildStructureFromViolations(Set<ConstraintViolation<T>> violations) {
        // !violations.isEmpty()
        List<Map<String, Object>> errors = new ArrayList<Map<String, Object>>(violations.size());
        for (ConstraintViolation<T> violation : violations) {
            Map<String, Object> error = new HashMap<String, Object>();
            // errorOn 出错位置
            String propertyPathString = violation.getPropertyPath().toString();
            if (StringUtils.isNotBlank(propertyPathString)) {
                error.put(BasicFields.ERROR_ON, propertyPathString);
            }
            // errorInfo 错误信息
            error.put(BasicFields.ERROR_INFO, violation.getMessage());
            ConstraintDescriptor<?> consDesc = violation.getConstraintDescriptor();
            Map<String, Object> annAttr = consDesc.getAttributes();
            // errorNo 错误编号
            String errorcode = ValueUtil.getString(annAttr.get("errorCode"));
            if (StringUtils.isNotBlank(errorcode)) {
                error.put(BasicFields.ERROR_NO, errorcode);
            }
            // errorValue 错误值
            error.put(BasicFields.ERROR_VALUE, violation.getInvalidValue());
            errors.add(error);
        }
        return errors;
    }
    
    public static <T> boolean hasInvalidOf(Set<ConstraintViolation<T>> violations, Class<?>... constraintAnnClasses) {
        if (null == violations || violations.isEmpty() || null == constraintAnnClasses
                || 0 == constraintAnnClasses.length) {
            return false;
        }
        for (ConstraintViolation<T> violation : violations) {
            Class<?> violationConsAnnClass = violation.getConstraintDescriptor().getAnnotation().getClass();
            for (Class<?> consAnnClass : constraintAnnClasses) {
                if (consAnnClass.isAssignableFrom(violationConsAnnClass)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T> boolean hasInvalidAllOf(Set<ConstraintViolation<T>> violations,
            Class<?>... constraintAnnClasses) {
        if (null == violations || violations.isEmpty() || null == constraintAnnClasses
                || 0 == constraintAnnClasses.length) {
            return false;
        }
        Set<Class<?>> checkConsAnnClasses = new HashSet<Class<?>>(constraintAnnClasses.length);
        for (Class<?> consAnnClass : constraintAnnClasses) {
            checkConsAnnClasses.add(consAnnClass);
        }
        Set<Class<?>> foundConsAnnClasses = new HashSet<Class<?>>(checkConsAnnClasses.size());
        for (ConstraintViolation<T> violation : violations) {
            Class<?> violationConsAnnClass = violation.getConstraintDescriptor().getAnnotation().getClass();
            for (Class<?> consAnnClass : constraintAnnClasses) {
                if (consAnnClass.isAssignableFrom(violationConsAnnClass)) {
                    foundConsAnnClasses.add(consAnnClass);
                }
            }
        }
        return checkConsAnnClasses.size() == foundConsAnnClasses.size();
    }
    
}
