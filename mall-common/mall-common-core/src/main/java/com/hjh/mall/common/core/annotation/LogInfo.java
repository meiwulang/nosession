package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hjh.mall.common.core.filed.LogInfoType;



@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
		
	/**
	 * 操作类型枚举类
	 * @return
	 */
	LogInfoType enumClass();
	/**
	 * 操作内容
	 * @return
	 */
	String content() default "";	
	/**备注
	 * @return
	 */
	String remark() default "";

}
