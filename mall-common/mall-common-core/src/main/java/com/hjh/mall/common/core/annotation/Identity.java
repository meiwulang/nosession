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
import com.hjh.mall.common.core.validation.IdentityValidator;



@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE,
        ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IdentityValidator.class)
public @interface Identity {
    
    Class<?>[] groups() default {};
    
    String message() default ValidationConstants.MSG_IDENTITY;
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_MALFORMED;
    
    String idKindField() default "id_kind";
    
    String idNoField() default "id_no";
    
}
