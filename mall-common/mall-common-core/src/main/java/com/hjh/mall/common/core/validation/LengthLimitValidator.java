package com.hjh.mall.common.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.annotation.LengthLimit;

public class LengthLimitValidator implements ConstraintValidator<LengthLimit, Object> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LengthLimitValidator.class);
    
    private int len;
    
    @Override
    public void initialize(LengthLimit constraintAnnotation) {
        len = constraintAnnotation.len();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value || "".equals(value) || "null".equals(value)) {
            return true;
        }
        if (value instanceof String) {
            String str = (String) value;
            if (str.length() > len) {
                LOGGER.error("长度不符合规范");
                return false;
            } else {
                return true;
            }
        } else {
            LOGGER.error("长度不符合规范");
            return false;
        }
    }
    
}
