package com.hjh.mall.common.core.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.hjh.mall.common.core.annotation.LogInfo;



public class LogAspect {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(LogAspect.class);

	// @Resource
	// private MqService mqService;

	public void log(JoinPoint jp, Object rvt) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		// 获取Method
		Method method = methodSignature.getMethod();
		// 判断方法上是否有需要记录日志要求
		if (method.isAnnotationPresent(LogInfo.class)) {
			// 记录日志
			LogInfo logInfo = method.getAnnotation(LogInfo.class);
			logInfo.content();
			logInfo.enumClass();

		}

	}

}
