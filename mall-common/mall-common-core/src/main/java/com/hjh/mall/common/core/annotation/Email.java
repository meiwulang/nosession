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
import javax.validation.constraints.Pattern;

import com.hjh.mall.common.core.annotation.Email;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;

@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@org.hibernate.validator.constraints.Email
public @interface Email {

	String message() default "{org.hibernate.validator.constraints.Email.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String errorCode() default ValidationErrorCodes.PARAM_MALFORMED;

	@OverridesAttribute(constraint = org.hibernate.validator.constraints.Email.class, name = "regexp")
	String regexp() default "@*";

	@OverridesAttribute(constraint = org.hibernate.validator.constraints.Email.class, name = "flags")
	Pattern.Flag[] flags() default {};

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Email[] value();
	}

}
