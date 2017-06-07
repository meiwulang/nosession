package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.hjh.mall.common.core.constants.DatePrecision;
import com.hjh.mall.common.core.constants.ValidationConstants;
import com.hjh.mall.common.core.constants.ValidationErrorCodes;
import com.hjh.mall.common.core.validation.PastValidator;


@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PastValidator.class)
public @interface Past {
    
    Class<?>[] groups() default {};
    
    String message() default ValidationConstants.MSG_PAST;
    
    Class<? extends Payload>[] payload() default {};
    
    String errorCode() default ValidationErrorCodes.PARAM_NOT_PAST;
    
    /**
     * 参考比较时间，{@link java.util.Date#getTime()}
     * @return
     */
    long refDate() default -1;
    
    /**
     * 是否包括参考时间
     * @return
     */
    boolean includeRef() default false;
    
    /**
     * 如果值是字符串类型的话，指明格式，如果为空白，默认按数字连续的形式根据datePrecision生成
     * @return
     */
    String stringPattern() default "";
    
    /**
     * 指明比较精度
     * @return
     */
    DatePrecision datePrecision() default DatePrecision.SECOND;
    
}
