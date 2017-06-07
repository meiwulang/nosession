package com.hjh.mall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

import com.hjh.mall.common.core.constants.ValidationConstants;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;

/**
 * 
 * @Project: hjh.mall
 * @Description 校验手机或者座机
 * @author 曾繁林
 * @date 2017年2月24日
 * @version V1.0
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface MobileOrTel {

	Class<?>[] groups() default {};

	String message() default ValidationConstants.MSG_MOBILE_OR_TEL;

	Class<? extends Payload>[] payload() default {};

	String errorCode() default ValidationErrorCodes.PARAM_MALFORMED;

	@OverridesAttribute(constraint = Pattern.class, name = "regexp")
	String regexp() default "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|"
			+ "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" + "^(1[34578]\\d{9})?$";

}
