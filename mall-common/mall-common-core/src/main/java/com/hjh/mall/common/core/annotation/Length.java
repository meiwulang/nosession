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

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;

@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@org.hibernate.validator.constraints.Length
public @interface Length {
    
    @OverridesAttribute(constraint = org.hibernate.validator.constraints.Length.class, name = "min")
    int min() default 0;
    
    @OverridesAttribute(constraint = org.hibernate.validator.constraints.Length.class, name = "max")
    int max() default Integer.MAX_VALUE;
    
    String message() default "{org.hibernate.validator.constraints.Length.message}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_LENGTH_ERROR;
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Length[] value();
    }
    
}
