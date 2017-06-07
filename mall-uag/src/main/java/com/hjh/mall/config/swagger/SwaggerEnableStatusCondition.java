package com.hjh.mall.config.swagger;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.field.constant.MallFields;

/**
 * @Project: mall-uag
 * @Description swagger是启用的判断条件，默认关闭，要启用在环境变量中配置 enableSwagger=1
 * @author 杨益桦
 * @date 2017年6月1日
 * @version V1.0
 */
public class SwaggerEnableStatusCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

		String enableSwagger = System.getenv(MallFields.ENABLE_SWAGGER);
		if (BasicFields.ENABLE.equals(enableSwagger)) {
			System.out.println("swagger is start successed ,if you want to shutdown it,please set your envirment path 'enableSwagger'=-1 and restart the container");
			return true;
		}
		System.out.println("swagger is not start ,if you want to start it,please set your envirment path 'enableSwagger'=1  and restart the container");
		return false;
	}

}
