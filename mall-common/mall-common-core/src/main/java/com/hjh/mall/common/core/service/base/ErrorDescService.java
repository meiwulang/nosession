package com.hjh.mall.common.core.service.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

//import com.hundsun.bcs.common.context.RuntimeContext;

import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.message.ExtendMessageSource;
import com.hjh.mall.common.core.service.base.ErrorDescService;
import com.hjh.mall.common.core.util.ValueUtil;

public class ErrorDescService {
    
    public static final String MSG_PREFIX_ERROR_INFO = "errorInfo.";
    
    public static final String MSG_PREFIX_ERROR_CONVERT = "errorConvert.";
    
    public static final String MSG_KEY_DEFAULT_SUCCESS = MSG_PREFIX_ERROR_INFO + BasicErrorCodes.SUCCESS;
    
    public static final String MSG_KEY_DEFAULT_ERROR = MSG_PREFIX_ERROR_INFO + BasicErrorCodes.COMMON_ERROR;
    
    public static final String MSG_KEY_DEFAULT_PARAM_ERROR = MSG_PREFIX_ERROR_INFO + BasicErrorCodes.PARAM_ERROR;
    
    private static ErrorDescService errorDescService;
    
    private String msgPrefixErrorInfo = MSG_PREFIX_ERROR_INFO;
    
    private String msgPrefixErrorConvert = MSG_PREFIX_ERROR_CONVERT;
    
    private String msgKeyDefaultSuccess;
    
    private String msgKeyDefaultError;
    
    private String msgKeyDefaultParamError;
    
    private String errorCodeSuccess = BasicErrorCodes.SUCCESS;
    
    private String errorCodeError = BasicErrorCodes.COMMON_ERROR;
    
    private String errorCodeParamError = BasicErrorCodes.PARAM_ERROR;
    
    /**
     * 是否使用原有信息，若设置为true，则按精准匹配查找一次，如果查不到，则使用原有的信息
     */
    private boolean useExistsErrorInfo = true;
    
    private ExtendMessageSource messageSource;
    
    public static ErrorDescService getInstance() {
        if (null == errorDescService) {
            errorDescService = new ErrorDescService();
        }
        return errorDescService;
    }
    
    public void init() {
        msgKeyDefaultSuccess = msgPrefixErrorInfo + errorCodeSuccess;
        msgKeyDefaultError = msgPrefixErrorInfo + errorCodeError;
        msgKeyDefaultParamError = msgPrefixErrorInfo + errorCodeParamError;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void descriptError(Object result) {
        if (result instanceof Map) {
            Map map = (Map) result;
            setErrorDescInSimpleMap(map, null);
            try {
                List<Map> errors = (List<Map>) map.get(BasicFields.ERRORS);
                for (Map error : errors) {
                    setErrorDescInSimpleMap(error, getDefaultErrorDesc());
                }
            } catch (Exception e) {
                // omit
            }
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setErrorDescInSimpleMap(Map map, String defaultMessage) {
        // 查找错误信息
        String errorInfo = findErrorDescFromSimpleMap(map, defaultMessage);
        // 设置错误描述
        map.put(BasicFields.ERROR_INFO, errorInfo);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String findErrorDescFromSimpleMap(Map map, String defaultMessage) {
        // 获取原有的错误信息
        String originErrorInfo = ValueUtil.getString(map.get(BasicFields.ERROR_INFO));
        // 原有信息是否存在
        boolean existsOriginErrorInfo = StringUtils.isNotBlank(originErrorInfo);
        // 原有信息是否可用：原有信息存在且使用原有信息
        boolean originErrorInfoAvailable = existsOriginErrorInfo && useExistsErrorInfo;
        String findErrorInfo = null;
        String errorNo = ValueUtil.getString(map.get(BasicFields.ERROR_NO));
        if (StringUtils.isNotBlank(errorNo)) {
            // 转换错误码
            errorNo = convertErrorCode(errorNo);
            // 回写errorNo
            map.put(BasicFields.ERROR_NO, errorNo);
            // 获取errorOn
            String errorOn = ValueUtil.getString(map.get(BasicFields.ERROR_ON));
            // 如果errorOn非空，结合errorOn查找
            if (StringUtils.isNotBlank(errorOn)) {
                findErrorInfo = getErrorDesc(errorNo + '.' + errorOn, (String) null);
                if (StringUtils.isNotBlank(findErrorInfo)) {
                    // 如果按errorOn查到，则使用查到的
                    return findErrorInfo;
                } else if (originErrorInfoAvailable) {
                    // 如果按errorOn查不到，但原有信息可用，则使用原来的
                    return originErrorInfo;
                }
            }
            // errorOn为空，或者按errorOn没查到且原有信息不可用
            // 按errorNo查找
            findErrorInfo = getErrorDesc(errorNo, (String) null);
            if (StringUtils.isNotBlank(findErrorInfo)) {
                // 如果按errorNo查到，则使用查到的
                return findErrorInfo;
            } else if (originErrorInfoAvailable) {
                // 如果按errorOn查不到，但原有信息可用，则使用原来的
                return originErrorInfo;
            }
            // 此时必然是按errorNo查不到且原有信息不可用
            // 使用默认描述
            if (null == defaultMessage) {
                // 没指定默认错误信息，则取全局默认错误信息
                return getDefaultDesc(errorNo);
            } else {
                // 指定了默认错误信息则返回
                return defaultMessage;
            }
        } else {
            // 没有错误码，是成功的情况
            if (originErrorInfoAvailable) {
                // 原有信息可用，则使用原来的
                return originErrorInfo;
            } else {
                // 如果原有信息不可用，则取默认信息
                if (null == defaultMessage) {
                    // 没指定默认成功信息，则取全局默认成功信息
                    return getDefaultDesc(null);
                } else {
                    // 指定了默认成功信息则返回
                    return defaultMessage;
                }
            }
        }
    }
    
    private String convertErrorCode(String errorCode) {
        return getMessageSource().getMessage(msgPrefixErrorConvert + errorCode, errorCode);
    }
    
    public String getErrorDesc(String errorCode) {
        return getMessageSource().getMessage(msgPrefixErrorInfo + errorCode);
    }
    
    public String getErrorDesc(String errorCode, String defaultMessage) {
        return getMessageSource().getMessage(msgPrefixErrorInfo + errorCode, defaultMessage);
    }
    
    public String getErrorDesc(String errorCode, Object[] args) {
        return getMessageSource().getMessage(msgPrefixErrorInfo + errorCode, args);
    }
    
    public String getErrorDesc(String errorCode, Object[] args, String defaultMessage) {
        return getMessageSource().getMessage(msgPrefixErrorInfo + errorCode, args, defaultMessage);
    }
    
    public String getDefaultDesc(String errorCode) {
        if (StringUtils.isBlank(errorCode) || errorCodeSuccess.equals(errorCode)) {
            return getDefaultSuccessDesc();
        } else {
            return getDefaultErrorDesc();
        }
    }
    
    public String getDefaultSuccessDesc() {
        return getMessageSource().getMessage(msgKeyDefaultSuccess, "成功");
    }
    
    public String getDefaultErrorDesc() {
        return getMessageSource().getMessage(msgKeyDefaultError, "系统繁忙");
    }
    
    public String getDefaultParamErrorDesc() {
        return getMessageSource().getMessage(msgKeyDefaultParamError, getDefaultErrorDesc());
    }
    
    public String getMsgPrefixErrorInfo() {
        return msgPrefixErrorInfo;
    }
    
    public void setMsgPrefixErrorInfo(String msgPrefixErrorInfo) {
        if (StringUtils.isNotBlank(msgPrefixErrorInfo)) {
            this.msgPrefixErrorInfo = msgPrefixErrorInfo;
        }
    }
    
    public String getMsgPrefixErrorConvert() {
        return msgPrefixErrorConvert;
    }
    
    public void setMsgPrefixErrorConvert(String msgPrefixErrorConvert) {
        if (StringUtils.isNotBlank(msgPrefixErrorConvert)) {
            this.msgPrefixErrorConvert = msgPrefixErrorConvert;
        }
    }
    
    public String getErrorCodeSuccess() {
        return errorCodeSuccess;
    }
    
    public void setErrorCodeSuccess(String errorCodeSuccess) {
        if (StringUtils.isNotBlank(errorCodeSuccess)) {
            this.errorCodeSuccess = errorCodeSuccess;
        }
    }
    
    public String getErrorCodeError() {
        return errorCodeError;
    }
    
    public void setErrorCodeError(String errorCodeError) {
        if (StringUtils.isNotBlank(errorCodeError)) {
            this.errorCodeError = errorCodeError;
        }
    }
    
    public String getErrorCodeParamError() {
        return errorCodeParamError;
    }
    
    public void setErrorCodeParamError(String errorCodeParamError) {
        if (StringUtils.isNotBlank(errorCodeParamError)) {
            this.errorCodeParamError = errorCodeParamError;
        }
    }
    
    public ExtendMessageSource getMessageSource() {
        if (null == messageSource) {
            System.out.println("messageSource is null" );
        }
        return messageSource;
    }
    
    public void setMessageSource(ExtendMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public boolean isUseExistsErrorInfo() {
        return useExistsErrorInfo;
    }
    
    public void setUseExistsErrorInfo(boolean useExistsErrorInfo) {
        this.useExistsErrorInfo = useExistsErrorInfo;
    }
    
}
