package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Digits;

import com.hjh.mall.common.core.constants.ValidationConstants;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Digits(integer = Integer.MAX_VALUE, fraction = 0)
public @interface IntegerLength {
    
    Class<?>[] groups() default {};
    
    String message() default ValidationConstants.MSG_INTEGERLENGTH;
    
    String errorCode() default ValidationErrorCodes.PARAM_LENGTH_ERROR;
    
    @OverridesAttribute(constraint = Digits.class, name = "integer")
    int length();
    
    Class<? extends Payload>[] payload() default {};
    
}
