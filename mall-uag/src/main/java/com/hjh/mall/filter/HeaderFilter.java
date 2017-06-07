package com.hjh.mall.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.util.StringUtil;

public class HeaderFilter implements Filter {

	public static final String PARAM_NAME_ADD_HEADER = "addHeader";

	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFilter.class);

	private Map<String, String> addHeaders = new HashMap<String, String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initAddHeader(filterConfig);
	}

	private void initAddHeader(FilterConfig filterConfig) {
		String addHeadersConfig = filterConfig.getInitParameter(PARAM_NAME_ADD_HEADER);
		List<String> addHeaderConfigPairs = StringUtil.tokenize(addHeadersConfig, "\r\n", true, true);
		for (String addHeaderConfigPair : addHeaderConfigPairs) {
			String[] addHeaderConfig = addHeaderConfigPair.split(":");
			if (addHeaderConfig.length == 2) {
				addHeaders.put(addHeaderConfig[0].trim(), addHeaderConfig[1].trim());
			}
		}
		LOGGER.info("will add headers: " + addHeaders);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (!addHeaders.isEmpty()) {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			for (Entry<String, String> addHeader : addHeaders.entrySet()) {
				httpServletResponse.addHeader(addHeader.getKey(), addHeader.getValue());
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
