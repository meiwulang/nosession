package com.hjh.mall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hjh.mall.common.core.util.CookieUtil;
import com.hjh.mall.constants.SysContants;
import com.hjh.mall.context.HJYBizDataContext;

public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// no-op
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			// 获取access_token
			String requestAccess_token = CookieUtil.getCookie((HttpServletRequest) request,
					SysContants.COOKIE_NAME_ACCESS_TOKEN);
			// 如果access_token的cookie存在，把它设置到线程上
			if (null != requestAccess_token) {
				HJYBizDataContext.setAccess_token(requestAccess_token);
			}

			// 执行业务处理
			chain.doFilter(request, response);
		} finally {
			HJYBizDataContext.removeAccess_token();
		}
	}

	@Override
	public void destroy() {
		// no-op
	}

}
