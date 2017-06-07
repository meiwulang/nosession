package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务服务标记
 * @author chengjia
 *
 */
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizService {
    
    /**
     * 业务服务名称，如为空，则取方法名
     * @return
     */
    String name() default "";
    
    /**
     * 功能号
     * @return
     */
    String functionId() default "";
    
    /**
     * 别名（可多个）
     * @return
     */
    String[] aliases() default {};
    
    /**
     * 描述
     * @return
     */
    String desc() default "";
    
    /**
     * 是否暴露
     * @return
     */
    boolean export() default true;
    
}
