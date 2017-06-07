package com.hjh.mall.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务服务模块标记
 * @author chengjia
 *
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizServiceModule {
    
    /**
     * 业务服务模块名称，如为空，则取类名
     * @return
     */
    String name() default "";
    
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
