package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.hjh.mall.common.core.constants.ValidationConstants;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;
import com.hjh.mall.common.core.validation.ConsistentValidator;


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE,
        ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ConsistentValidator.class)
public @interface Consistent {
    
    Class<?>[] groups() default {};
    
    String message() default ValidationConstants.MSG_CONSISTENT;
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_INCONSISTENT;
    
    /**
     * properties，要校验一致性的属性集
     * @return
     */
    String[] value();
    
    /**
     * 是否按字面比较，如为true，会都按string比较。
     * @return
     */
    boolean literal() default false;
    
}
