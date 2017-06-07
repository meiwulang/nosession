package com.hjh.mall.common.core.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hjh.mall.common.core.annotation.DateValue;
import com.hjh.mall.common.core.util.DateTimeUtil;

public class DateValueValidator implements ConstraintValidator<DateValue, Object> {
    
    /**
     * 指明格式
     */
    private String pattern;
    
    @Override
    public void initialize(DateValue constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 空值直接通过，由@NotNull之类的控制
        if (null == value || value instanceof Date || value instanceof Calendar) {
            return true;
        }
        
        if (value instanceof String || value instanceof Integer || value instanceof Long) {
            // 字符串或Integer或Long需要解析
            String stringValue = String.valueOf(value).trim();
            // 空值直接通过，由@NotNull之类的控制
            // 0直接通过，按无值处理
            if (stringValue.isEmpty() || "0".equals(stringValue)) {
                return true;
            }
            Date date = DateTimeUtil.toDateOrNull(stringValue, pattern);
            // 可解析但可能解析错，重新format比较
            return null != date && stringValue.equals(DateTimeUtil.toStringOrNull(date, pattern));
        } else {
            // 其他类型非法
            return false;
        }
    }
    
}
