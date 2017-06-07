package com.hjh.mall.config.swagger;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月9日
 * @version V1.0 
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/* 
 * Restful API 访问路径: 
 * http://IP:port/{context-path}/swagger-ui.html 
 * eg:http://localhost:8080/jd-config-web/swagger-ui.html 
 */  
@Conditional(value = { SwaggerEnableStatusCondition.class })
@Configuration
@EnableSwagger2
public class RestApiConfig extends WebMvcConfigurationSupport{  
  
    @Bean
    public Docket createRestApi() {  
        return new Docket(DocumentationType.SWAGGER_2)  
                .apiInfo(apiInfo())  
                .select()   
                .paths(PathSelectors.any())  
                .build(); 
        //.apis(RequestHandlerSelectors.basePackage("com.hjh.mall.controller")) 
    }  
  
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()  
                .title("Spring 中使用Swagger2构建RESTful APIs")  
                .termsOfServiceUrl("http://192.168.0.5:8887/brandinfo")
                .contact(new Contact("boochy","","boochy@hjh365.com"))
                .version("1.1")  
                .build();  
    }  
} 
