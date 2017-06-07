package com.hjh.mall.controller.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.service.base.ErrorDescService;
import com.hjh.mall.common.core.util.VOUtil;


public abstract class ControllerBase {
    
	public static final String FORWARD_TO_LOGIN = "forward:/login";
	
	public static final String REDIRECT_TO_LOGIN = "redirect:/login";
    
    public static final String PAGE_ERR_MSG = "errMsg";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerBase.class);
    
    protected HttpServletRequest request;
    
    protected HttpServletResponse response;
    
    private void initRequestAndResponse() {
        if (null == request) {
			// 获取httpRequest和httpResponse
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            request = requestAttributes.getRequest();
            response = requestAttributes.getResponse();
        }
    }
    
    protected HttpServletRequest getRequest() {
        initRequestAndResponse();
        return request;
    }
    
    protected HttpServletResponse getResponse() {
        initRequestAndResponse();
        return response;
    }
    
    protected ModelAndView endWithError(ModelAndView mv, String toView, String errorInfo) {
		// TODO 使用messageResource
        mv.addObject(PAGE_ERR_MSG, errorInfo);
        mv.setViewName(toView);
        return mv;
    }
    
    protected ModelAndView endWithError(ModelAndView mv, String toView, Map<String, Object> errorResult) {
        return endWithError(mv, toView, (String) errorResult.get(BasicFields.ERROR_INFO));
    }
    
    protected ModelAndView endWithErrorCheckNotLogged(ModelAndView mv, String toView,
            Map<String, Object> errorResult) {
        if (VOUtil.isErrorOf(errorResult, BasicErrorCodes.NOT_LOGGED)) {
            mv.setViewName(REDIRECT_TO_LOGIN);
            return mv;
        } else {
            return endWithError(mv, toView, errorResult);
        }
    }
    
    protected ModelAndView endWithErrorCheckNotLogged(ModelAndView mv, String toView,
            Map<String, Object> errorResult, String errorInfo) {
        if (VOUtil.isErrorOf(errorResult, BasicErrorCodes.NOT_LOGGED)) {
            mv.setViewName(REDIRECT_TO_LOGIN);
            return mv;
        } else {
            return endWithError(mv, toView, errorInfo);
        }
    }
    
    protected ModelAndView simpleEnd(ModelAndView mv, String successTo, String errorTo, Map<String, Object> result) {
        if (VOUtil.isSuccess(result)) {
            mv.setViewName(successTo);
            return mv;
        } else {
            return endWithErrorCheckNotLogged(mv, errorTo, result);
        }
    }
    
    protected ModelAndView simpleEnd(ModelAndView mv, String successTo, String errorTo, Map<String, Object> result,
            String errorInfo) {
        if (VOUtil.isSuccess(result)) {
            mv.setViewName(successTo);
            return mv;
        } else {
            return endWithErrorCheckNotLogged(mv, errorTo, result, errorInfo);
        }
    }
    
    public static ModelAndView endWithError(Map<String, Object> result, String errorInfo,
            HttpServletResponse response) {
        if (null == result) {
            result = VOUtil.genCommonErrorResult();
        }
        if (StringUtils.isNotBlank(errorInfo)) {
            result.put(BasicFields.ERROR_INFO, errorInfo);
        } else {
            ErrorDescService.getInstance().descriptError(result);
        }
        try {
            String jsonString = new JSONObject(result).toJSONString();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(jsonString);
            response.getWriter().flush();
        } catch (Exception e) {
            LOGGER.error("write json result error", e);
        }
        return null;
    }
    
    protected ModelAndView endWithError(Map<String, Object> result, String errorInfo) {
        return endWithError(result, errorInfo, getResponse());
    }
    
    protected ModelAndView endWithError(Map<String, Object> result) {
        return endWithError(result, null);
    }
    
    protected ModelAndView simpleEnd(Map<String, Object> result, String successTo, String errorInfo) {
        if (VOUtil.isSuccess(result)) {
            return new ModelAndView(successTo);
        } else {
            return endWithError(result, errorInfo);
        }
    }
    
    protected ModelAndView simpleEnd(Map<String, Object> result, String successTo) {
        if (VOUtil.isSuccess(result)) {
            return new ModelAndView(successTo);
        } else {
            return endWithError(result, null);
        }
    }
    
}
