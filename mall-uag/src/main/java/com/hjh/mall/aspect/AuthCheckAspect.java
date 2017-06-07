package com.hjh.mall.aspect;

import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.LGSBCVOBase;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.controller.base.ControllerBase;
import com.hjh.mall.util.BusiSessionHelper;

public class AuthCheckAspect {

	@Resource
	private BusiSessionHelper busiSessionHelper;

	private static final String CLASS_NAME = AuthCheckAspect.class.getName();

	// private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

	public AuthCheckAspect() {
		// PrintUtil.printComponentLoaded(LOGGER, CLASS_NAME);
	}

	public Object checkAuth(ProceedingJoinPoint pjp) throws Throwable {
		// 在Spring的环境里，signature就是MethodSignature
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		// 获取Method
		Method method = methodSignature.getMethod();
		// 方法上没有申明@NoLogin且类上也没有申明@NoLogin，则检查登录状况
		if (!method.isAnnotationPresent(NoLogin.class)
				&& !method.getDeclaringClass().isAnnotationPresent(NoLogin.class)) {

			// // 获取携带的access_token
			// String access_token = HJYBizDataContext.getAccess_token();
			// if (null == access_token) {
			String access_token = "";
			for (Object arg : pjp.getArgs()) {
				if (arg instanceof LGSBCVOBase) {
					access_token = ((LGSBCVOBase) arg).getAccess_token();
					break;
				}
			}
			if (StringUtils.isBlank(access_token)) {
				access_token = HJYBizDataContext.getAccess_token();
			}
			// }
			SessionIdentity sessionIdentity = busiSessionHelper.fetchSessionIdentityByAccess_token(access_token);

			if (null == sessionIdentity) {
				ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes();
				HttpServletRequest request = requestAttributes.getRequest();
				String uri = request.getRequestURI();
				// if (null != request.getContentType() &&
				// request.getContentType().contains("application/json")) {
				if (method.getReturnType().equals(Map.class)) {
					return (VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED));

				} else {
					return ControllerBase.REDIRECT_TO_LOGIN;
				}
			} else {
				busiSessionHelper.refreshSession(access_token);
				try {
					HJYBizDataContext.setSessionIdentity(sessionIdentity);
					// for (Object arg : pjp.getArgs()) {
					// if (arg instanceof OperatorVo) {
					// ((OperatorVo)
					// arg).setHjy_operator_id(HJYBizDataContext.getSessionIdentity().getClientId());
					// ((OperatorVo)
					// arg).setHjy_operator_name(HJYBizDataContext.getSessionIdentity()
					// .getOperatorName());
					// break;
					// }
					// }
					// 执行业务
					return pjp.proceed();
				} finally {
					HJYBizDataContext.removeAccess_token();
					HJYBizDataContext.removeSessionIdentity();
				}
			}

		}

		// 执行业务
		return pjp.proceed();
	}

	private HttpServletResponse getReponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return requestAttributes.getResponse();
	}

}
