package com.hjh.mall.common.core.aspect;

import java.lang.reflect.Method;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.context.DataContext;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.LGSBCVOBase;

/**
 * @author chengjia
 *
 */

public class AuthCheckAspect {

	public Object checkAuth(ProceedingJoinPoint pjp) throws Throwable {
		// 在Spring的环境里，signature就是MethodSignature
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取Method
		Method method = methodSignature.getMethod();
		// 方法上没有申明@NoLogin且类上也没有申明@NoLogin，则检查登录状况
		if (!method.isAnnotationPresent(NoLogin.class) && !method
				.getDeclaringClass().isAnnotationPresent(NoLogin.class)) {
			Object result = null;
			try {
				// 检查登录及权限
				try {
					// 获取注解映射请求 TODO
					// RequestMapping p = method`
					// .getAnnotation(RequestMapping.class);
					// String[] values = p.value();

					checkAndBindLoginInfo(pjp.getArgs());
				} catch (HJHBCSErrInfoException e) {
					if (e.isErrorIn(BasicErrorCodes.NOT_LOGGED,
							BasicErrorCodes.NO_PERMISSION,
							BasicErrorCodes.NO_SUCH_CLIENT)) {
						return VOUtil.genErrorResult(e);
					}
					throw e;
				}

				// 执行业务
				result = pjp.proceed();
			} finally {
				// 清楚线程上上下问数据
				clearBinding();
			}
			return result;
		} else {
			// 执行业务
			return pjp.proceed();
		}
	}

	private void checkAndBindLoginInfo(Object[] args) {
		for (Object arg : args) {
			if (arg instanceof LGSBCVOBase) {
				checkSessionPermission((LGSBCVOBase) arg);
				break;
			}
		}
	}

	private SessionIdentity checkSessionPermission(LGSBCVOBase param) {
		// 获取携带的access_token
		String access_token = param.getAccess_token();

		// 获取会话身份对象
		SessionIdentity identity = SessionIdentity.fromStringInfo("");
		if (null == identity) {
			// 如果会话身份对象不存在则表示未登录或已超时
			throw new HJHBCSErrInfoException(BasicErrorCodes.NOT_LOGGED);
		} else {
			// 刷新会话，重置超时时间
		}
		DataContext.setAccess_token(access_token);
		DataContext.setSessionIdentity(identity);
		return identity;
	}

	public void clearBinding() {
		DataContext.removeSessionIdentity();
		DataContext.removetoken();
	}
	// 判断是否有权限
	public boolean ishaspermission(String functionId, Set<String> permissions) {
		if (permissions.contains(functionId)) {
			return true;
		} else {
			return false;
		}
	}

}
