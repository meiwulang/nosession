package com.hjh.mall.order.configuration;

import com.hjh.mall.order.aop.BusinessErrorHandlerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by qiuxianxiang on 17/5/17.
 */
@Configuration
@ImportResource("classpath*:/META-INF/dubbo/dubbo.xml")
public class DubboConfiguration {



    @Bean
    public BusinessErrorHandlerAspect getBusinessErrorHandlerAspect() {
        return new BusinessErrorHandlerAspect();
    }

}
