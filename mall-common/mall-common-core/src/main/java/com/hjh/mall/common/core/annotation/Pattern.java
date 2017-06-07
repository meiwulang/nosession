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
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern.Flag;

import com.hjh.mall.common.core.annotation.Pattern;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;



@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@javax.validation.constraints.Pattern(regexp = "")
public @interface Pattern {
    
    @OverridesAttribute(constraint = Min.class, name = "regexp")
    String regexp();
    
    @OverridesAttribute(constraint = Min.class, name = "flags")
    Flag[] flags() default {};
    
    String message() default "{javax.validation.constraints.Pattern.message}";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_MALFORMED;
    
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Pattern[] value();
    }
    
}
