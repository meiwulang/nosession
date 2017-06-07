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
import javax.validation.constraints.Pattern;

import com.hjh.mall.common.core.constants.ValidationConstants;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;

/**
 * @Project: mall-common-core
 * @Description 校验中文或者字母	
 * @author 杨益桦
 * @date 2017年5月31日
 * @version V1.0 
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface ChineseOrLetter {

	Class<?>[] groups() default {};

	String message() default ValidationConstants.MSG_MOBILE_OR_LETTER;

	Class<? extends Payload>[] payload() default {};

	String errorCode() default ValidationErrorCodes.PARAM_MALFORMED;

	@OverridesAttribute(constraint = Pattern.class, name = "regexp")
	String regexp() default "^[a-zA-Z\u4e00-\u9fa5]*$";

}
