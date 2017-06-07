package com.hjh.mall.common.core.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.service.base.ErrorDescService;

/**
 * @author chengjia
 *
 */
public class ErrorDescAspect {

	private static final String CLASS_NAME = ErrorDescAspect.class.getName();

	private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

	private String bizServiceNamePrefix = "";

	private ErrorDescService errorDescService;

	public ErrorDescAspect() {

	}

	public Object handleErrorDesc(ProceedingJoinPoint pjp) throws Throwable {
		setRequestMappingName(pjp);
		Object result = null;
		try {
			result = pjp.proceed();
			descError(result);
		} finally {
			clearBinding();
		}
		return result;
	}

	private void setRequestMappingName(ProceedingJoinPoint pjp) {
		try {
			// 在Spring的环境里，signature就是MethodSignature
			MethodSignature methodSignature = (MethodSignature) pjp
					.getSignature();
			// 获取Method
			Method method = methodSignature.getMethod();
			String controllerName = null;
			// 如果方法上声明了@，则取其name
			if (method.isAnnotationPresent(BizService.class)) {
				BizService bizserverMark = method
						.getAnnotation(BizService.class);
				controllerName = bizserverMark.functionId();
			}
			// 如果空白，使用方法名
			if (StringUtils.isBlank(controllerName)) {
				controllerName = bizServiceNamePrefix + method.getName();
			}
		} catch (Exception e) {
			LOGGER.warn("setBizServiceName failed", e);
		}
	}

	private void descError(Object result) {
		try {
			errorDescService.descriptError(result);
		} catch (Exception e) {
			LOGGER.warn("descError failed", e);
		}
	}



	/**
	 * 
	 */
	private void clearBinding() {
		// BizDataContext.removeBizServiceName();
	}

	public void setBizServiceNamePrefix(String bizServiceNamePrefix) {
		if (StringUtils.isNotBlank(bizServiceNamePrefix)) {
			this.bizServiceNamePrefix = bizServiceNamePrefix;
		}
	}

	public void setErrorDescService(ErrorDescService errorDescService) {
		this.errorDescService = errorDescService;
	}

}
