package com.hjh.mall.common.core.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.hjh.mall.common.core.annotation.Range;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;



@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@org.hibernate.validator.constraints.Range
public @interface Range {
    
    @OverridesAttribute(constraint = org.hibernate.validator.constraints.Range.class, name = "min")
    long min() default 0;
    
    @OverridesAttribute(constraint = org.hibernate.validator.constraints.Range.class, name = "max")
    long max() default Long.MAX_VALUE;
    
    String message() default "{org.hibernate.validator.constraints.Range.message}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_RANGE_ERROR;
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Range[] value();
    }
    
}
