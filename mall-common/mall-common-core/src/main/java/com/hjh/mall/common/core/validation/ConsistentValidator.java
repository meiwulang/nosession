package com.hjh.mall.common.core.validation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.annotation.Consistent;
import com.hjh.mall.common.core.util.BeanUtil;

public class ConsistentValidator implements ConstraintValidator<Consistent, Object> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsistentValidator.class);
    
    private Set<String> propertyNames;
    
    private boolean literal;
    
    private String messageTemplate;
    
    @Override
    public void initialize(Consistent constraintAnnotation) {
        // 要比较的属性名
        propertyNames = new HashSet<String>();
        String[] propNames = constraintAnnotation.value();
        if (null != propNames && 0 != propNames.length) {
            for (String propName : propNames) {
                propertyNames.add(propName);
            }
        }
        
        // 是否需要按字面比较
        literal = constraintAnnotation.literal();
        
        messageTemplate = constraintAnnotation.message();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 空值直接通过，由@NotNull之类的控制
        if (null == value) {
            return true;
        }
        // 没设置要比较的属性名或者属性名少于2的，通过
        if (2 > propertyNames.size()) {
            return true;
        }
        boolean valid = true;
        String errorField = "";
        Iterator<String> propNameIt = propertyNames.iterator();
        String propName = propNameIt.next();
        String oPropName = propName;
        try {
            // 获取基准值
            Object propVal = BeanUtil.getProperty(value, propName, true);
            // 如果需要按字面比较，准备字面基准值
            String literalPropVal = null;
            if (literal && null != propVal) {
                literalPropVal = String.valueOf(propVal);
            }
            while (propNameIt.hasNext()) {
                // 获取比较值
                oPropName = propNameIt.next();
                Object oPropVal = BeanUtil.getProperty(value, oPropName, true);
                
                // 确认不是同一个对象或者都为null
                if (!propVal.equals(oPropVal)) {
                    // 已经确认了不是都为null，那其中有一个为null即不等
                    if (null == propVal || null == oPropVal) {
                        valid = false;
                        errorField = oPropName;
                        break;
                    }
                    if (literal) {
                        // 按字面比较
                        if (!literalPropVal.equals(String.valueOf(oPropVal))) {
                            valid = false;
                            errorField = oPropName;
                            break;
                        }
                    } else {
                        // 直接（非字面）比较
                        if (!propVal.equals(oPropVal)) {
                            valid = false;
                            errorField = oPropName;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("check consistent failed", e);
            errorField = oPropName;
            valid = false;
        }
        
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate).addNode(errorField)
                    .addConstraintViolation();
        }
        return valid;
    }
}
