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
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.validation.DateValueValidator;
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
		ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValueValidator.class)
public @interface DateValue {

	Class<?>[] groups() default {};

	String message() default ValidationConstants.MSG_DATE;

	Class<? extends Payload>[] payload() default {};

	String errorCode() default ValidationErrorCodes.PARAM_NOT_DATE;

	/**
	 * 指明格式
	 * 
	 * @return
	 */
	String pattern() default DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK;

}
